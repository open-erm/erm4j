package com.erm4j.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines an object representation of a persistent model entity
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({"uid", "system_name", "name","description", "attributes", "table"})
public class Entity extends AccessibleElement{

	@JsonProperty("table")
	private String table = null;
	
	@JsonProperty("attributes")
	private List<EntityAttribute> attributes = new ArrayList<EntityAttribute>(); 
	
	/***
	 * Returns name of the table that is mapped to an entity
	 * @return
	 */
	@JsonProperty("table")
	public String getTable() {
		return table;
	}
	
	/***
	 * Sets name of the table that is mapped to an entity
	 * @param table
	 */
	@JsonProperty("table")
	public void setTable(String table) {
		this.table = table;
	}
	
	/***
	 * Returns list of {@link EntityAttribute}
	 * @return
	 */
	@JsonProperty("attributes")
	public List<EntityAttribute> getAttributes() {
		return attributes;
	}

	/***
	 * Sets a list of {@link EntityAttribute} for an entity 
	 * @param attributes
	 */
	@JsonProperty("attributes")
	public void setAttributes(List<EntityAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
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
		Entity other = (Entity) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}
	
	
	
}
