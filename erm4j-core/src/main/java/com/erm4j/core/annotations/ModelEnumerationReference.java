package com.erm4j.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Specifies a {@link ModelEntity} attribute 
 * that references {@link ModelEnumeration}  
 * 
 * @author skadnikov
 *
 */
@Target(value = {ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
public @interface ModelEnumerationReference {
	
	/***
	 * Specifies target enumeration that is referenced by annotated attribute
	 * @return
	 */
	Class<@ModelEnumeration ?> target();

}
