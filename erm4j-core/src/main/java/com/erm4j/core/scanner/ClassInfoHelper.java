package com.erm4j.core.scanner;

import org.apache.commons.lang3.StringUtils;

import com.erm4j.core.bean.ModelElement;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;

/***
 * Helper class for building {@link ModelElement} objects from {@link ClassGraph}
 * provided {@link ClassInfo} objects 
 * @author root
 *
 */
public class ClassInfoHelper {
	
	private static final String ENTITY_POSTFIX = "entity";

	public static String generateClassUID(ClassInfo classInfo) {
		return classInfo.getName();
	}

	public static String generateClassFieldUID(ClassInfo classInfo, FieldInfo fieldInfo) {
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

	public static String generateClassSystemName(ClassInfo classInfo) {
		return classInfo.getSimpleName();
	}

	public static String generateClassFieldSystemName(FieldInfo fieldInfo) {
		return uppercaseFirstLetter(fieldInfo.getName());
	}

	public static String generateEntityName(ClassInfo classInfo) {
		String entitySystemName = generateEntitySystemName(classInfo);
		return splitCamelCase(entitySystemName);
	}

	public static String generateClassName(ClassInfo classInfo) {
		String classSystemName = generateClassSystemName(classInfo);
		return splitCamelCase(classSystemName);
	}

	public static String generateClassFieldName(FieldInfo fieldInfo) {
		String attrSystemName = generateClassFieldSystemName(fieldInfo);
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
		String attrSystemName = generateClassFieldSystemName(fieldInfo);
		if (tableNaming.isUseSnakeCaseNaming()) {
			return camelCaseToSnakeCase(attrSystemName).toUpperCase();
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
