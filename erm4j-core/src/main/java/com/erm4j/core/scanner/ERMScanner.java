package com.erm4j.core.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erm4j.core.bean.Entity;
import com.erm4j.core.bean.EntityAttribute;
import com.erm4j.core.bean.EntityReferenceAttribute;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.ScanResult;

/***
 * Scanner that scans classes in certain packages and extracts
 * information about {@link Entity} objects 
 * @author root
 *
 */
public class ERMScanner {

	private String packageScanMask = "";
	private List<ClassGraphEntityBuilder> builders = new ArrayList<>();
	private DBTableNamingConventions tableNamingConventions = new DBTableNamingConventions();

	public String getPackageScanMask() {
		return packageScanMask;
	}

	public ERMScanner setPackageScanMask(String packageScanMask) {
		this.packageScanMask = packageScanMask;
		return this;
	}
	
	public ERMScanner addEntityBuilder(ClassGraphEntityBuilder builder) {
		this.builders.add(builder);
		return this;
	}
	
	public List<Entity> scan() {
		try (ScanResult scanResult =
		        new ClassGraph()
		            .verbose()                   // Log to stderr
		            .enableClassInfo()             // Scan only classes
		            .whitelistPackages(packageScanMask)  // Scan packageScanMask subpackages (omit to scan all packages)
		            .scan()) {                   // Start the scan
			
			//Building list of unique classes suitable for building entities
			Map<String, ClassInfo> classInfoMap = new HashMap<String, ClassInfo>();
			for (ClassGraphEntityBuilder entityBuilder : builders) {
				ClassInfoList applicableClassList = entityBuilder.getApplicableClassList(scanResult);
				for (ClassInfo classInfo : applicableClassList) {
					if (!classInfoMap.containsKey(classInfo.getName())) {
						classInfoMap.put(classInfo.getName(), classInfo);
					}
				}
			}
			// We need to perform double step transformation in order
			// to correctly build references between entities, because
			// during the first step we may not have correctly built referenced entities
			// having correct uid's
			List<Entity> entities = new ArrayList<>();
			// Map of attribute.uid - FieldInfo needed for entity references resolutions
			Map<String, FieldInfo> refAttributesFieldInfo = new HashMap<>();
			// First step - build entities with all attributes
			// and do not fill references
			for (ClassInfo classInfo : classInfoMap.values()) {
				Entity entity = new Entity();
				// We are sure that one of builders is applicable for building entity
				// because earlier we preselected only applicable classes
				for (ClassGraphEntityBuilder builder : builders) {
					if (builder.isApplicableFor(classInfo)) {
						builder.fillEntityFields(entity, classInfo, tableNamingConventions);
					}
				}
				// Filling basic attributes fields
				for (FieldInfo fieldInfo : classInfo.getFieldInfo()) {
					EntityAttribute entityAttribute = null;
					for (ClassGraphEntityBuilder builder : builders) {
						if (builder.isApplicableFor(fieldInfo)) {
							if (entityAttribute == null) {
								if (builder.isReferenceAttribute(fieldInfo)) {
									entityAttribute = new EntityReferenceAttribute();
								}
								else {
									entityAttribute = new EntityAttribute();
								}
							}
							// This call fills common attributes and doesn't not touch
							// entity reference attributes
							builder.fillEntityAttributeFields(entityAttribute, fieldInfo, tableNamingConventions);
						}
					}
					if (entityAttribute != null) {
						entity.getAttributes().add(entityAttribute);
						if (entityAttribute instanceof EntityReferenceAttribute) {
							refAttributesFieldInfo.put(entityAttribute.getUid(), fieldInfo);
						}
					}
				}
				entities.add(entity);
			}
			if (!entities.isEmpty()) {
				// Second step - resolve references between entities
				for (Entity entity : entities) {
					for (EntityAttribute attr : entity.getAttributes()) {
						if (attr instanceof EntityReferenceAttribute) {
							FieldInfo fieldInfo = refAttributesFieldInfo.get(attr.getUid());
							if (fieldInfo != null) {
								for (ClassGraphEntityBuilder builder : builders) {
									if (builder.isApplicableFor(fieldInfo)) {
										builder.resolveReferenceAttribute(
													(EntityReferenceAttribute) attr,
													fieldInfo,
													entities
												);
									}
								}
							}
						}
					}
				}
			}
			
			return entities;
		}

	}
	
}
