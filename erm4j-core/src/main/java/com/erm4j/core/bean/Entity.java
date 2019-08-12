package com.erm4j.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines an object representation of a persistent model entity
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
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
	
}
