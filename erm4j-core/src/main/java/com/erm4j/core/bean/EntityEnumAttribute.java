package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.erm4j.core.constant.ModelDomainType;

/***
 * {@link EntityAttribute} that references {@link Enumeration}
 * 
 * @author skadnikov
 *
 */
@JsonPropertyOrder({"uid", "system_name", "name","description", "type", "pk", "unique","nullable","column", "target_enum_ref"})
public class EntityEnumAttribute extends EntityAttribute {

	@JsonProperty("target_enum_ref")
	private ModelElement target = null;
	
	/***
	 * Returns a target {@link Enumeration} reference
	 * @return
	 */
	@JsonProperty("target_enum_ref")
	public ModelElement getTarget() {
		return target;
	}
	
	/***
	 * Sets a target {@link Enumeration} reference
	 * @param target
	 */
	@JsonProperty("target_enum_ref")
	public void setTarget(ModelElement target) {
		this.target = target.createReference();
	}

	public EntityEnumAttribute() {
		this.domainType = ModelDomainType.ENUM;
		this.nullable = false;
		this.primaryKey = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityEnumAttribute other = (EntityEnumAttribute) obj;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}
	
}
