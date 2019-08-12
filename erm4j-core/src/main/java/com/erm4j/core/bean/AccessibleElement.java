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
public class AccessibleElement {
	
	@JsonProperty("uid")
	private String uid;
	
	@JsonProperty("system_name")
	private String systemName;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	
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
