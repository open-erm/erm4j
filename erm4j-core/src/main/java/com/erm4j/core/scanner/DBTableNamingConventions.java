package com.erm4j.core.scanner;

/***
 * Conventions for building table names from system name of entities
 * @author skadnikov
 *
 */
public class DBTableNamingConventions {
	
	private String tableNamePrefix = "";
	
	private boolean useSnakeCaseNaming = true;
	
	
	/***
	 * Indicates if snake case naming must be used.
	 * If true that 'ClientOrderItem' entity name would be converted to 'CLIENT_ORDER_ITEM'
	 * table name. Default value is true
	 * @return
	 */
	public boolean isUseSnakeCaseNaming() {
		return useSnakeCaseNaming;
	}
	
	/***
	 * Sets if snake case naming must be used.
	 * If true that 'ClientOrderItem' entity name would be converted to 'CLIENT_ORDER_ITEM'
	 * table name. Default value is true
	 * 
	 * @param useSnakeCaseNaming
	 */
	public void setUseSnakeCaseNaming(boolean useSnakeCaseNaming) {
		this.useSnakeCaseNaming = useSnakeCaseNaming;
	}

	/***
	 * Returns prefix that would prepend generated table name
	 * i.e. if prefix is 'SBT_' and generated table name is 'order'
	 * that table name will be 'SBT_Order'
	 * @return
	 */
	public String getTableNamePrefix() {
		return tableNamePrefix;
	}
	
	/***
	 * Sets prefix that would prepend generated table name
	 * i.e. if prefix is 'SBT_' and generated table name is 'order'
	 * that table name will be 'SBT_Order'
	 * 
	 * @param tableNamePrefix
	 */
	public void setTableNamePrefix(String tableNamePrefix) {
		this.tableNamePrefix = tableNamePrefix;
	}
	
}
