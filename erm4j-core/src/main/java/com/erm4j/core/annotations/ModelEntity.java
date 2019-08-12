package com.erm4j.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Specifies a class as a persistent entity that will take part
 * in a generated entity relation model
 * 
 * @author skadnikov
 *
 */
@Target(value = {ElementType.TYPE, ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention (RetentionPolicy.RUNTIME)
public @interface ModelEntity {
	
	/***
	 * Unique identifier of an entity in the model and or set of models.
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
	 * Human-readable short logical name of the entity 
	 * @return
	 */
	String name() default "";

	/***
	 * Name of a DB table that is mapped to an entity
	 * @return
	 */
	String table() default "";

	/***
	 * Business description of an entity
	 * @return
	 */
	String description() default "";
}
