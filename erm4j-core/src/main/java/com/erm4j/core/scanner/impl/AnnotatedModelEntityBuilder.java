package com.erm4j.core.scanner.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.erm4j.core.annotations.ModelEntity;
import com.erm4j.core.bean.Entity;
import com.erm4j.core.bean.EntityAttribute;
import com.erm4j.core.bean.EntityReferenceAttribute;
import com.erm4j.core.constant.ModelDomainType;
import com.erm4j.core.constant.Multiplicity;
import com.erm4j.core.scanner.ClassGraphEntityBuilder;
import com.erm4j.core.scanner.DBTableNamingConventions;
import com.erm4j.core.scanner.ClassInfoHelper;

import io.github.classgraph.AnnotationClassRef;
import io.github.classgraph.AnnotationEnumValue;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationParameterValue;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.ScanResult;

/***
 * Implementation of {@link ClassGraphEntityBuilder} 
 * capable of building {@link Entity} from classes annotated with {@link ModelEntity}
 * @author skadnikov
 *
 */
public class AnnotatedModelEntityBuilder implements ClassGraphEntityBuilder {
	
	private final static String ENTITY_ANNOTATION_NAME = "com.erm4j.core.annotations.ModelEntity";
	private static final String ATTRIBUTE_ANNOTATION_NAME = "com.erm4j.core.annotations.ModelEntityAttribute";
	private static final String REFERENCE_ANNOTATION_NAME = "com.erm4j.core.annotations.ModelEntityReference";

	private final static String UID_PARAM_NAME = "uid";
	private final static String SYSTEM_NAME_PARAM_NAME = "systemName";
	private final static String NAME_PARAM_NAME = "name";
	private final static String TABLE_PARAM_NAME = "table";
	private final static String DESCRIPTION_PARAM_NAME = "description";

	private final static String COLUMN_PARAM_NAME = "column";
	private final static String PK_PARAM_NAME = "primaryKey";
	private final static String UNIQUE_PARAM_NAME = "unique";
	private final static String NULLABLE_PARAM_NAME = "nullable";
	private final static String DOMAIN_TYPE_PARAM_NAME = "domainType";

	private final static String MULTIPLICITY_PARAM_NAME = "multiplicity";
	private final static String FK_PARAM_NAME = "foreignKey";
	private final static String TARGET_PARAM_NAME = "target";

	@Override
	public void fillEntityFields(Entity entity, ClassInfo classInfo, DBTableNamingConventions tableNaming) {
		// Filling entity fields from ModelEntity annotation
		AnnotationInfo annotationInfo = classInfo.getAnnotationInfo(ENTITY_ANNOTATION_NAME);
		if (annotationInfo != null) {
			for (AnnotationParameterValue param : annotationInfo.getParameterValues()) {
				fillEntityFieldsFromAnnotationParam(entity, param);
			}
			
			//If no annotation parameters found - we preset fields with autogenerated values
			fillEntityFieldsWithAutogeneratedValues(entity, classInfo, tableNaming);
		}
	}

	@Override
	public boolean isApplicableFor(ClassInfo classInfo) {
		return classInfo != null && classInfo.hasAnnotation(ENTITY_ANNOTATION_NAME);
	}

	@Override
	public ClassInfoList getApplicableClassList(ScanResult scanResult) {
		return scanResult.getClassesWithAnnotation(ENTITY_ANNOTATION_NAME);
	}
	
	@Override
	public void fillEntityAttributeFields(EntityAttribute attribute, FieldInfo fieldInfo,
			DBTableNamingConventions tableNamingConventions) {
		AnnotationInfo annotationInfo = fieldInfo.getAnnotationInfo(ATTRIBUTE_ANNOTATION_NAME);
		if (annotationInfo != null) {
			for (AnnotationParameterValue param : annotationInfo.getParameterValues()) {
				fillAttributeFieldsFromAnnotationParam(attribute, param);
			}
			
			//If no annotation parameters found - we preset fields with autogenerated values
			fillAttributeFieldsWithAutogeneratedValues(attribute, fieldInfo.getClassInfo(), fieldInfo, tableNamingConventions);
		}
	}

	@Override
	public boolean isApplicableFor(FieldInfo fieldInfo) {
		return fieldInfo.hasAnnotation(ATTRIBUTE_ANNOTATION_NAME);
	}

	@Override
	public boolean isReferenceAttribute(FieldInfo fieldInfo) {
		return fieldInfo.hasAnnotation(REFERENCE_ANNOTATION_NAME);
	}
	
