package org.egso.provider.datamanagement.connector;


import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.egso.common.votable.EGSOVOTable;
import org.egso.common.votable.VOTableFactory;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class SECWebServiceParser extends DefaultHandler implements ProviderResultsParser {


//	private String spaces = "";
	private boolean getTheResult = false;
	private StringBuffer finalString = null;
	private EGSOVOTable votable = null;
	private Vector finalResults = null;


	public SECWebServiceParser() {
		System.out.println("New instance of 'SECWebServiceParser' class.");
//		spaces = "";
		finalString = new StringBuffer();
		finalResults = new Vector();
	}

	
	public void setSelectedFields(Vector selected) {
	}

	public Vector getResults() {
		return (finalResults);
	}

	public void characters(char[] ch, int start, int length)
		throws SAXException {
		String str = new String(ch, start, length);
//		System.out.println(spaces + str);
		if (getTheResult) {
			finalString.append(str);
		}
	}


	public void endDocument()
		throws SAXException {
//		System.out.println("...End of the document");
//		System.out.println("Reception of results finished. Formatting results...");
		String tmp = finalString.toString();
		// Remove the <?xml?> and <!DOCTYPE> parts.
		tmp = tmp.substring(tmp.indexOf("<VOTABLE"));
		try {
			VOTableFactory vtf = VOTableFactory.newInstance();
			votable = vtf.createVOTable(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("-- VOTABLE CREATED --");
//		System.out.println(votable.toString());
//		System.out.println("---------------------");
		List l = null;
		Vector tempo = null;
		int nb = votable.getFieldNames().size();
		for (Iterator it = votable.getAllRows().iterator() ; it.hasNext() ; ) {
			tempo = new Vector();
			tempo.add("SEC");
			for (Iterator it2 = ((List) it.next()).iterator() ; it2.hasNext() ; ) {
				tempo.add((String) it2.next());
			}
//			tempo.add("No link for the SEC");
			finalResults.add(tempo);
		}
		System.out.println("[=================================]");
		for (Iterator it = finalResults.iterator() ; it.hasNext() ; ) {
			tempo = (Vector) it.next();
			System.out.print("[" + tempo.get(0));
			for (int i = 1 ; i < tempo.size() ; i++) {
				System.out.print(", " + tempo.get(i));
			}
			System.out.println("]");
		}
		System.out.println("[=================================]");
	}

	public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
//		spaces = spaces.substring(2);
//		System.out.println(spaces + "</" + localName + ">");
		if (qName.equals("soapVal")) {
			getTheResult = false;
		}
	}
	
	public void endPrefixMapping(String prefix)
		throws SAXException {
//		System.out.println("endPrefixMapping: " + prefix);
	}
	
	public void ignorableWhitespace(char[] ch, int start, int length)
		throws SAXException {
//		System.out.println("ignorableWhitespace: " + new String(ch, start, length));
	}
	
	public void processingInstruction(String target, String data)
		throws SAXException {
//		System.out.println("processingInstruction: " + target + " | " + data);
	}
	
	public void setDocumentLocator(Locator locator) {
//		System.out.println("setDocumentLocator");
	}
	
	public void skippedEntity(String name)
		throws SAXException {
//		System.out.println("skippedEntity: " + name);
	}
	
	public void startDocument()
		throws SAXException {
		finalResults = new Vector();
//		System.out.println("Start of the Document...");
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
		throws SAXException {
//		System.out.println(spaces + "<" + localName + ">");
//		spaces += "  ";
		if (qName.equals("soapVal")) {
			getTheResult = true;
		}
	}
	
	public void startPrefixMapping(String prefix, String uri)
		throws SAXException {
//		System.out.println("startPrefixMapping: " + prefix + " | " + uri);
	}
}

