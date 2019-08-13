package test.com.erm4j.core.model;

import java.math.BigDecimal;

import com.erm4j.core.annotations.ModelEntity;
import com.erm4j.core.annotations.ModelEntityAttribute;
import com.erm4j.core.constant.ModelDomainType;

@ModelEntity(systemName = "Order", name = "Order")
public class OrderEntity {

	@ModelEntityAttribute(domainType = ModelDomainType.ID, primaryKey = true)
	private long id;
	
	@ModelEntityAttribute(domainType = ModelDomainType.GUID, unique = true)
	private String guid;

	@ModelEntityAttribute(domainType = ModelDomainType.NAME)
	private String name;

	/***
	 * Attribute that must not be included into entity model
	 */
	private BigDecimal calculatedTotalAmount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCalculatedTotalAmount() {
		return calculatedTotalAmount;
	}

	public void setCalculatedTotalAmount(BigDecimal calculatedTotalAmount) {
		this.calculatedTotalAmount = calculatedTotalAmount;
	}

	
}
