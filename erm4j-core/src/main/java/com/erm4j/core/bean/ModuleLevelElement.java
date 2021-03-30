package com.erm4j.core.bean;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines an object that is directly contained in {@link Module}
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ModuleLevelElement extends AccessibleElement{

	@JsonProperty("container_module")
	private ModelElement module = null;
	
	/***
	 * Returns a {@link Module} reference that contains an element
	 * @return
	 */
	@JsonProperty("container_module")
	public ModelElement getModule() {
		return module;
	}
	
	/***
	 * Sets a {@link Module} reference that contains an element
	 * @param target
	 */
	@JsonProperty("container_module")
	public void setModule(ModelElement target) {
		this.module = target.createReference();
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((module == null) ? 0 : module.hashCode());
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
		ModuleLevelElement other = (ModuleLevelElement) obj;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		return true;
	}

}
