package com.erm4j.core.scanner;

import java.util.List;

import com.erm4j.core.bean.Entity;
import com.erm4j.core.bean.EntityAttribute;
import com.erm4j.core.bean.EntityReferenceAttribute;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.ScanResult;

/***
 * Builder capable of building {@link Entity} objects
 * from metadata obtained from {@link ClassGraph} 
 * 
 * @author skadnikov
 *
 */
public interface ClassGraphEntityBuilder {

	/***
	 * Fills {@link Entity} object fields
	 * on the basis of metadata obtained from {@link ClassInfo}
	 * @param entity {@link Entity} object to fill
	 * @param classInfo Source {@link ClassInfo} that is identified as a candidate for entity
	 * @param tableNamingConventions Table naming conventions to use for DB table attributes generation
	 */
	public void fillEntityFields(Entity entity, ClassInfo classInfo, DBTableNamingConventions tableNamingConventions); 
	
	/***
	 * Fills {@link EntityAttribute} object fields 
	 * on the basis of metadata obtained  from {@link FieldInfo}
	 * @param entityAttribute
	 * @param fieldInfo
	 * @param tableNamingConventions
	 */
	public void fillEntityAttributeFields(EntityAttribute entityAttribute, FieldInfo fieldInfo, DBTableNamingConventions tableNamingConventions); 
	
	/***
	 * Checks if class is applicable for building 
	 * entities with the builder
	 * @param classInfo
	 * @return
	 */
	public boolean isApplicableFor(ClassInfo classInfo);
	
	/***
	 * Checks if field is applicable for being field with this builder
	 * @param fieldInfo
	 * @return
	 */
	public boolean isApplicableFor(FieldInfo fieldInfo);
	
	/***
	 * Returns list of classes from {@link ScanResult} applicable 
	 * for building entities with the builder 
	 * @param scanResult
	 * @return
	 */
	public ClassInfoList getApplicableClassList(ScanResult scanResult);

	/***
	 * Checks if given {@link FieldInfo} represents {@link EntityReferenceAttribute}
	 * @param fieldInfo
	 * @return
	 */
	public boolean isReferenceAttribute(FieldInfo fieldInfo);
	
	/***
	 * Resolves {@link EntityReferenceAttribute} fields on the basis
	 * of metadata in {@link FieldInfo} and full collection of {@link Entity}
	 * objects
	 * @param attr
	 * @param fieldInfo
	 * @param entities
	 */
	public void resolveReferenceAttribute(EntityReferenceAttribute attr, FieldInfo fieldInfo, List<Entity> entities);
}
