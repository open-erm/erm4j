package com.erm4j.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Specifies enumeration item with extra model attributes
 * 
 * @author skadnikov
 *
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
public @interface ModelEnumerationItem {
	
	/***
	 * Unique identifier of enumeration item in the model and or set of models.
	 * For example, abcd-1234-defxy
	 * @return
	 */
	String uid() default "";

	/***
	 * Human-readable short system name of the item 
	 * @return
	 */
	String systemName() default "";

	/***
	 * Human-readable short logical name of the item 
	 * @return
	 */
	String name() default "";
	
	/***
	 * Business description of an item
	 * @return
	 */
	String description() default "";
}
