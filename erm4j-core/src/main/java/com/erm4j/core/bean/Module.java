package com.erm4j.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/***
 * Defines a top level container for {@link ModelElement} objects
 * 
 * @author skadnikov
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({"uid", "system_name", "name","description","enumerations","entities"})
public class Module extends AccessibleElement{

	@JsonProperty("entity_refs")
	private List<ModelElement> entityReferences = new ArrayList<>();
	
	@JsonProperty("enum_refs")
	private List<ModelElement> enumReferences = new ArrayList<>();

	/***
	 * Returns list of references to {@link Entity} contained in module
	 * @return
	 */
	@JsonProperty("entity_refs")
	public List<ModelElement> getEntityReferences() {
		return new ArrayList<>(entityReferences);
	}
	
	/***
	 * Sets list of references to {@link Entity} contained in module
	 * @param entityReferences
	 */
	@JsonProperty("entity_refs")
	public void setEntityReferences(List<ModelElement> entityReferences) {
		this.entityReferences = entityReferences;
	}
	
	/***
	 * Adds {@link Entity} to {@link Module} and sets a container module link
	 * to added object
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		entity.setModule(this);
		this.entityReferences.add(entity.createReference());
	}

	/***
	 * Returns list of references to {@link Enumeration} contained in module
	 * @return
	 */
	@JsonProperty("enum_refs")
	public List<ModelElement> getEnumReferences() {
		return new ArrayList<>(enumReferences);
	}

	/***
	 * Sets list of references to {@link Enumeration} contained in module
	 * @param enumReferences
	 */
	@JsonProperty("enum_refs")
	public void setEnumReferences(List<ModelElement> enumReferences) {
		this.enumReferences = enumReferences;
	}

	/***
	 * Adds {@link Enumeration} to {@link Module} and sets a container module link
	 * to added object
	 * @param enumeration
	 */
	public void addEnumeration(Enumeration enumeration) {
		enumeration.setModule(this);
		this.enumReferences.add(enumeration.createReference());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((entityReferences == null) ? 0 : entityReferences.hashCode());
		result = prime * result + ((enumReferences == null) ? 0 : enumReferences.hashCode());
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
		Module other = (Module) obj;
		if (entityReferences == null) {
			if (other.entityReferences != null)
				return false;
		} else if (!entityReferences.equals(other.entityReferences))
			return false;
		if (enumReferences == null) {
			if (other.enumReferences != null)
				return false;
		} else if (!enumReferences.equals(other.enumReferences))
			return false;
		return true;
	}
	
}
