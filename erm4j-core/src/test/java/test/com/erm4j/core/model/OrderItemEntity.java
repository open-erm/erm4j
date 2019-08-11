package test.com.erm4j.core.model;

import java.math.BigDecimal;

import com.erm4j.core.annotations.ModelEntity;
import com.erm4j.core.annotations.ModelEntityAttribute;
import com.erm4j.core.annotations.ModelEntityReference;
import com.erm4j.core.constant.ModelDomainType;

@ModelEntity(systemName = "OrderItem", name = "Order item")
public class OrderItemEntity {
	
	@ModelEntityAttribute(domainType = ModelDomainType.ID, primaryKey = true)
	private long id;
	
	@ModelEntityAttribute(domainType = ModelDomainType.GUID, unique = true)
	private String guid;

	@ModelEntityAttribute(domainType = ModelDomainType.NAME)
	private String name;

	@ModelEntityAttribute(domainType = ModelDomainType.MONEY)
	private BigDecimal amount;

	@ModelEntityAttribute(domainType = ModelDomainType.ENTITY, systemName = "Order", name = "Order")
	@ModelEntityReference(target = OrderEntity.class)
	private long orderId;
}
