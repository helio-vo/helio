package org.egso.provider.datamanagement.mapper;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import org.egso.provider.admin.ProviderMonitor;
import org.egso.provider.utils.Conversion;
import org.egso.provider.utils.ProviderUtils;
import org.egso.provider.utils.XMLTools;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author  Romain LINSOLAS
 * @version 1.1.1 26/10/2004
 **/
/*
1.1.1 - 26/10/2004:
	Adaptation to the SAXParser (instead of Xerces SAX Parser).
1.1 - 13/04/2004:
	Bug correction. Too much calls were created.
*/
public class WebServiceParser extends DefaultHandler {

	private Stack paramName = null;
	private Stack stack = null;
	private final int DATA = 0;
	private final int PARAM = 1;
	private final int VALUE = 2;
	private final int INTERVAL = 3;
	private final int START = 4;
	private final int END = 5;
	private final String AND = "AND";
	private final String OR = "OR";
	private final String[] tagNames = {"data", "param", "value", "interval", "start", "end"};
	private final String[][] acceptedList = {{"param"}, {"param", "value", "interval"}, {}, {"start", "end"}, {}, {}};
	private String[] acceptedNodes = null;
	private boolean textExpected = false;
	private boolean gotTheValue = false;
	private int intervalStartEnd = 0;
	private String currentNode = null;
	private Hashtable allValues = null;
	private StringBuffer[] dateInterval = null;
	private Vector dates = null;
	private Node mappingNode = null;
	private Node structureNode = null;
	private Vector calls = null;
	private Vector exceptions = null;
	private XMLTools xmlTools = null;
	private StringBuffer temporateValue = null;


	public WebServiceParser(Node map, Node struct) {
		paramName = new Stack();
		allValues = new Hashtable();
		mappingNode = map;
		structureNode = struct;
		stack = new Stack();
		dates = new Vector();
		exceptions = new Vector();
		xmlTools = XMLTools.getInstance();
	}


	public Vector getAllCalls() {
		return (calls);
	}
	
	public Vector getExceptions() {
		return (exceptions);
	}


