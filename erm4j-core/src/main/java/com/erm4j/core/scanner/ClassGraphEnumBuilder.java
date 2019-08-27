package com.erm4j.core.scanner;

import com.erm4j.core.bean.Enumeration;
import com.erm4j.core.bean.EnumerationItem;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.ScanResult;

/***
 * Builder capable of building {@link Enumeration} objects
 * from metadata obtained from {@link ClassGraph} 
 * 
 * @author skadnikov
 *
 */
public interface ClassGraphEnumBuilder {

	/***
	 * Fills {@link Enumeration} object fields
	 * on the basis of metadata obtained from {@link ClassInfo}
	 * @param entity {@link Enumeration} object to fill
	 * @param classInfo Source {@link ClassInfo} that is identified as a candidate for entity
	 */
	public void fillEnumerationFields(Enumeration enumeration, ClassInfo classInfo); 
	
	/***
	 * Checks if class is applicable for building 
	 * enumeration with the builder
	 * @param classInfo
	 * @return
	 */
	public boolean isApplicableFor(ClassInfo classInfo);
	
	/***
	 * Returns list of classes from {@link ScanResult} applicable 
	 * for building enumeration with the builder 
	 * @param scanResult
	 * @return
	 */
	public ClassInfoList getApplicableClassList(ScanResult scanResult);

	/***
	 * Checks if field is applicable for building 
	 * enumeration with the builder
	 * @param fieldInfo
	 * @return
	 */
	public boolean isApplicableFor(FieldInfo fieldInfo);

	/***
	 * Fills {@link EnumerationItem} object fields
	 * on the basis of metadata obtained from {@link FieldInfo}
	 * @param item
	 * @param fieldInfo
	 * @param classInfo
	 */
	public void fillEnumerationItemFields(EnumerationItem item, FieldInfo fieldInfo);

}
