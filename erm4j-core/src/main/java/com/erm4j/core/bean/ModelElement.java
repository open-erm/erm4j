package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines accessible (human readable) model element
 * @author root
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({ "uid", "system_name", "name" })
public class ModelElement {

	@JsonProperty("uid")
	private String uid;
	
	@JsonProperty("system_name")
	private String systemName;

	@JsonProperty("name")
	private String name;

	/***
	 * Returns machine readable unique identifier of element 
	 * @return
	 */
	@JsonProperty("uid")
	public String getUid() {
		return uid;
	}
	
	/***
	 * Sets machine readable unique identifier of element
	 * @param uid
	 */
	@JsonProperty("uid")
	public void setUid(String uid) {
		this.uid = uid;
	}

	/***
	 * Returns system name of an element used for 
	 * runtime routines and code generation 
	 * @return
	 */
	@JsonProperty("system_name")
	public String getSystemName() {
		return systemName;
	}

	/***
	 * Sets system name of an element used for 
	 * runtime routines and code generation 
	 * 
	 * @param systemName
	 */
	@JsonProperty("system_name")
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/***
	 * Returns logical name of an element used for information modeling 
	 * @return
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
	/***
	 * Sets logical name of an element used for information modeling
	 * 
	 * @param name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	
	/***
	 * Creates new instance of {@link ModelElement}
	 * that can be used as a reference
	 * @return
	 */
	public ModelElement createReference() {
		ModelElement ref = new ModelElement();
		ref.setUid(getUid());
		ref.setName(getName());
		ref.setSystemName(getSystemName());
		return ref;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((systemName == null) ? 0 : systemName.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		ModelElement other = (ModelElement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (systemName == null) {
			if (other.systemName != null)
				return false;
		} else if (!systemName.equals(other.systemName))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

}