	/**
	 * This method is called at the end of the document parsing. It formates the
	 * temporary results into the web-service calls (for the moment, it is the
	 * content of the SOAP message that will be send to the remote server).
	 * The hashtable (<i>allValues</i>) that contains temporary results is an
	 * Hashtable of Hashtable of Hashtable of Vector :)<br>
	 * <u>allValues:</u> Hashtable of (key, Hastable), where key=name of the
	 * parameter in the query, and Hashtable=<i>values</i>.<br>
	 * <u>values:</u> Hashtable of (key, Hashtable), where key=EGSO value of the
	 * previous parameter, and Hashtable=<i>hash</i>.<br>
	 * <u>hash:</u> Hashtable of (key, Vector), where key=name of the parameter
	 * in the Web-Service query, and Vector=a vector that contains all values
	 * for this parameter.<br>
	 * At the end of the execution of this method, the Vector <i>calls</i>
	 * contains the formatted SOAP messages (one per WS call) for the query.
	 **/
	private void finish() {
//		System.out.println("FINISH WEB SERVICES MAPPING...");
/*
// --- DEBUG INFO ---
		String key = null;
		Vector v = null;
		Hashtable values = null;
		Hashtable hash = null;
		for (Enumeration e = allValues.keys() ; e.hasMoreElements() ; ) {
			key = (String) e.nextElement();
			values = (Hashtable) allValues.get(key);
			System.out.println(key + ":");
			for (Enumeration e2 = values.keys() ; e2.hasMoreElements() ; ) {
				key = (String) e2.nextElement();
				System.out.println("\t" + key + ":");
				hash = (Hashtable) values.get(key);
				for (Enumeration e3 = hash.keys() ; e3.hasMoreElements() ; ) {
					key = (String) e3.nextElement();
					System.out.println("\t\t" + key + ":");
					v = (Vector) hash.get(key);
					for (Iterator it = v.iterator() ; it.hasNext() ; ) {
						System.out.println("\t\t\t" + (String) it.next());
					}
				}
			}
		}
		System.out.println("DATES:");
		String[] tmp = null;
		for (Iterator it = dates.iterator() ; it.hasNext() ; ) {
			tmp = (String[]) it.next();
			System.out.println("\t" + tmp[0] + " <-> " + tmp[1]);
		}
		System.out.println("-----");
*/
		// Formatting dates.
		formatDates();
/*
		System.out.println("DATES:");
		for (Iterator it = dates.iterator() ; it.hasNext() ; ) {
			System.out.println((String) it.next());
		}
		System.out.println("=====");
*/
		String[] tmp = null;
		// Format the content of allValues and put the output in calls.
		try {
			int nb = Integer.parseInt(xmlTools.selectSingleNode(structureNode, "//structure/method").getAttributes().getNamedItem("parameter-number").getNodeValue());
			calls = new Vector();
			int index = -1;
			Vector callsTmp = null;
			Vector callsTempo = null;
			Vector vals = null;
			Hashtable values = null;
			Hashtable hash = null;
			String key = null;
			// Process all allValues elements...
			for (Enumeration e = allValues.keys() ; e.hasMoreElements() ; ) {
				key = (String) e.nextElement();
				values = (Hashtable) allValues.get(key);
				for (Enumeration e2 = values.keys() ; e2.hasMoreElements() ; ) {
					hash = (Hashtable) values.get((String) e2.nextElement());
					callsTmp = new Vector();
					callsTmp.add(new String[nb]);
					for (Enumeration e3 = hash.keys() ; e3.hasMoreElements() ; ) {
						key = (String) e3.nextElement();
						index = Integer.parseInt(xmlTools.selectSingleNode(structureNode, "//structure/method/param[@name='" + key + "']").getAttributes().getNamedItem("index").getNodeValue());
						vals = (Vector) hash.get(key);
						callsTempo = new Vector();
						for (Iterator it = callsTmp.iterator() ; it.hasNext() ; ) {
							tmp = (String[]) it.next();
							for (Iterator it2 = vals.iterator() ; it2.hasNext() ; ) {
								tmp[index] = (String) it2.next();
								callsTempo.add(tmp.clone());
							}
						}
						callsTmp = callsTempo;
					}
					for (Iterator it = callsTmp.iterator() ; it.hasNext() ; ) {
						calls.add((String[]) it.next());
					}
				}
			}
			// Add dates in the calls elements.
			//int indexDateBegin = Integer.parseInt(xmlTools.selectSingleNode(structureNode, "//structure/method/param[@name='start-date']").getAttributes().getNamedItem("index").getNodeValue());
			int indexDateBegin = Integer.parseInt(xmlTools.selectSingleNode(structureNode, "//structure/method/param[@name='time_start']").getAttributes().getNamedItem("index").getNodeValue());
			//int indexDateEnd = Integer.parseInt(xmlTools.selectSingleNode(structureNode, "//structure/method/param[@name='end-date']").getAttributes().getNamedItem("index").getNodeValue());
			int indexDateEnd = Integer.parseInt(xmlTools.selectSingleNode(structureNode, "//structure/method/param[@name='time_end']").getAttributes().getNamedItem("index").getNodeValue());
			String begin = null;
			String end = null;
			callsTmp = new Vector();
			for (Iterator it = dates.iterator() ; it.hasNext() ; ) {
				begin = (String) it.next();
				end = (String) it.next();
				for (Iterator it2 = calls.iterator() ; it2.hasNext() ; ) {
					tmp = (String[]) it2.next();
					tmp[indexDateBegin] = begin;
					tmp[indexDateEnd] = end;
					callsTmp.add(tmp.clone());
				}
			}
			calls = callsTmp;
			// Now, format all SOAP messages, with the content of the Vector calls
			Node f = xmlTools.selectSingleNode(structureNode, "//structure/method/query");
			NodeList nl = f.getChildNodes();
			Node t = null;
			String query = null;
			for (int i = 0 ; i < nl.getLength() ; i++) {
				t = nl.item(i);
				if (t.getNodeType() == Node.CDATA_SECTION_NODE) {
					query = t.getNodeValue().trim();
				}
			}
			callsTmp = new Vector();
//			System.out.println("CALLS:");
			String[] tmp2 = null;
			for (Iterator it = calls.iterator() ; it.hasNext() ; ) {
				tmp2 = (String[]) it.next();
/*
				System.out.print("[" + tmp2[0]);
				for (int i = 1 ; i < (tmp2.length - 2) ; i++) {
					System.out.print(", " + tmp2[i]);
				}
				System.out.println("]");
*/
				callsTmp.add(MessageFormat.format(query, tmp2));
			}
			calls = callsTmp;
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
			exceptions.add(ProviderUtils.reportException("WebServiceParser", t));
		}
/*		
		System.out.println("FINAL TEST:\n===========");
		for (Iterator it = calls.iterator() ; it.hasNext() ; ) {
			System.out.println((String) it.next() + "\n---");
		}
		System.out.println(calls.size() + " Web-Service call(s).");
*/
	}


