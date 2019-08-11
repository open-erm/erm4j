package com.erm4j.core.bean;

import com.erm4j.core.constant.ModelDomainType;
import com.erm4j.core.constant.Multiplicity;

/***
 * {@link EntityAttribute} that references other {@link Entity}
 * 
 * @author skadnikov
 *
 */
public class EntityReferenceAttribute extends EntityAttribute {

	private String targetEntityUid;
	
	private Multiplicity multiplicity = Multiplicity.ONE_TO_ONE;
	
	private boolean foreignKey = true;
	
	/***
	 * Returns multiplicity of a relation
	 * @return
	 */
	public Multiplicity getMultiplicity() {
		return multiplicity;
	}

	/***
	 * Sets multiplicity of a relation
	 * @param multiplicity
	 */
	public void setMultiplicity(Multiplicity multiplicity) {
		this.multiplicity = multiplicity;
	}

	/***
	 * Returns UID of target entity that is referenced by attribute
	 * @return
	 */
	public String getTargetEntityUid() {
		return targetEntityUid;
	}

	/***
	 * Sets UID of target entity that is referenced by attribute
	 * @param targetEntityUid
	 */
	public void setTargetEntityUid(String targetEntityUid) {
		this.targetEntityUid = targetEntityUid;
	}
	
	/***
	 * Identifies if an attribute is controlled by foreign key constraint
	 * @return
	 */
	public boolean isForeignKey() {
		return foreignKey;
	}
	
	/***
	 * Sets if an attribute is controlled by foreign key constraint
	 * 
	 * @param foreignKey
	 */
	public void setForeignKey(boolean foreignKey) {
		this.foreignKey = foreignKey;
	}
	
	public EntityReferenceAttribute() {
		this.domainType = ModelDomainType.ENTITY;
		this.nullable = false;
		this.primaryKey = false;
	}
}
