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

	private boolean logErrorsToStdOut = false;
	private String packageScanMask = "";
	private List<ClassGraphEntityBuilder> builders = new ArrayList<>();
	private DBTableNamingConventions tableNamingConventions = new DBTableNamingConventions();
	
	/***
	 * Sets if class scanning will be logged to stdout.
	 * Default is false
	 * @param logErrorsToStdOut
	 * @return
	 */
	public ERMScanner setLogErrorsToStdOut(boolean logErrorsToStdOut) {
		this.logErrorsToStdOut = logErrorsToStdOut;
		return this;
	}

	/***
	 * Sets package mask for classes which would be scanned.
	 * Therefore if mask would 'com.abc.core' than only classes that
	 * are included into 'com.abc.core.*' would be scanned
	 * @param packageScanMask
	 * @return
	 */
	public ERMScanner setPackageScanMask(String packageScanMask) {
		this.packageScanMask = packageScanMask;
		return this;
	}
	
	/***
	 * Adds {@link ClassGraphEntityBuilder} which will process
	 * class metadata and extract {@link Entity} objects
	 * @param builder
	 * @return
	 */
	public ERMScanner addEntityBuilder(ClassGraphEntityBuilder builder) {
		this.builders.add(builder);
		return this;
	}
	/***
	 * Sets prefix that would prepend generated table name
	 * i.e. if prefix is 'SBT_' and generated table name is 'order'
	 * that table name will be 'SBT_Order'

	 * @param tablePrefix
	 * @return
	 */
	public ERMScanner setTablePrefix(String tablePrefix) {
		tableNamingConventions.setTableNamePrefix(tablePrefix);
		return this;
	}
	
	/***
	 * Sets if snake case naming must be used.
	 * If true that 'ClientOrderItem' entity name would be converted to 'CLIENT_ORDER_ITEM'
	 * table name. Default value is true
	 * @param useSnakeCase
	 * @return
	 */
	public ERMScanner setUseSnakeCase(boolean useSnakeCase) {
		tableNamingConventions.setUseSnakeCaseNaming(useSnakeCase);
		return this;
	}
	
	/***
	 * Performs scanning of classes and returns list of {@link Entity} objects
	 * that were found by {@link ClassGraphEntityBuilder} objects added 
	 * to a list of entity builders 
	 * @return
	 */
	public List<Entity> scan() {
		ClassGraph classGraph = new ClassGraph()
									.enableAllInfo()             // Scan everything
							         .whitelistPackages(packageScanMask);  // Scan packageScanMask subpackages (omit to scan all packages)
		if (logErrorsToStdOut) {
			classGraph = classGraph.verbose();
		}
		
		try (ScanResult scanResult = classGraph.scan()) { // Start the scan
			
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