	private void formatDates() {
		String formatBegin = null;
		String formatEnd = null;
		Node[] formats = null;
		Vector v = new Vector();
		String[] tmp = null;
		int size = 0;
		try {
			Node n = xmlTools.selectSingleNode(mappingNode, "//mapping/param[@name='date']");
			// Getting format for BEGIN and END dates.
			//Node f = xmlTools.selectSingleNode(mappingNode, "//mapping/param/format/start-date");
			Node f = xmlTools.selectSingleNode(mappingNode, "//mapping/param/format/time_start");
			NodeList nl = f.getChildNodes();
			Node t = null;
			for (int i = 0 ; i < nl.getLength() ; i++) {
				t = nl.item(i);
				if (t.getNodeType() == Node.CDATA_SECTION_NODE) {
					formatBegin = t.getNodeValue().trim();
				}
			}
		//	f = xmlTools.selectSingleNode(mappingNode, "//mapping/param/format/end-date");
			f = xmlTools.selectSingleNode(mappingNode, "//mapping/param/format/time_end");
			nl = f.getChildNodes();
			for (int i = 0 ; i < nl.getLength() ; i++) {
				t = nl.item(i);
				if (t.getNodeType() == Node.CDATA_SECTION_NODE) {
					formatEnd = t.getNodeValue().trim();
				}
			}
			// Getting format information.
			size = Integer.parseInt(n.getAttributes().getNamedItem("size").getNodeValue());
			formats = new Node[size];
			for (int i = 0 ; i < size ; i++) {
				formats[i] = xmlTools.selectSingleNode(mappingNode, "//mapping/param/map[@index='" + i + "']");
			}
//			System.out.println("START-DATE:\n" + formatBegin + "\nEND-DATE:\n" + formatEnd);
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
			exceptions.add(ProviderUtils.reportException("WebServiceParser", t));
		}
		// Reformatting all dates...
		String[] objBegin = new String[size];
		String[] objEnd = new String[size];
		String beginDate = null;
		String beginTime = null;
		String endDate = null;
		String endTime = null;
		String form = null;
		for (Iterator it = dates.iterator() ; it.hasNext() ; ) {
			tmp = (String[]) it.next();
			beginDate = tmp[0].substring(0, 10);
			beginTime = tmp[0].substring(11);
			endDate = tmp[1].substring(0, 10);
			endTime = tmp[1].substring(11);
			for (int i = 0 ; i < size ; i++) {
				form = formats[i].getAttributes().getNamedItem("format").getNodeValue();
				if (formats[i].getAttributes().getNamedItem("nature").getNodeValue().equals("DATE")) {
					// Date conversion.
					objBegin[i] = Conversion.convertDate("YYYY-MM-DD", form, beginDate);
					objEnd[i] = Conversion.convertDate("YYYY-MM-DD", form, endDate); 
				} else {
					// Time conversion
					objBegin[i] = Conversion.convertTimeMS("HH:MM:SS", form, beginTime);
					objEnd[i] = Conversion.convertTimeMS("HH:MM:SS", form, endTime);
				}
			}
			v.add(MessageFormat.format(formatBegin, objBegin));
			v.add(MessageFormat.format(formatEnd, objEnd));
		}
		dates = v;
	}


