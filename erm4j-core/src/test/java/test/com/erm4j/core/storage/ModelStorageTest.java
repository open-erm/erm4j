package test.com.erm4j.core.storage;


import static org.testng.AssertJUnit.assertTrue;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.erm4j.core.bean.Entity;
import com.erm4j.core.bean.EntityAttribute;
import com.erm4j.core.bean.EntityEnumAttribute;
import com.erm4j.core.bean.EntityReferenceAttribute;
import com.erm4j.core.bean.Enumeration;
import com.erm4j.core.bean.EnumerationItem;
import com.erm4j.core.bean.Module;
import com.erm4j.core.constant.ModelDomainType;

public class ModelStorageTest {
	
	private ObjectMapper jsonMapper;
	private Module module;

	@BeforeClass
	public void setUp() {
		module = new Module();
		module.setUid("som-module-1");
		module.setSystemName("Module1");
		module.setName("Module 1");
		jsonMapper = new ObjectMapper();
	}
	
	@Test
	public void testEnumStorage() throws JsonGenerationException, JsonMappingException, IOException {
		Enumeration taxTypeEnum = createEnumType();
		module.addEnumeration(taxTypeEnum);
		
		String taxTypeEnumJSON = jsonMapper.writeValueAsString(taxTypeEnum);
		assertTrue("Tax type enumeration serialized correctly", StringUtils.isNotBlank(taxTypeEnumJSON));

		Enumeration restoredTaxTypeEnum = jsonMapper.readValue(taxTypeEnumJSON, Enumeration.class);
		assertTrue("Enumeration deserialized correctly", taxTypeEnum.equals(restoredTaxTypeEnum));
	}

	@Test
	public void testEntityStorage() throws JsonGenerationException, JsonMappingException, IOException {
		
		Entity orderEntity = createEntity("uid-order","Order","Order","TBL_ORDER");
		module.addEntity(orderEntity);

		EntityAttribute orderID = createEntityAttribute(
									"uid-order-1","ID","ID","ID", ModelDomainType.ID);
		orderID.setPrimaryKey(true);
		orderEntity.getAttributes().add(orderID);
		orderEntity.getAttributes()
			.add(createEntityAttribute(
					"uid-order-2","Number","Number","NUMBER", ModelDomainType.CODE)
				);
		orderEntity.getAttributes()
			.add(createEntityAttribute(
					"uid-order-3","OrderDate","Order date","ORDER_DATE", ModelDomainType.DATE_TIME)
				);

		
		Entity orderItemEntity = createEntity("uid-orderItem","OrderItem","Order item","TBL_ORDER_ITEM");
		module.addEntity(orderItemEntity);

		EntityAttribute orderItemID = createEntityAttribute(
				"uid-order-item-1","ID","ID","ID", ModelDomainType.ID);
		orderItemID.setPrimaryKey(true);
		orderItemEntity.getAttributes().add(orderItemID);
		
		orderItemEntity.getAttributes()
			.add(createEntityAttribute(
				"uid-order-item-2","Price","Price","PRICE", ModelDomainType.MONEY)
			);

		orderItemEntity.getAttributes()
			.add(createEntityReferenceAttribute(
				"uid-order-item-3","Order","Order","ORDER_ID", orderEntity)
			);

		orderItemEntity.getAttributes()
			.add(createEntityEnumAttribute(
				"uid-order-item-4","TaxType","Tax type","TAX_TYPE", createEnumType())
			);

		String orderEntityJSON = jsonMapper.writeValueAsString(orderEntity);
		assertTrue("Order entity serialized correctly", StringUtils.isNotBlank(orderEntityJSON));
		
		String orderItemEntityJSON = jsonMapper.writeValueAsString(orderItemEntity);
		assertTrue("Order item entity serialized correctly", StringUtils.isNotBlank(orderItemEntityJSON));
		
		
		Entity restoredOrderEntity = jsonMapper.readValue(orderEntityJSON, Entity.class);
		assertTrue("Order entity deserialized correctly", orderEntity.equals(restoredOrderEntity));

		Entity restoredOrderItemEntity = jsonMapper.readValue(orderItemEntityJSON, Entity.class);
		assertTrue("Order item entity deserialized correctly", orderItemEntity.equals(restoredOrderItemEntity));
	}
	
	@Test(dependsOnMethods = {"testEntityStorage","testEnumStorage"})
	public void testModuleSerialization() throws JsonGenerationException, JsonMappingException, IOException {
		String moduleJSON = jsonMapper.writeValueAsString(module);
		assertTrue("Order entity serialized correctly", StringUtils.isNotBlank(moduleJSON));

		Module restoredModule = jsonMapper.readValue(moduleJSON, Module.class);
		assertTrue("Module deserialized correctly", module.equals(restoredModule));

	}

	private Enumeration createEnumType() {
		Enumeration taxType = new Enumeration();
		taxType.setUid("tax-type-uid");
		taxType.setSystemName("TaxType");
		taxType.setName("Tax type");
		
		EnumerationItem noTax = new EnumerationItem();
		noTax.setUid("tax-type-1-uid");
		noTax.setSystemName("NoTax");
		noTax.setName("No tax");
		taxType.getItems().add(noTax);
		
		EnumerationItem tax20 = new EnumerationItem();
		tax20.setUid("tax-type-2-uid");
		tax20.setSystemName("Tax20");
		tax20.setName("Tax 20%");
		taxType.getItems().add(tax20);
		
		return taxType;
	}

	private EntityAttribute createEntityAttribute(
								String uid, String systemName,
								String name, String columnName,
								ModelDomainType type) {
		EntityAttribute orderEntity = new EntityAttribute();
		orderEntity.setUid(uid);
		orderEntity.setSystemName(systemName);
		orderEntity.setName(name);
		orderEntity.setColumn(columnName);
		orderEntity.setDomainType(type);
		return orderEntity;
	}

	private EntityAttribute createEntityReferenceAttribute(
								String uid, String systemName,
								String name, String columnName,
								Entity target) {
		EntityReferenceAttribute orderEntity = new EntityReferenceAttribute();
		orderEntity.setUid(uid);
		orderEntity.setSystemName(systemName);
		orderEntity.setName(name);
		orderEntity.setColumn(columnName);
		orderEntity.setTarget(target);
		return orderEntity;
	}

	private EntityAttribute createEntityEnumAttribute(
			String uid, String systemName,
			String name, String columnName,
			Enumeration enumType) {
		EntityEnumAttribute orderEntity = new EntityEnumAttribute();
		orderEntity.setUid(uid);
		orderEntity.setSystemName(systemName);
		orderEntity.setName(name);
		orderEntity.setColumn(columnName);
		orderEntity.setTarget(enumType);
		return orderEntity;
	}

	private Entity createEntity(String uid, String systemName, String name, String tableName) {
		Entity orderEntity = new Entity();
		orderEntity.setUid(uid);
		orderEntity.setSystemName(systemName);
		orderEntity.setName(name);
		orderEntity.setTable(tableName);
		return orderEntity;
	}
	
}