	@Override
	public void resolveReferenceAttribute(EntityReferenceAttribute attr, FieldInfo fieldInfo, List<Entity> entities) {
		AnnotationInfo annotationInfo = fieldInfo.getAnnotationInfo(REFERENCE_ANNOTATION_NAME);
		if (annotationInfo != null) {
			for (AnnotationParameterValue param : annotationInfo.getParameterValues()) {
				Object value = param.getValue();
				if (param.getName().equalsIgnoreCase(TARGET_PARAM_NAME)) {
					if (value instanceof AnnotationClassRef) {
						AnnotationClassRef classRef = (AnnotationClassRef) value;
						// We need to extract entity UID from class that 
						// is referenced in annotation
						ClassInfo targetClassInfo = classRef.getClassInfo();
						String targetEntityUID = lookupTargetEntityUID(targetClassInfo);
						if (StringUtils.isNotBlank(targetEntityUID)) {
							// Looking for entity with found uid in global context
							Entity targetEntity = entities.stream()
													.filter(e -> e.getUid().equalsIgnoreCase(targetEntityUID))
													.findFirst()
													.orElse(null);
							if (attr.getTarget() == null) {
								attr.setTarget(targetEntity);
							}
						}
					}
				}
				else if (param.getName().equalsIgnoreCase(FK_PARAM_NAME)) {
					Boolean isFK = Boolean.valueOf(value.toString());
					attr.setForeignKey(isFK);
				}
				else if (param.getName().equalsIgnoreCase(MULTIPLICITY_PARAM_NAME)) {
					if (value instanceof AnnotationEnumValue) {
						AnnotationEnumValue enumVal = (AnnotationEnumValue) value;
						Multiplicity multiplicity = (Multiplicity) enumVal.loadClassAndReturnEnumValue();
						attr.setMultiplicity(multiplicity);
					}
				}
			}

		}
	}

	private String lookupTargetEntityUID(ClassInfo targetClassInfo) {
		String targetEntityUID = null;
		if (targetClassInfo.hasAnnotation(ENTITY_ANNOTATION_NAME)) {
			AnnotationInfo entityAnnottion = targetClassInfo.getAnnotationInfo(ENTITY_ANNOTATION_NAME);
			// Looking for uid parameter in referenced class annotation
			for (AnnotationParameterValue refParam : entityAnnottion.getParameterValues()) {
				// If it is present and not empty - we take it as a entity uid
				if (refParam.getName().equalsIgnoreCase(UID_PARAM_NAME)) {
					String annotatedUid = refParam.getValue().toString();
					if (StringUtils.isNotBlank(annotatedUid)) {
						targetEntityUID = annotatedUid;
					}
				}
			}
			if (StringUtils.isBlank(targetEntityUID)) {
				targetEntityUID = ClassInfoHelper
									.generateEntityUID(targetClassInfo);
			}
		}
		return targetEntityUID;
	}


	private void fillAttributeFieldsWithAutogeneratedValues(EntityAttribute attribute, ClassInfo classInfo,
			FieldInfo fieldInfo, DBTableNamingConventions tableNaming) {
		if (StringUtils.isBlank(attribute.getUid())) {
			attribute.setUid(ClassInfoHelper
								.generateEntityAttributeUID(classInfo, fieldInfo));
		}
		
		if (StringUtils.isBlank(attribute.getSystemName())) {
			attribute.setSystemName(
						ClassInfoHelper
							.generateEntityAttributeSystemName(fieldInfo)
					);
		}

		if (StringUtils.isBlank(attribute.getName())) {
			attribute.setName(
					ClassInfoHelper
						.generateEntityAttributeName(fieldInfo)
				);
		}
		
		if (StringUtils.isBlank(attribute.getColumn())) {
			attribute.setColumn(
					ClassInfoHelper
						.generateTableColumnName(fieldInfo,tableNaming)
				);
		}
	}

