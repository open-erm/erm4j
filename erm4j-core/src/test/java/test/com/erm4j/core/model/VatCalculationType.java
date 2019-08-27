package test.com.erm4j.core.model;

import com.erm4j.core.annotations.ModelEnumeration;
import com.erm4j.core.annotations.ModelEnumerationItem;

@ModelEnumeration(uid = "vat-calc-type-uid")
public enum VatCalculationType {
	
	@ModelEnumerationItem(uid = "novat-guid", systemName = "NoVat", name = "No VAT")
	NO_VAT,
	
	@ModelEnumerationItem(uid = "20vat-guid", systemName = "Vat20", name = "20 percent VAT")
	VAT20
	
	
}
