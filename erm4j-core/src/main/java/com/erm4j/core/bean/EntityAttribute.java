package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import com.erm4j.core.constant.ModelDomainType;

/***
 * Defines an attribute of an {@link Entity}
 * 
 * @author skadnikov
 *
 */
public class EntityAttribute extends AccessibleElement {

	@JsonProperty("type")
	protected ModelDomainType domainType = ModelDomainType.UNDEFINED;

	@JsonProperty("column")
	private String column = "";
	
	@JsonProperty("pk")
	protected boolean primaryKey = false;
	
	@JsonProperty("unique")
	protected boolean unique= false;
	
	@JsonProperty("nullable")
	protected boolean nullable = true;

	/***
	 * Returns {@link ModelDomainType} associated with an attribute
	 * @return
	 */
	@JsonProperty("type")
	public ModelDomainType getDomainType() {
		return domainType;
	}
	
	/***
	 * Sets {@link ModelDomainType} associated with an attribute
	 * @param domainType
	 */
	@JsonProperty("type")
	public void setDomainType(ModelDomainType domainType) {
		this.domainType = domainType;
	}

	/***
	 * Returns name of DB table column mapped to an entity attribute
	 * @return
	 */
	@JsonProperty("column")
	public String getColumn() {
		return column;
	}
	
	/***
	 * Sets name of DB table column mapped to an entity attribute
	 * @param column
	 */
	@JsonProperty("column")
	public void setColumn(String column) {
		this.column = column;
	}

	/***
	 * Identifies if an attribute is a part of primary key
	 * @return
	 */
	@JsonProperty("pk")
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/***
	 * Sets if an attribute a part of primary key
	 * @param primaryKey
	 */
	@JsonProperty("pk")
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/***
	 * Identifies if an attribute value is unique in a table records
	 * @return
	 */
	@JsonProperty("unique")
	public boolean isUnique() {
		return unique;
	}

	/***
	 * Sets if an attribute value is unique in a table records
	 * @param unique
	 */
	@JsonProperty("unique")
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/***
	 * Identifies if an attribute value could be null
	 * @return
	 */
	@JsonProperty("nullable")
	public boolean isNullable() {
		return nullable;
	}

	/***
	 * Sets if an attribute value could be null
	 * @param nullable
	 */
	@JsonProperty("nullable")
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

}
