package com.erm4j.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.erm4j.core.constant.Multiplicity;

/***
 * Specifies a {@link ModelEntity} attribute 
 * that references other {@link ModelEntity}  
 * 
 * @author skadnikov
 *
 */
@Target(value = {ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
public @interface ModelEntityReference {
	
	/***
	 * Relation multiplicity from the possible list of  
	 * {@link Multiplicity} enumeration
	 * @return
	 */
	Multiplicity multiplicity() default Multiplicity.ONE_TO_ONE;
	
	/***
	 * Identifies if a relation must be checked with a foreign key
	 * constraint
	 * @return
	 */
	boolean foreignKey() default true;
	
	/***
	 * Specifies target entity that is referenced by annotated attribute
	 * @return
	 */
	Class<?> target();

}
