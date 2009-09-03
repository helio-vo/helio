package org.egso.provider.datamanagement.archives.mapping;


import java.util.Enumeration;
import java.util.Hashtable;

import org.egso.provider.datamanagement.archives.Archive;
import org.egso.provider.utils.XMLTools;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MappingTable {

	private Archive archive = null;
	private Hashtable mapping = null;


	public MappingTable(Archive a) {
		archive = a;
		mapping = new Hashtable();
		init(archive.getConfNode());
	}



	private void init(Node conf) {
		int archiveType = archive.isMixed() ? archive.getMapperType() : archive.getType();
		NodeList params = null;
		NodeList masks = null;
		try {
			params = XMLTools.getInstance().selectNodeList(conf, "//map/mapping/param");
			if ((archiveType == Archive.FTP_ARCHIVE) || (archiveType == Archive.HTTP_ARCHIVE)) {
				masks = XMLTools.getInstance().selectNodeList(conf, "//structure/mask/param");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Node n = null;
		MappingObject obj = null;
		for (int i = 0 ; i < params.getLength() ; i++) {
			n = params.item(i).getAttributes().getNamedItem("map");
			if ((n == null) || (!n.getNodeValue().equals("IGNORE"))) {
				switch (archiveType) {
					case Archive.HTTP_ARCHIVE:
					case Archive.FTP_ARCHIVE:
							obj = new FTPHTTPMappingObject(params.item(i), masks);
						break;
					case Archive.SQL_ARCHIVE:
							obj = new SQLMappingObject(params.item(i));
						break;
					case Archive.WEB_SERVICES_ARCHIVE:
							obj = new WebServicesMappingObject(params.item(i));
						break;
				}
	//			System.out.println(obj.toString());
				mapping.put(obj.getEGSOName(), obj);
			}
		}
	}

	public MappingObject getFromEGSO(String egso) {
		return ((MappingObject) mapping.get(egso));
	}

	public MappingObject getFromArchive(String param) {
		boolean found = false;
		Enumeration elts = mapping.elements();
		MappingObject obj = null;
		String[] tmp = null;
		while (!found && elts.hasMoreElements()) {
			obj = (MappingObject) elts.nextElement();
			tmp = obj.getArchiveNames();
			int i = 0;
			while (!found && (i < tmp.length)) {
				found = tmp[i].equals(param);
				i++;
			}
		}
		if (found) {
			return (obj);
		}
		return (null);
	}

}

