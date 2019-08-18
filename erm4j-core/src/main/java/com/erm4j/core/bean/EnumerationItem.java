package com.erm4j.core.bean;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines an element in enumeration
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({"uid", "system_name", "name","description", "attributes"})
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributeValues == null) ? 0 : attributeValues.hashCode());
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
		EnumerationItem other = (EnumerationItem) obj;
		if (attributeValues == null) {
			if (other.attributeValues != null)
				return false;
		} else if (!attributeValues.equals(other.attributeValues))
			return false;
		return true;
	}
	
}
