package com.erm4j.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
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
}