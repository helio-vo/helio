package org.egso.provider.datamanagement.archives.mapping;


import java.util.Enumeration;
import java.util.Hashtable;

import org.w3c.dom.Node;


public class WebServicesMappingObject implements MappingObject {


	private String nameEGSO = null;
	private String valueArchive = null;
	private Hashtable egso2archive = null;
	private Hashtable archive2egso = null;


	public WebServicesMappingObject(Node node) {
		egso2archive = new Hashtable();
		archive2egso = new Hashtable();
		init(node);
	}
	
	private void init(Node node) {
		// TODO
		
	}
	

	public String getEGSOName() {
		return (nameEGSO);
	}

	public String getArchiveName() {
		return (valueArchive);
	}

	public String[] getArchiveNames() {
		return (new String[] {valueArchive});
	}

	public String egso2archive(String value) {
		Object obj = egso2archive.get(value);
		if (obj instanceof String) {
			return ((String) obj);
		}
		return (null);
	}

	public String archive2egso(String value) {
		Object obj = archive2egso.get(value);
		if (obj instanceof String) {
			return ((String) obj);
		}
		return (null);
	}

	public int getType() {
		return (MappingObject.WEB_SERVICES);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("WEB SERVICES Mapping Object for the EGSO parameter '" + nameEGSO + "'\n");
		sb.append("Archive Value: " + valueArchive);
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


}

