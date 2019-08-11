package com.erm4j.core.bean;

import com.erm4j.core.constant.ModelDomainType;

/***
 * Defines an attribute of an {@link Entity}
 * 
 * @author skadnikov
 *
 */
public class EntityAttribute extends AccessibleElement {

	protected ModelDomainType domainType = ModelDomainType.UNDEFINED;

	private String column = "";
	
	protected boolean primaryKey = false;
	
	protected boolean unique= false;
	
	protected boolean nullable = true;

	/***
	 * Returns {@link ModelDomainType} associated with an attribute
	 * @return
	 */
	public ModelDomainType getDomainType() {
		return domainType;
	}
	
	/***
	 * Sets {@link ModelDomainType} associated with an attribute
	 * @param domainType
	 */
	public void setDomainType(ModelDomainType domainType) {
		this.domainType = domainType;
	}

	/***
	 * Returns name of DB table column mapped to an entity attribute
	 * @return
	 */
	public String getColumn() {
		return column;
	}
	
	/***
	 * Sets name of DB table column mapped to an entity attribute
	 * @param column
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/***
	 * Identifies if an attribute is a part of primary key
	 * @return
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/***
	 * Sets if an attribute a part of primary key
	 * @param primaryKey
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/***
	 * Identifies if an attribute value is unique in a table records
	 * @return
	 */
	public boolean isUnique() {
		return unique;
	}

	/***
	 * Sets if an attribute value is unique in a table records
	 * @param unique
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/***
	 * Identifies if an attribute value could be null
	 * @return
	 */
	public boolean isNullable() {
		return nullable;
	}

	/***
	 * Sets if an attribute value could be null
	 * @param nullable
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	
	

}
