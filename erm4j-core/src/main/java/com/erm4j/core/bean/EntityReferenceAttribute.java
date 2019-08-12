package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import com.erm4j.core.constant.ModelDomainType;
import com.erm4j.core.constant.Multiplicity;

/***
 * {@link EntityAttribute} that references other {@link Entity}
 * 
 * @author skadnikov
 *
 */
public class EntityReferenceAttribute extends EntityAttribute {

	@JsonProperty("target_entity_ref")
	private AccessibleElementReference target = null;
	
	@JsonProperty("multiplicity")
	private Multiplicity multiplicity = Multiplicity.ONE_TO_ONE;
	
	@JsonProperty("fk")
	private boolean foreignKey = true;
	
	
	/***
	 * Returns a target {@link Entity} reference
	 * @return
	 */
	@JsonProperty("target_entity_ref")
	public AccessibleElementReference getTarget() {
		return target;
	}
	
	/***
	 * Sets a target {@link Entity} reference
	 * @param target
	 */
	@JsonProperty("target_entity_ref")
	public void setTarget(AccessibleElementReference target) {
		this.target = target;
	}

	/***
	 * Returns multiplicity of a relation
	 * @return
	 */
	@JsonProperty("multiplicity")
	public Multiplicity getMultiplicity() {
		return multiplicity;
	}

	/***
	 * Sets multiplicity of a relation
	 * @param multiplicity
	 */
	@JsonProperty("multiplicity")
	public void setMultiplicity(Multiplicity multiplicity) {
		this.multiplicity = multiplicity;
	}

	/***
	 * Identifies if an attribute is controlled by foreign key constraint
	 * @return
	 */
	@JsonProperty("fk")
	public boolean isForeignKey() {
		return foreignKey;
	}
	
	/***
	 * Sets if an attribute is controlled by foreign key constraint
	 * 
	 * @param foreignKey
	 */
	@JsonProperty("fk")
	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}
	
	public EntityReferenceAttribute() {
		this.domainType = ModelDomainType.ENTITY;
		this.nullable = false;
		this.primaryKey = false;
	}
}
