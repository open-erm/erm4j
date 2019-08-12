package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines an accessible human-readable element of model 
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AccessibleElement extends AccessibleElementReference {
	

	@JsonProperty("description")
	private String description;

	
	/***
	 * Returns complete description of an element in an information model 
	 * @return
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	
	/***
	 * Sets complete description of an element in an information model
	 * @param description
	 */
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	
}
