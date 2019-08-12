package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines accessible (human readable) element reference
 * @author root
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AccessibleElementReference {

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


}
