package com.erm4j.core.bean;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines an element in enumeration
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class EnumerationItem extends AccessibleElement {

	@JsonProperty("attributes")
	private Map<String,Object> attributeValues = new HashMap<>();


	/***
	 * Returns {@link EnumerationItemAttribute} value
	 * 
	 * @param attributeName {@link EnumerationItemAttribute#getName()} 
	 * @return
	 */
	public Object getAttributeValue(String attributeName) {
		return attributeValues.get(attributeName);
	}

	/***
	 * Sets {@link EnumerationItemAttribute} value
	 * @param attributeName {@link EnumerationItemAttribute#getName()}
	 * @param value Value of the attribute of primitive type
	 */
	public void setAttributeValue(String attributeName, Object value) {
		attributeValues.put(attributeName, value);
	}
	
	/***
	 * Returns map of {@link EnumerationItemAttribute} values where 
	 * {@link EnumerationItemAttribute#getName()} is a key
	 * @return
	 */
	@JsonProperty("attributes")
	public Map<String, Object> getAttributeValues() {
		return attributeValues;
	}

	/***
	 * Sets map of {@link EnumerationItemAttribute} values where 
	 * {@link EnumerationItemAttribute#getName()} is a key
	 * @return
	 */
	@JsonProperty("attributes")
	public void setAttributeValues(Map<String, Object> attributeValues) {
		this.attributeValues = attributeValues;
	}
	
}
