package test.com.erm4j.core.model;

/***
 * Some class that must not be included into entity model
 * @author root
 *
 */
public class NotAnnotatedEntity {
	
	private long id = -1;
	private String name = "";
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
