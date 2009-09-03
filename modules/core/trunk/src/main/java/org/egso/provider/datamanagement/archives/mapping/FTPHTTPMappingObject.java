package org.egso.provider.datamanagement.archives.mapping;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.egso.provider.utils.Conversion;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class FTPHTTPMappingObject implements MappingObject {


	private String nameEGSO = null;
	private String nameArchive = null;
	private Hashtable egso2archive = null;
	private Hashtable archive2egso = null;
	private String none = null;
	private String[] nones = null;
	private String format = null;
	private String[] formats = null;
	private int index = -1;
	private int[] indexes = null;
	private String operation = null;
	private String[] operations = null;
	private int type = UNKNOWN;
	private static final int UNKNOWN = -1;
	private static final int PARAMETER = 0;
	private static final int TIME = 1;
	private static final int DATE = 2;


	public FTPHTTPMappingObject(Node node, NodeList masks) {
		egso2archive = new Hashtable();
		archive2egso = new Hashtable();
		init(node, masks);
	}
	
	
	private void init(Node node, NodeList masks) {
		NamedNodeMap atts = node.getAttributes();
		nameEGSO = atts.getNamedItem("name").getNodeValue();
		if (nameEGSO.equals("TIME")) {
			type = TIME;
			none = atts.getNamedItem("none").getNodeValue();
			format = atts.getNamedItem("format").getNodeValue();
			index = Integer.parseInt(atts.getNamedItem("index").getNodeValue());
			operation = getOperation(index, masks);
		} else {
			if (nameEGSO.equals("DATE")) {
				type = DATE;
				Node n = null;
				NodeList child = node.getChildNodes();
				Vector v = new Vector();
				for (int x = 0 ; x < child.getLength() ; x++) {
					n = child.item(x);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						atts = n.getAttributes();
						if (n.getNodeName().toLowerCase().equals("value")) {
							v.add(atts.getNamedItem("index").getNodeValue());
							v.add(atts.getNamedItem("format").getNodeValue());
							v.add(atts.getNamedItem("none").getNodeValue());
						}
					}
				}
				int i = 0;
				indexes = new int[v.size() / 3];
				formats = new String[v.size() / 3];
				nones = new String[v.size() / 3];
				operations = new String[v.size() / 3];
				Iterator it = v.iterator();
				while (it.hasNext()) {
					indexes[i] = Integer.parseInt((String) it.next());
					formats[i] = (String) it.next();
					nones[i] = (String) it.next();
					operations[i] = getOperation(indexes[i], masks);
					i++;
				}
			} else {
				type = PARAMETER;
				nameArchive = atts.getNamedItem("mappedName").getNodeValue();
				none = atts.getNamedItem("none").getNodeValue();
				if (atts.getNamedItem("multiple-index").getNodeValue().toLowerCase().equals("no")) {
					// One index.
					index = Integer.parseInt(atts.getNamedItem("index").getNodeValue());
					operation = getOperation(index, masks);
				} else {
					// Multiple indexes.
					
//					getOperation for all indexes.
				}
				Node n = null;
				NodeList child = node.getChildNodes();
				for (int x = 0 ; x < child.getLength() ; x++) {
					n = child.item(x);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						atts = n.getAttributes();
						if (n.getNodeName().toLowerCase().equals("value")) {
							egso2archive.put(atts.getNamedItem("name").getNodeValue(), atts.getNamedItem("value").getNodeValue());
							archive2egso.put(atts.getNamedItem("value").getNodeValue(), atts.getNamedItem("name").getNodeValue());
						} else {
							if (n.getNodeName().toLowerCase().equals("valueout")) {
								archive2egso.put(atts.getNamedItem("value").getNodeValue(), atts.getNamedItem("name").getNodeValue());
							} else {
								if (n.getNodeName().toLowerCase().equals("valuein")) {
									egso2archive.put(atts.getNamedItem("name").getNodeValue(), atts.getNamedItem("value").getNodeValue());
								}
							}
						}
					}
				}
			}
		}
	}
	
	private String getOperation(int index, NodeList masks) {
		Node n = null;
		StringBuffer op = new StringBuffer();
		boolean hasToken = false;
		for (int i = 0 ; i < masks.getLength() ; i++) {
			n = masks.item(i);
			NamedNodeMap atts = n.getAttributes();
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				if (n.getNodeName().equals("param") && (Integer.parseInt(n.getAttributes().getNamedItem("index").getNodeValue()) == index)) {
					if (atts.getNamedItem("token") != null) {
						op.append("token," + atts.getNamedItem("token").getNodeValue() + "," + atts.getNamedItem("nb").getNodeValue());
						hasToken = true;
					}
					if (atts.getNamedItem("start") != null) {
						if (hasToken) {
							op.append(",");
						}
						op.append("substring," + atts.getNamedItem("start").getNodeValue());
						if (atts.getNamedItem("end") != null) {
							op.append("," + atts.getNamedItem("end").getNodeValue());
						}
					}
					if (atts.getNamedItem("format") != null) {
						op.append(",format," + atts.getNamedItem("format").getNodeValue());
					}
				}
			}
		}
		return (op.toString());
	}

	public String getEGSOName() {
		return (nameEGSO);
	}

	public String getArchiveName() {
		return (nameArchive);
	}

	public String[] getArchiveNames() {
		return (new String[] {nameArchive});
	}

	public String egso2archive(String value) {
		Object obj = egso2archive.get(value);
		if (obj instanceof String) {
			return ((String) obj);
		}
		return (none);
	}

	public String archive2egso(String value) {
//		System.out.println("Mapping the value '" + value + "'");
		String oper = operation;
		if (oper == null) {
			int i = 0;
			boolean found = false;
			while (!found && (i < operations.length)) {
				found = (operations[i] != null) && (!operations[i].trim().equals(""));
				i++;
			}
			if (found) {
				oper = operations[--i];
			}
		}
//		System.out.println("Operation> " + oper);
		StringTokenizer tokenOperation = new StringTokenizer(oper, ",");
		String op = tokenOperation.nextToken();
		if (op.equals("token")) {
			String token = tokenOperation.nextToken();
			int x = Integer.parseInt(tokenOperation.nextToken());
			StringTokenizer tokenValue = new StringTokenizer(value, token);
			while (x > 1) {
				tokenValue.nextToken();
				x--;
			}
			value = tokenValue.nextToken();
			if (tokenOperation.hasMoreTokens()) {
				op = tokenOperation.nextToken();
			} else {
				op = null;
			}
		}
		if ((op != null) && op.equals("substring")) {
			int start = Integer.parseInt(tokenOperation.nextToken());
			String end = tokenOperation.nextToken();
			if (end == null) {
				value = value.substring(start);
			} else {
				value = value.substring(start, Integer.parseInt(end) + 1);
			}
		}
		if (tokenOperation.hasMoreTokens() && tokenOperation.nextToken().equals("format")) {
			String format = tokenOperation.nextToken();
			if (format.indexOf("YY") != -1) {
				// Convert Date...
				value = Conversion.convertDate(format, "YYYY-MM-DD", value);
			} else {
				// Convert Time...
				value = Conversion.convertAllTime(format, "HH:MM:SS", value);
			}
		}
//		System.out.println("-> '" + value + "'.");
		Object obj = archive2egso.get(value);
		if (obj instanceof String) {
//			System.out.println("--> '" + (String) obj + "'.");
			return ((String) obj);
		}
		return (value);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("FTP/HTTP Mapping Object for the EGSO parameter '" + nameEGSO + "'\n");
		sb.append("Archive value: " + nameArchive + " | In case of no values: " + none + "\n");
		sb.append("MAPPING TABLE :\nEGSO -> ARCHIVE:\n");
		String tmp = null;
		for (Enumeration e = egso2archive.keys() ; e.hasMoreElements() ; ) {
			tmp = (String) e.nextElement();
			sb.append("\t" + tmp + " -> " + (String) egso2archive.get(tmp) + "\n");
		}
		sb.append("ARCHIVE -> EGSO:\n");
		for (Enumeration e = archive2egso.keys() ; e.hasMoreElements() ; ) {
			tmp = (String) e.nextElement();
			sb.append("\t" + tmp + " -> " + (String) archive2egso.get(tmp) + "\n");
		}
		return (sb.toString());
	}

	public int getType() {
		return (MappingObject.FTP_HTTP);
	}

}