	private void fillAttributeFieldsFromAnnotationParam(EntityAttribute attribute, AnnotationParameterValue param) {
		Object value = param.getValue();
		if (param.getName().equalsIgnoreCase(UID_PARAM_NAME)) {
			// Annotated UID has a priority over auto-generated
			String annotatedUID = value.toString();
			if (StringUtils.isNotBlank(annotatedUID)) {
				attribute.setUid(annotatedUID);
			}
			
		}
		else if (param.getName().equalsIgnoreCase(SYSTEM_NAME_PARAM_NAME)) {
			String annotatedSystemName = value.toString();
			// Annotated system name has a priority over auto-generated
			if (StringUtils.isNotBlank(annotatedSystemName)) {
				attribute.setSystemName(annotatedSystemName);
			}
		}
		else if (param.getName().equalsIgnoreCase(NAME_PARAM_NAME)) {
			String annotatedName = value.toString();
			// Annotated name has a priority over auto-generated
			if (StringUtils.isNotBlank(annotatedName)) {
				attribute.setName(annotatedName);
			}
		}
		else if (param.getName().equalsIgnoreCase(DESCRIPTION_PARAM_NAME)) {
			String annotatedDescription = value.toString();
			if (StringUtils.isNotBlank(annotatedDescription)) {
				attribute.setDescription(annotatedDescription);
			}
		}
		else if (param.getName().equalsIgnoreCase(COLUMN_PARAM_NAME)) {
			String annotatedColumn = value.toString();
			// Annotated column name has a priority over auto-generated
			if (StringUtils.isNotBlank(annotatedColumn)) {
				attribute.setColumn(annotatedColumn);
			}
		}
		else if (param.getName().equalsIgnoreCase(PK_PARAM_NAME)) {
			Boolean isPK = Boolean.valueOf(value.toString());
			attribute.setPrimaryKey(isPK);
		}
		else if (param.getName().equalsIgnoreCase(UNIQUE_PARAM_NAME)) {
			Boolean isUnique = Boolean.valueOf(value.toString());
			attribute.setUnique(isUnique);
		}
		else if (param.getName().equalsIgnoreCase(NULLABLE_PARAM_NAME)) {
			Boolean isNullable = Boolean.valueOf(value.toString());
			attribute.setNullable(isNullable);
		}
		// We set type only for simple attributes
		// for reference attributes it is by default equals to ENTITY
		else if (param.getName().equalsIgnoreCase(DOMAIN_TYPE_PARAM_NAME) && 
					!(attribute instanceof EntityReferenceAttribute)) {
			if (value instanceof AnnotationEnumValue) {
				AnnotationEnumValue enumVal = (AnnotationEnumValue) value;
				ModelDomainType domainType = (ModelDomainType) enumVal.loadClassAndReturnEnumValue();
				attribute.setDomainType(domainType);
			}
		}
	}

	private void fillEntityFieldsFromAnnotationParam(Entity entity, AnnotationParameterValue param) {
		Object value = param.getValue();
		if (param.getName().equalsIgnoreCase(UID_PARAM_NAME)) {
			// Annotated UID has a priority over auto-generated
			String annotatedUID = value.toString();
			if (StringUtils.isNotBlank(annotatedUID)) {
				entity.setUid(annotatedUID);
			}
		}
		else if (param.getName().equalsIgnoreCase(SYSTEM_NAME_PARAM_NAME)) {
			String annotatedSystemName = value.toString();
			// Annotated system name has a priority over auto-generated
			if (StringUtils.isNotBlank(annotatedSystemName)) {
				entity.setSystemName(annotatedSystemName);
			}
		}
		else if (param.getName().equalsIgnoreCase(NAME_PARAM_NAME)) {
			String annotatedName = value.toString();
			// Annotated name has a priority over auto-generated
			if (StringUtils.isNotBlank(annotatedName)) {
				entity.setName(annotatedName);
			}
		}
		else if (param.getName().equalsIgnoreCase(DESCRIPTION_PARAM_NAME)) {
			String annotatedDescription = value.toString();
			if (StringUtils.isNotBlank(annotatedDescription)) {
				entity.setDescription(annotatedDescription);
			}
		}
		else if (param.getName().equalsIgnoreCase(TABLE_PARAM_NAME)) {
			String annotatedTableName = value.toString();
			// Annotated table name has a priority over auto-generated
			if (StringUtils.isNotBlank(annotatedTableName)) {
				entity.setTable(annotatedTableName);
			}
		}
	}

	private void fillEntityFieldsWithAutogeneratedValues(Entity entity, ClassInfo classInfo,
			DBTableNamingConventions tableNaming) {
		if (StringUtils.isBlank(entity.getUid())) {
			entity.setUid(ClassInfoHelper.generateEntityUID(classInfo));
		}
		
		if (StringUtils.isBlank(entity.getSystemName())) {
			entity.setSystemName(
						ClassInfoHelper
							.generateEntitySystemName(classInfo)
					);
		}
		if (StringUtils.isBlank(entity.getName())) {
			entity.setName(
					ClassInfoHelper
						.generateEntityName(classInfo)
				);
		}
		if (StringUtils.isBlank(entity.getTable())) {
			entity.setTable(
					ClassInfoHelper
						.generateTableName(classInfo,tableNaming)
					);
		}
	}


	

}
