package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import com.erm4j.core.constant.ModelDomainType;

/***
 * Attribute of {@link EnumerationItem}
 * @author skadnikov
 *
 */
public class EnumerationItemAttribute {
	
	@JsonProperty("name")
	private String key = null;
	
	@JsonProperty("type")
	private ModelDomainType domainType = ModelDomainType.UNDEFINED;
	
	@JsonProperty("key_attribute")
	private boolean keyAttribute = false;

	/***
	 * Returns internal name of the attribute that serves as a key
	 * in attribute collection
	 * @return
	 */
	@JsonProperty("name")
	public String getName() {
		return key;
	}
	
	/***
	 * Sets internal name of the attribute that serves as a key
	 * in attribute collection
	 * 
	 * @param name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.key = name;
	}

	/***
	 * Returns {@link ModelDomainType} of attribute value
	 * @return
	 */
	@JsonProperty("type")
	public ModelDomainType getDomainType() {
		return domainType;
	}

	/***
	 * Sets {@link ModelDomainType} of attribute value
	 * 
	 * @param domainType
	 */
	@JsonProperty("type")
	public void setDomainType(ModelDomainType domainType) {
		this.domainType = domainType;
	}

	/***
	 * Defines if attribute is a key, which value uniquely identifies 
	 * enumeration item and is stored in DB table as a enumeration type key
	 * @return
	 */
	@JsonProperty("key_attribute")
	public boolean isKeyAttribute() {
		return keyAttribute;
	}

	/***
	 * Sets if attribute is a key, which value uniquely identifies 
	 * enumeration item and is stored in DB table as a enumeration type key
	 * 
	 * @param keyAttribute
	 */
	@JsonProperty("key_attribute")
	public void setKeyAttribute(boolean keyAttribute) {
		this.keyAttribute = keyAttribute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domainType == null) ? 0 : domainType.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + (keyAttribute ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnumerationItemAttribute other = (EnumerationItemAttribute) obj;
		if (domainType != other.domainType)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (keyAttribute != other.keyAttribute)
			return false;
		return true;
	}
	
}