	/**
	 * Gets the code that corresponds to the tag name (name of the XML node).
	 *
	 * @param qName  Name of the node.
	 * @return           The code of the tag.
	 */
	private int getTag(String qName) {
		int i = 0;
		boolean found = false;
		while (!found & (i < tagNames.length)) {
			found = qName.equals(tagNames[i]);
			i++;
		}
		if (!found) {
			return(-1);
		}
		return (i - 1);
	}



	private void addValue(String val) {
		String tmp = (String) paramName.peek();
		try {
			// Get the node that contains mapping information for the current parameter.
			Node n = xmlTools.selectSingleNode(mappingNode, "//mapping/param[@name='" + tmp + "']");
			if (n != null) {
				// If n == null, then there is no mapping for this parameter...
				Node n2 = xmlTools.selectSingleNode(n, "//param/value[@name='" + val.toUpperCase() + "']");
				if (n2 != null) {
					// n2 contains the information to map the value into an archive-specific value...
					NodeList nl = n2.getChildNodes();
					Node n3 = null;
					NamedNodeMap atts = null;
					String param = null;
					String value = null;
					Node att = null;
					Vector v = null;
					Hashtable values = (Hashtable) allValues.get(tmp);
					if (values == null) {
						values = new Hashtable();
					}
					Hashtable hash = (Hashtable) values.get(val);
					if (hash == null) {
						hash = new Hashtable();
					}
					for (int i = 0 ; i < nl.getLength() ; i++) {
						n3 = nl.item(i);
						if ((n3.getNodeType() == Node.ELEMENT_NODE) && (n3.getNodeName().equals("param"))) {
							atts = n3.getAttributes();
							param = atts.getNamedItem("name").getNodeValue();
							att = atts.getNamedItem("value");
							v = (Vector) hash.get(param);
							if (v == null) {
								v = new Vector();
							}
							if (att != null) {
								// Case of unique value.
								value = att.getNodeValue().trim();
								if (!v.equals("")) {
									if (!v.contains(value)) {
										v.add(value);
									} else {
										System.out.println("[DEBUG - WSParser]: Can't add the value " + value + " for the param " + param + " because it has already been added.");
									}
								}
							} else {
								// Case of multiple value.
								for (StringTokenizer st = new StringTokenizer(atts.getNamedItem("values").getNodeValue(), atts.getNamedItem("separator").getNodeValue()) ; st.hasMoreTokens() ; ) {
									value = st.nextToken();
									if (!v.contains(value)) {
										v.add(value);
									}
								}
							}
							hash.put(param, v);
						}
					}
					values.put(val, hash);
					allValues.put(tmp, values);
				} else {
					System.out.println("[DEBUG - WSParser]: No value for " + tmp + "=" + val +".");
				}
			}
		} catch (Throwable t) {
			ProviderMonitor.getInstance().reportException(t);
			exceptions.add(ProviderUtils.reportException("WebServiceParser", t));
		}
	}


	public void characters(char[] ch, int start, int length)
		throws SAXException {
		String str = new String(ch, start, length);
		if (!str.trim().equals("")) {
//			System.out.println("characters: [" + str + "]");
			if (textExpected) {
				gotTheValue = true;
				if (currentNode.equals("value")) {
					temporateValue.append(str);
				} else {
					if (currentNode.equals("start")) {
						if (paramName.peek().equals("date")) {
							if (dateInterval[0] == null) {
								dateInterval[0] = new StringBuffer(str);
							} else {
								dateInterval[0].append(str);
							}
						}
					} else {
						if (currentNode.equals("end")) {
							if (paramName.peek().equals("date")) {
								if (dateInterval[1] == null) {
									dateInterval[1] = new StringBuffer(str);
								} else {
									dateInterval[1].append(str);
								}
							}
						} else {
							System.out.println("ERROR: Don't know what to do with these characters: '" + str + "'.");
						}
					}
				}
//				textExpected = false;
			} else {
				System.out.println("WARNING: Text (" + str + ") not expected here [parent node = " + currentNode + "]:\n'" + str + "'.");
			}
		}
	}


