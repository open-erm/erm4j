package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import com.erm4j.core.constant.ModelDomainType;

/***
 * {@link EntityAttribute} that references {@link Enumeration}
 * 
 * @author skadnikov
 *
 */
public class EntityEnumAttribute extends EntityAttribute {

	@JsonProperty("target_enum_ref")
	private AccessibleElementReference target = null;
	
	/***
	 * Returns a target {@link Enumeration} reference
	 * @return
	 */
	@JsonProperty("target_enum_ref")
	public AccessibleElementReference getTarget() {
		return target;
	}
	
	/***
	 * Sets a target {@link Enumeration} reference
	 * @param target
	 */
	@JsonProperty("target_enum_ref")
	public void setTarget(AccessibleElementReference target) {
		this.target = target;
	}

	public EntityEnumAttribute() {
		this.domainType = ModelDomainType.ENUM;
		this.nullable = false;
		this.primaryKey = false;
	}
}
