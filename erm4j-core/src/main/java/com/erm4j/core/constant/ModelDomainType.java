package com.erm4j.core.constant;

import com.erm4j.core.annotations.ModelEntityAttribute;

/***
 * Domain types that can be assigned to a {@link ModelEntityAttribute}
 * 
 * @author skadnikov
 *
 */
public enum ModelDomainType {
	
	/***
	 * Undefined type assigned to {@link ModelEntityAttribute} by default
	 */
	UNDEFINED,
	/***
	 * Logical value storing true\false
	 */
	BOOLEAN,
	/***
	 * Single byte value
	 */
	BYTE,
	/***
	 * 32 bytes integer number 
	 */
	INTEGER,
	/***
	 * 64 bytes integer number  
	 */
	LONG,
	/***
	 * Floating point number
	 */
	FLOAT,
	/***
	 * Numeric value with fixed precision 
	 */
	NUMERIC,
	/***
	 * Numeric identifier
	 */
	ID,
	/***
	 * Money value stored as a number with fixed precision
	 */
	MONEY,
	/***
	 * String unique identifier of an entity instance
	 */
	GUID,
	/***
	 * Short string with a system name or code used to identify some
	 * reference record
	 */
	CODE,
	/***
	 * Brief name of an entity instance
	 */
	BRIEF,
	/***
	 * Full name of an entity instance
	 */
	NAME,
	/***
	 * Common string value of undefined length
	 */
	STRING,
	/***
	 * Email address string
	 */
	EMAIL,
	/***
	 * URL string
	 */
	URL,
	/***
	 * Phone number string
	 */
	PHONE,
	/***
	 * String type for storing descriptions
	 */
	DESCRIPTION,
	/***
	 * Text value that can store large amounts of data
	 */
	TEXT,
	/***
	 * JSON text value that can store large amounts of data
	 */
	JSON,
	/***
	 * Short date value 
	 */
	DATE,
	/***
	 * Date and time value
	 */
	DATE_TIME,
	/***
	 * Timestamp for storing time with maximum precision 
	 */
	TIMESTAMP,
	/***
	 * Enumerated type value
	 */
	ENUM,
	/***
	 * Link that uniquely identifies other referenced model entity 
	 */
	ENTITY;
	
	
}
