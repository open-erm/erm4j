package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import com.erm4j.core.constant.ModelDomainType;

/***
 * Defines an attribute of an {@link Entity}
 * 
 * @author skadnikov
 *
 */
@JsonPropertyOrder({"uid", "system_name", "name","description", "type", "pk", "unique","nullable","column"})
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "class")
		@JsonSubTypes({ 
		  @Type(value = EntityAttribute.class, name = "primitive-attribute"), 
		  @Type(value = EntityReferenceAttribute.class, name = "reference-attribute"), 
		  @Type(value = EntityEnumAttribute.class, name = "enum-attribute") 
		})
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((domainType == null) ? 0 : domainType.hashCode());
		result = prime * result + (nullable ? 1231 : 1237);
		result = prime * result + (primaryKey ? 1231 : 1237);
		result = prime * result + (unique ? 1231 : 1237);
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
		EntityAttribute other = (EntityAttribute) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (domainType != other.domainType)
			return false;
		if (nullable != other.nullable)
			return false;
		if (primaryKey != other.primaryKey)
			return false;
		if (unique != other.unique)
			return false;
		return true;
	}

}
