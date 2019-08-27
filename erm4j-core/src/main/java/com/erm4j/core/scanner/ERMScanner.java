package com.erm4j.core.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.erm4j.core.bean.Entity;
import com.erm4j.core.bean.EntityAttribute;
import com.erm4j.core.bean.EntityEnumAttribute;
import com.erm4j.core.bean.EntityReferenceAttribute;
import com.erm4j.core.bean.Enumeration;
import com.erm4j.core.bean.EnumerationItem;

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
	private String entityPackageScanMask = "";
	private String enumPackageScanMask = "";
	private List<ClassGraphEntityBuilder> entityBuilders = new ArrayList<>();
	private List<ClassGraphEnumBuilder> enumBuilders = new ArrayList<>();
	private DBTableNamingConventions tableNamingConventions = new DBTableNamingConventions();
	private ModelScanResult modelScanResult;
	
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
	 * Sets package mask for classes which would be scanned for building enumerations.
	 * Therefore if mask would 'com.abc.core' than only classes that
	 * are included into 'com.abc.core.*' would be scanned
	 * @param enumPackageScanMask
	 * @return
	 */
	public ERMScanner setEnumPackageScanMask(String enumPackageScanMask) {
		this.enumPackageScanMask = enumPackageScanMask;
		return this;
	}

	/***
	 * Sets package mask for classes which would be scanned for building entities.
	 * Therefore if mask would 'com.abc.core' than only classes that
	 * are included into 'com.abc.core.*' would be scanned
	 * @param entityPackageScanMask
	 * @return
	 */
	public ERMScanner setEntityPackageScanMask(String entityPackageScanMask) {
		this.entityPackageScanMask = entityPackageScanMask;
		return this;
	}
	
	/***
	 * Adds {@link ClassGraphEntityBuilder} which will process
	 * class metadata and extract {@link Entity} objects
	 * @param builder
	 * @return
	 */
	public ERMScanner addEntityBuilder(ClassGraphEntityBuilder builder) {
		this.entityBuilders.add(builder);
		return this;
	}

	/***
	 * Adds {@link ClassGraphEnumBuilder} which will process
	 * class metadata and extract {@link Entity} objects
	 * @param builder
	 * @return
	 */
	public ERMScanner addEnumerationBuilder(ClassGraphEnumBuilder builder) {
		this.enumBuilders.add(builder);
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
	 * Performs scanning of classes and returns {@link ModelScanResult} containing entities
	 * that were found in packages defined by {@link ERMScanner#setEntityPackageScanMask(String)} 
	 * @return
	 */
	public ModelScanResult scan() {
		modelScanResult = new ModelScanResult();
		scanEnumerations();
		scanEntities();
		return modelScanResult;
	}

	private void scanEnumerations() {
		ClassGraph classGraph = new ClassGraph()
									.enableAllInfo()             // Scan everything
							         .whitelistPackages(enumPackageScanMask);  // Scan packageScanMask subpackages (omit to scan all packages)
		if (logErrorsToStdOut) {
			classGraph = classGraph.verbose();
		}

		try (ScanResult scanResult = classGraph.scan()) { // Start the scan
			//Building list of unique classes suitable for building enumerations
			Map<String, ClassInfo> classInfoMap = new HashMap<String, ClassInfo>();
			for (ClassGraphEnumBuilder enumBuilder : enumBuilders) {
				ClassInfoList applicableClassList = enumBuilder.getApplicableClassList(scanResult);
				for (ClassInfo classInfo : applicableClassList) {
					if (classInfo.isEnum() && !classInfoMap.containsKey(classInfo.getName())) {
						classInfoMap.put(classInfo.getName(), classInfo);
					}
				}
			}
			
			for (ClassInfo classInfo : classInfoMap.values()) {
				Enumeration enumeration = new Enumeration();
				// We are sure that one of builders is applicable for building enumeration
				// because earlier we preselected only applicable classes
				for (ClassGraphEnumBuilder builder : enumBuilders) {
					if (builder.isApplicableFor(classInfo)) {
						builder.fillEnumerationFields(enumeration, classInfo);
					}
				}
				
				for (FieldInfo fieldInfo : classInfo.getFieldInfo()) {
					EnumerationItem item = new EnumerationItem();
					boolean itemFilled = false;
					for (ClassGraphEnumBuilder builder : enumBuilders) {
						if (builder.isApplicableFor(fieldInfo)) {
							builder.fillEnumerationItemFields(item,fieldInfo);
							itemFilled = true;
						}
					}
					if (itemFilled) {
						enumeration.getItems().add(item);
					}
				}
				modelScanResult.getEnumerations().add(enumeration);
			}
		}

	}

	private void scanEntities() {
		ClassGraph classGraph = new ClassGraph()
									.enableAllInfo()             // Scan everything
							         .whitelistPackages(entityPackageScanMask);  // Scan packageScanMask subpackages (omit to scan all packages)
		if (logErrorsToStdOut) {
			classGraph = classGraph.verbose();
		}
		
		try (ScanResult scanResult = classGraph.scan()) { // Start the scan
			
			//Building list of unique classes suitable for building entities
			Map<String, ClassInfo> classInfoMap = new HashMap<String, ClassInfo>();
			for (ClassGraphEntityBuilder entityBuilder : entityBuilders) {
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
			
			// Map of attribute.uid - FieldInfo needed for entity references resolutions
			Map<String, FieldInfo> refAttributesFieldInfo = new HashMap<>();
			// First step - build entities with all attributes
			// and do not fill references
			for (ClassInfo classInfo : classInfoMap.values()) {
				Entity entity = new Entity();
				// We are sure that one of builders is applicable for building entity
				// because earlier we preselected only applicable classes
				for (ClassGraphEntityBuilder builder : entityBuilders) {
					if (builder.isApplicableFor(classInfo)) {
						builder.fillEntityFields(entity, classInfo, tableNamingConventions);
					}
				}
				// Filling basic attributes fields
				for (FieldInfo fieldInfo : classInfo.getFieldInfo()) {
					EntityAttribute entityAttribute = null;
					for (ClassGraphEntityBuilder builder : entityBuilders) {
						if (builder.isApplicableFor(fieldInfo)) {
							if (entityAttribute == null) {
								if (builder.isReferenceAttribute(fieldInfo)) {
									entityAttribute = new EntityReferenceAttribute();
								}
								else if (builder.isEnumAttribute(fieldInfo)) {
									entityAttribute = new EntityEnumAttribute();
									builder.fillEnumAttributeTarget(
													(EntityEnumAttribute) entityAttribute,
													fieldInfo,
													modelScanResult.getEnumerations()
											);
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
				modelScanResult.getEntities().add(entity);
			}
			if (!modelScanResult.getEntities().isEmpty()) {
				// Second step - resolve references between entities
				for (Entity entity : modelScanResult.getEntities()) {
					for (EntityAttribute attr : entity.getAttributes()) {
						if (attr instanceof EntityReferenceAttribute) {
							FieldInfo fieldInfo = refAttributesFieldInfo.get(attr.getUid());
							if (fieldInfo != null) {
								for (ClassGraphEntityBuilder builder : entityBuilders) {
									if (builder.isApplicableFor(fieldInfo)) {
										builder.resolveReferenceAttribute(
													(EntityReferenceAttribute) attr,
													fieldInfo,
													modelScanResult.getEntities()
												);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
}
