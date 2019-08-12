package com.erm4j.core.scanner;

import org.apache.commons.lang3.StringUtils;

import com.erm4j.core.bean.Entity;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;

/***
 * Helper class for building {@link Entity} objects from {@link ClassGraph}
 * provided {@link ClassInfo} objects 
 * @author root
 *
 */
public class EntityBuilderClassInfoHelper {
	
	private static final String ENTITY_POSTFIX = "entity";

	public static String generateEntityUID(ClassInfo classInfo) {
		return classInfo.getName();
	}

	public static String generateEntityAttributeUID(ClassInfo classInfo, FieldInfo fieldInfo) {
		return classInfo.getName() + "_" + fieldInfo.getName();
	}

	public static String generateEntitySystemName(ClassInfo classInfo) {
		String shortClassName = classInfo.getSimpleName();
		String lcClassName = shortClassName.toLowerCase();
		if (lcClassName.endsWith(ENTITY_POSTFIX) && lcClassName.length() > ENTITY_POSTFIX.length()) {
			return shortClassName.substring(0, shortClassName.length() - ENTITY_POSTFIX.length());
		}
		return shortClassName;
	}
	
	public static String generateEntityAttributeSystemName(FieldInfo fieldInfo) {
		return uppercaseFirstLetter(fieldInfo.getName());
	}

	public static String generateEntityName(ClassInfo classInfo) {
		String entitySystemName = generateEntitySystemName(classInfo);
		return splitCamelCase(entitySystemName);
	}
	
	public static String generateEntityAttributeName(FieldInfo fieldInfo) {
		String attrSystemName = generateEntityAttributeSystemName(fieldInfo);
		return splitCamelCase(attrSystemName);
	}


	public static String generateTableName(ClassInfo classInfo, DBTableNamingConventions tableNaming) {
		String entitySystemName = generateEntitySystemName(classInfo);
		if (tableNaming.isUseSnakeCaseNaming()) {
			return tableNaming.getTableNamePrefix() + camelCaseToSnakeCase(entitySystemName).toUpperCase();
		}
		else {
			return tableNaming.getTableNamePrefix() + entitySystemName;
		}
	}
	
	public static String generateTableColumnName(FieldInfo fieldInfo, DBTableNamingConventions tableNaming) {
		String attrSystemName = generateEntityAttributeSystemName(fieldInfo);
		if (tableNaming.isUseSnakeCaseNaming()) {
			return camelCaseToSnakeCase(attrSystemName);
		}
		else {
			return attrSystemName;
		}
	}

	
	private static String uppercaseFirstLetter(String str) {
		if (StringUtils.isNotBlank(str) && str.length() > 0) {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

	private static String camelCaseToSnakeCase(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		return s.replaceAll("([^_A-Z])([A-Z])", "$1_$2");
	}
	
	private static String splitCamelCase(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		return s.replaceAll(
				String.format("%s|%s|%s",
						"(?<=[A-Z])(?=[A-Z][a-z])",
						"(?<=[^A-Z])(?=[A-Z])",
						"(?<=[A-Za-z])(?=[^A-Za-z])"
						),
					" "
				);
	}




}
