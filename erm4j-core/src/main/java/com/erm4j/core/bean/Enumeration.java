package com.erm4j.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Enumeration model. Defines limited collection of items
 * that is static or pseudo-static (changing very rare).
 * Enumerations are used as a one of data types in {@link EntityAttribute} 
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({"uid", "system_name", "name","description", "attributes", "items"})
public class Enumeration extends AccessibleElement {
	
	@JsonProperty("attributes")
	private List<EnumerationItemAttribute> attributes = new ArrayList<>(); 
	
	@JsonProperty("items")
	private List<EnumerationItem> items = new ArrayList<EnumerationItem>();

	/***
	 * Returns items in the enumeration
	 * @return
	 */
	@JsonProperty("items")
	public List<EnumerationItem> getItems() {
		return items;
	}
	
	/***
	 * Sets items in the enumeration
	 * @param items
	 */
	@JsonProperty("items")
	public void setItems(List<EnumerationItem> items) {
		this.items = items;
	}

	/***
	 * Returns collection of attributes describing each 
	 * {@link EnumerationItem}
	 * @return
	 */
	@JsonProperty("attributes")
	public List<EnumerationItemAttribute> getAttributes() {
		return attributes;
	}

	/***
	 * Sets collection of attributes describing each 
	 * {@link EnumerationItem}
	 * 
	 * @param attributes
	 */
	@JsonProperty("attributes")
	public void setAttributes(List<EnumerationItemAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		Enumeration other = (Enumeration) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}	
	
}