package eu.heliovo.clientapi.model.catalog;

import org.junit.Ignore;
import org.junit.Test;

import eu.heliovo.clientapi.model.field.DomainValueDescriptor;
import eu.heliovo.clientapi.model.field.HelioField;

public class HecStaticCatalogRegistryDemo {
  @Ignore
	@Test
	public void testStaticRegistry()
	{
	  CatalogRegistry reg=HecStaticCatalogRegistry.getInstance();
	  
	  for(DomainValueDescriptor<String> c:reg.getCatalogField().getValueDomain())
	  {
	    System.out.println(c.getValue());
	    for(HelioField<?> hf:reg.getFields(c.getValue()))
	      System.out.println("    "+hf.getId());
	  }
	}
}