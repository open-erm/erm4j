package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines an accessible human-readable element of model 
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({"description"})
public class AccessibleElement extends ModelElement {
	

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		AccessibleElement other = (AccessibleElement) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	
}
