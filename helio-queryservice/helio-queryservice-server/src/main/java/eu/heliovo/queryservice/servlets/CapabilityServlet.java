package eu.heliovo.queryservice.servlets;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CapabilityServlet
 */
public class CapabilityServlet extends VosiServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CapabilityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** 
     * Emits an XML document describing the capabilities. If {@code chosenCatalog}
     * is null, the capabilities cover all configured catalogues; otherwise they
     * cover only the named catalogue.
     */
    protected void output(Writer   writer) throws ServletException {
      System.out.println(" : URL string : "+getUrl());
      String tapUriHttp          = getUrl() + "HelioQueryService";
      String tapUriWeb          = getUrl() + "HelioService";
      //
      String tapLongUriHttp          = getUrl() + "HelioQueryService";
      String tapLongUriWeb          = getUrl() + "HelioLongQueryService";
      String capabilitiesUri = getUrl() + "VOSI/capabilities";
    
      String tablesURI       = getUrl() + "VOSI/tables";
      
      if(getQueryString() != null && getQueryString().equals("table=none")) {
    	  tablesURI = null;
      }
      if(getQueryString() != null && !getQueryString().equals("table=none")) {
    	  capabilitiesUri += "?" + getQueryString();
    	  tablesURI += "?" + getQueryString();
      }

      
      try {

        // Write the processing instruction for transforming this XML to HTML.
       // writer.write("<?xml-stylesheet type='text/xsl' href='capabilities.xsl'?>\n");

        // Output the capabilities header stuff
        writer.write(
        	"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"+
           	"<?xml-stylesheet type=\"text/xsl\" href=\""+ this.getUrl()+ "Style/vosi_capabilities.xsl\"?> \n"+
            "\n"+
            "<cap:capabilities\n" +
            "   xmlns:vr=\"http://www.ivoa.net/xml/VOResource/v1.0\"\n" +
            "   xmlns:vs=\"http://www.ivoa.net/xml/VODataService/v1.0\"\n" +
            "   xmlns:cs=\"http://www.ivoa.net/xml/ConeSearch/v1.0\"\n" +
            "   xmlns:cea=\"http://www.ivoa.net/xml/CEA/v1.0rc1\"\n" +
            "   xmlns:cap=\"urn:astrogrid:schema:Capabilities\"\n" +
            "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "   xsi:schemaLocation=\n" +
            "     \"http://www.ivoa.net/xml/VOResource/v1.0 http://software.astrogrid.org/schema/vo-resource-types/VOResource/v1.0/VOResource.xsd\n" +
            "      http://www.ivoa.net/xml/VODataService/v1.0 http://software.astrogrid.org/schema/vo-resource-types/VODataService/v1.0/VODataService.xsd\n" +
            "      http://www.ivoa.net/xml/ConeSearch/v1.0 http://software.astrogrid.org/schema/vo-resource-types/ConeSearch/v1.0/ConeSearch.xsd \n" +
            "      http://www.ivoa.net/xml/CEA/v1.0rc1 http://software.astrogrid.org/schema/vo-resource-types/CEAService/v1.0rc1/CEAService.xsd\n" +
            "      urn:astrogrid:schema:Capabilities Capabilities.xsd\">\n");


        // Output the TAP capability.
        if(tablesURI == null) {
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/v0.2'>\n");
	        writer.write("  <interface xsi:type='vs:ParamHTTP'>\n");
	        writer.write("    <accessURL use='full'>" + tapUriHttp + "</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("  <interface xsi:type='vr:WebService'>\n");
	        writer.write("    <accessURL use='full'>" + tapUriWeb + "</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        // Output the TAP capability.
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/WebService/v0.9'>\n");
	        writer.write("  <interface xsi:type='vr:WebService'>\n");
	        writer.write("    <accessURL use='full'>" + tapUriWeb + "</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	                
	        // Output the TAP capability.
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/Http/v0.9'>\n");
	        writer.write("  <interface xsi:type='vs:ParamHTTP'>\n");
	        writer.write("    <accessURL use='full'>" + tapLongUriHttp + "</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/LongRunningQuery/v0.9'>\n");
	        writer.write("  <interface xsi:type='vr:WebService'>\n");
	        writer.write("    <accessURL use='full'>" + tapLongUriWeb + "</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        /*
	         *       
	         *       
	  String tapUriHttp          = getUrl() + "HelioQueryService";
      String tapUriWeb          = getUrl() + "HelioService";
	         */
	        //New Helio HTTP capabalities
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/Soap/v1.0'>\n");
	        writer.write("  <interface xsi:type='vs:ParamHTTP'>\n");
	        writer.write("    <accessURL use='full'>" + getUrl()  + "HelioQueryService1_0</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/Soap/v1.0'>\n");
	        writer.write("  <interface xsi:type='vr:WebService'>\n");
	        writer.write("    <accessURL use='full'>" + getUrl() + "HelioService1_0</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/Soap/v1.0b'>\n");
	        writer.write("  <interface xsi:type='vs:ParamHTTP'>\n");
	        writer.write("    <accessURL use='full'>" + getUrl()  + "HelioQueryService1_0b</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/Soap/v1.0b'>\n");
	        writer.write("  <interface xsi:type='vr:WebService'>\n");
	        writer.write("    <accessURL use='full'>" + getUrl() + "HelioService1_0b</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/Soap/v1.1'>\n");
	        writer.write("  <interface xsi:type='vs:ParamHTTP'>\n");
	        writer.write("    <accessURL use='full'>" + getUrl()  + "HelioQueryService1_1</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/FullQuery/Soap/v1.1'>\n");
	        writer.write("  <interface xsi:type='vr:WebService'>\n");
	        writer.write("    <accessURL use='full'>" + getUrl() + "HelioService1_1</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        
	      //New Helio Long Running capabalities
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/LongFullQuery/Soap/v1.0'>\n");
	        writer.write("  <interface xsi:type='vs:ParamHTTP'>\n");
	        writer.write("    <accessURL use='full'>" + tapLongUriHttp + "</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
	        
	        writer.write("<capability standardID='ivo://helio-vo.eu/std/LongFullQuery/Soap/v1.0'>\n");
	        writer.write("  <interface xsi:type='vr:WebService'>\n");
	        writer.write("    <accessURL use='full'>" + tapLongUriWeb + "</accessURL>\n");
	        writer.write("  </interface>\n");
	        writer.write("</capability>\n");
        }//if
                
         
        writer.write(
            // Capability capability - IVOA name
            "<capability standardID=\"ivo://ivoa.net/std/VOSI#capabilities\">\n" +
            "  <interface xsi:type=\"vs:ParamHTTP\">\n" +
            "  <accessURL use=\"full\">" + capabilitiesUri + "</accessURL>\n" +
            "  <queryType>GET</queryType>\n" +
            "  <resultType>application/xml</resultType>\n" +
            "  </interface>\n" +
            "</capability>\n");
            
         
            // Tables capability - AstroGrid only so far as IVOA version not ready.
            if(tablesURI != null) {
            	writer.write(
	            "<capability standardID=\"ivo://org.astrogrid/std/VOSI/v0.3#tables\">\n" + 
	            "   <interface xsi:type=\"vs:ParamHTTP\">\n" + 
	            "   <accessURL use=\"full\">" + tablesURI + "</accessURL>\n" + 
	            "   <queryType>GET</queryType>\n" + 
	            "   <resultType>application/xml</resultType>\n" + 
	            "   </interface>\n" + 
	            "</capability>\n"
            	);
            }
           
        
        // End of capabilities
         writer.write("</cap:capabilities>\n");
       }
       catch (Exception ex) {
          throw new ServletException(ex.getMessage());
       }
    }

}
