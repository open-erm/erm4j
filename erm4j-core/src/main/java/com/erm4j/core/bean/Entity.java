package com.erm4j.core.bean;

import java.util.ArrayList;
import java.util.List;

/***
 * Defines an object representation of a persistent model entity
 * 
 * @author skadnikov
 *
 */
public class Entity extends AccessibleElement{

	private String table = null;
	
	private List<EntityAttribute> attributes = new ArrayList<EntityAttribute>(); 
	
	/***
	 * Returns name of the table that is mapped to an entity
	 * @return
	 */
	public String getTable() {
		return table;
	}
	
	/***
	 * Sets name of the table that is mapped to an entity
	 * @param table
	 */
	public void setTable(String table) {
		this.table = table;
	}
	
	/***
	 * Returns list of {@link EntityAttribute}
	 * @return
	 */
	public List<EntityAttribute> getAttributes() {
		return attributes;
	}

	/***
	 * Sets a list of {@link EntityAttribute} for an entity 
	 * @param attributes
	 */
	public void setAttributes(List<EntityAttribute> attributes) {
		this.attributes = attributes;
	}
	
}