	public void endDocument()
		throws SAXException {
//			System.out.println("...End of the document\n"+results);
		finish();
	}

	public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
//		System.out.println("endElement: " + namespaceURI + " | " + localName + " | " + qName);
		switch(getTag(qName)) {
			case PARAM:
					break;
			case VALUE:
			case START:
			case END:
					if (!gotTheValue) {
						System.out.println("ERROR: No value (text) for a " + qName + " node!");
					}
					gotTheValue = false;
					if (temporateValue != null) {
						addValue(temporateValue.toString());
					}
					temporateValue = null;
					textExpected = false;
				break;
			case INTERVAL:
					if (intervalStartEnd != 0) {
						System.out.println("ERROR: INTERVAL node has the good number of children: Node " + ((intervalStartEnd < 0) ? "<END>" : "<START>") + " is not present.");
					}
					intervalStartEnd = 0;
//					System.out.println("\t\t[X] " + (String) paramName.peek());
					if (((String) paramName.peek()).equals("date")) {
//						System.out.println("adding date: [" + dateInterval[0].toString() + " | " + dateInterval[1].toString() + "].");
						dates.add(new String[] {dateInterval[0].toString(), dateInterval[1].toString()});
					}
					textExpected = false;
				break;
			case DATA:
//					System.out.println("END OF <DATA>");
				break;
		}
		currentNode = (String) stack.pop();
		if (currentNode != null) {
			acceptedNodes = acceptedList[getTag(currentNode)];
		}
	}
	
	public void endPrefixMapping(String prefix)
		throws SAXException {
//			System.out.println("endPrefixMapping: " + prefix);
	}
	
	public void ignorableWhitespace(char[] ch, int start, int length)
		throws SAXException {
//		System.out.println("ignorableWhitespace: " + new String(ch, start, length));
	}
	
	public void processingInstruction(String target, String data)
		throws SAXException {
//			System.out.println("processingInstruction: " + target + " | " + data);
	}
	
	public void setDocumentLocator(Locator locator) {
//		System.out.println("setDocumentLocator");
	}
	
	public void skippedEntity(String name)
		throws SAXException {
//			System.out.println("skippedEntity: " + name);
	}
	
	public void startDocument()
		throws SAXException {
//			System.out.println("Start of the Document...");
		acceptedNodes = new String[] {"data"};
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
		throws SAXException {
//		System.out.println("startElement: " + namespaceURI + " | " + localName + " | " + qName);
		boolean found = false;
		int i = 0;
		// Test if the current node is accepted here...
		while (!found && (i < acceptedNodes.length)) {
			found = qName.equals(acceptedNodes[i]);
			i++;
		}
		if (!found) {
			System.out.println("ERROR: The " + qName + " element is not expected here. The parent element is " + currentNode + ".");
			return;
		}
		if (textExpected) {
			System.out.println("ERROR: A text node was expected here!");
			return;
		}
		// Replace the currentNode value by the current node, and push the old one in the Stack.
		stack.push(currentNode);
		currentNode = qName;
		int tag = getTag(qName);
		acceptedNodes = acceptedList[tag];
		String tmp = null;
		switch(tag) {
			case PARAM:
					paramName.push(atts.getValue("name"));
				break;
			case VALUE:
					textExpected = true;
					temporateValue = new StringBuffer();
				break;
			case INTERVAL:
					textExpected = false;
					gotTheValue = false;
					// When we will reach the </INTERVAL>, we must have intervalStartEnd==0,
					// which means that we have one node <START> and one node <END>.
					intervalStartEnd = 0;
					dateInterval = new StringBuffer[2];
				break;
			case START:
					textExpected = true;
					gotTheValue = false;
					intervalStartEnd--;
				break;
			case END:
					textExpected = true;
					gotTheValue = false;
					intervalStartEnd++;
				break;
			case DATA:
					break;
			default:
					System.out.println("ERROR: Node " + qName + " not supported.");
				break;
		}
	}
	
	public void startPrefixMapping(String prefix, String uri)
		throws SAXException {
//			System.out.println("startPrefixMapping: " + prefix + " | " + uri);
	}

}

