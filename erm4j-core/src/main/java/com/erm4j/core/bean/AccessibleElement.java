package com.erm4j.core.bean;

/***
 * Defines an accessible human-readable element of model 
 * 
 * @author skadnikov
 *
 */
public class AccessibleElement {
	
	private String uid;
	
	private String systemName;

	private String name;

	private String description;

	
	/***
	 * Returns machine readable unique identifier of element 
	 * @return
	 */
	public String getUid() {
		return uid;
	}
	
	/***
	 * Sets machine readable unique identifier of element
	 * @param uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/***
	 * Returns system name of an element used for 
	 * runtime routines and code generation 
	 * @return
	 */
	public String getSystemName() {
		return systemName;
	}

	/***
	 * Sets system name of an element used for 
	 * runtime routines and code generation 
	 * 
	 * @param systemName
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/***
	 * Returns logical name of an element used for information modeling 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/***
	 * Sets logical name of an element used for information modeling
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/***
	 * Returns complete description of an element in an information model 
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/***
	 * Sets complete description of an element in an information model
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
}
