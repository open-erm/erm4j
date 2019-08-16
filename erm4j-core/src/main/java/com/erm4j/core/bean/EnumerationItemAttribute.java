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
	
	
}
