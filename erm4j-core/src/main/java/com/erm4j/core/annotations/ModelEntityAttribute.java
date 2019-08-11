package com.erm4j.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.erm4j.core.constant.ModelDomainType;

/***
 * Specifies a field or a method as a persistent entity attribute
 * 
 * @author skadnikov
 *
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
public @interface ModelEntityAttribute {

	/***
	 * Unique identifier of an entity attribute in the model and or set of models.
	 * For example, abcd-1234-defxy
	 * @return
	 */
	String uid() default "";
	
	/***
	 * Human-readable short system name of the entity 
	 * @return
	 */
	String systemName() default "";

	/***
	 * Human-readable short logical name of the attribute 
	 * @return
	 */
	String name() default "";
	
	/***
	 * Business description of an attribute
	 * @return
	 */
	String description() default "";
	
	/***
	 * Defines name of column to which entity attribute is mapped
	 * @return
	 */
	String column() default "";
	
	/***
	 * Indicates that attribute composes primary key for enclosing {@link ModelEntity}
	 * @return
	 */
	boolean primaryKey() default false;
	
	/***
	 * Indicates that attribute must have unique value across entity records
	 * @return
	 */
	boolean unique() default false;
	
	/***
	 * Indicates that attribute can have null value;
	 * @return
	 */
	boolean nullable() default true;
	
	/***
	 * Specifies length of string value attribute if length is not defined
	 * by {@link ModelEntityAttribute#domainType()}
	 * @return
	 */
	int length() default -1;
	
	/***
	 * Domain type of an attribute from the list of possible {@link ModelDomainType}
	 * @return
	 */
	ModelDomainType domainType() default ModelDomainType.UNDEFINED;
}
