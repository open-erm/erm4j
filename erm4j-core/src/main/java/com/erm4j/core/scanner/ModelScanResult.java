package com.erm4j.core.scanner;

import java.util.ArrayList;
import java.util.List;

import com.erm4j.core.bean.Entity;
import com.erm4j.core.bean.Enumeration;
import com.erm4j.core.bean.ModelElement;

/***
 * Result of performing scan with the help of {@link ERMScanner}
 * that contains {@link ModelElement} objects that were found
 * during scanning classes 
 * @author root
 *
 */
public class ModelScanResult {
	
	private List<Entity> entities = new ArrayList<>();
	
	private List<Enumeration> enumerations = new ArrayList<>();

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Enumeration> getEnumerations() {
		return enumerations;
	}
	
	
}
