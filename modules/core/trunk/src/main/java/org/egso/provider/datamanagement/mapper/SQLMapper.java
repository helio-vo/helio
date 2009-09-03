package org.egso.provider.datamanagement.mapper;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.SAXParser;

import org.egso.common.context.EGSOContext;
import org.egso.provider.datamanagement.archives.Base;
import org.egso.provider.datamanagement.archives.Field;
import org.egso.provider.datamanagement.archives.Link;
import org.egso.provider.datamanagement.archives.MixedArchive;
import org.egso.provider.datamanagement.archives.SQLArchive;
import org.egso.provider.datamanagement.archives.Table;
import org.egso.provider.datamanagement.connector.ResultsFormatter;
import org.egso.provider.datamanagement.connector.SQLConnector;
import org.egso.provider.query.ProviderQuery;
import org.egso.provider.query.ProviderTable;
import org.egso.provider.query.SQLQuery;
import org.egso.provider.utils.XMLTools;
import org.egso.provider.utils.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * Mapper that creates the SQL Query.
 *
 * @author    Romain LINSOLAS
 * @version   2.1 - 26/10/2004
 */
/*
2.1 - 26/10/2004:
	Adaptation to the SAX Parser instead of the Xerces SAX Parser.
2.0 - 20/10/2004:
	Recreation of the SQL Mapper using the new SQL Base mapping.
*/
public class SQLMapper extends Thread implements Mapper {

	/**
	 * JAVADOC: Description of the Field
	 */
	private ProviderQuery providerQuery = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private ProviderTable providerTable = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private SQLArchive archive = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private MixedArchive mixedArchive = null;
	/**
	 * JAVADOC: Description of the Field
	 */
	private Base sqlBase = null;


	/**
	 * TODO: Constructor for the DataPresentationManagerImpl object
	 *
	 * @param tg       JAVADOC: Description of the Parameter
	 * @param pq       JAVADOC: Description of the Parameter
	 * @param arch     JAVADOC: Description of the Parameter
	 * @param results  JAVADOC: Description of the Parameter
	 */
	public SQLMapper(ThreadGroup tg, ProviderQuery pq, SQLArchive arch, ProviderTable results) {
		super(tg, "sql-mapper@" + arch.getID());
		providerQuery = pq;
		providerTable = results;
		archive = arch;
		mixedArchive = null;
		sqlBase = archive.getBase();
	}


	/**
	 * JAVADOC: Constructor for the SQLMapper object
	 *
	 * @param tg       JAVADOC: Description of the Parameter
	 * @param pq       JAVADOC: Description of the Parameter
	 * @param arch     JAVADOC: Description of the Parameter
	 * @param results  JAVADOC: Description of the Parameter
	 */
	public SQLMapper(ThreadGroup tg, ProviderQuery pq, MixedArchive arch, ProviderTable results) {
		super(tg, "sql-mapper@" + arch.getID());
		providerQuery = pq;
		providerTable = results;
		archive = null;
		mixedArchive = arch;
		sqlBase = mixedArchive.getBase();
	}


	/**
	 * JAVADOC: Main processing method for the SQLMapper object
	 */
	public void run() {
		// Creates the query.
		SQLQuery sqlQuery = createQuery();
		InputSource is = new InputSource(new ByteArrayInputStream(XMLUtils.nodeToString(providerQuery.getData()).getBytes()));
		is.setEncoding("ISO-8859-1");
		SQLParser parser = new SQLParser(sqlBase);
		try {
			SAXParser saxParser = XMLTools.getSAXParser();
			saxParser.parse(is, parser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sqlQuery.addWhere(parser.getQuery());
		System.out.println("SQL Query created:\n" + sqlQuery.toString());
		// Replace the < and > by [lt] and [gt] to avoid problem with XML.
		providerTable.getContext().addParameter("SQLQuery", EGSOContext.PARAMETER_DEBUG, sqlQuery.toString().replaceAll("<", "[lt]").replaceAll(">", "[gt]"));
		if (mixedArchive == null) {
			Vector v = new Vector();
			NodeList nl = providerQuery.getSelect().getChildNodes();
			Node n = null;
			for (int i = 0; i < nl.getLength(); i++) {
				n = nl.item(i);
				if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName().equals("field"))) {
					v.add(n.getAttributes().getNamedItem("name").getNodeValue());
				}
			}
			//ResultsFormatter rf = new ResultsFormatter(archive, v);
			ResultsFormatter rf = new ResultsFormatter(archive, providerTable.getFields());
			SQLConnector connector = new SQLConnector(archive, sqlQuery, providerTable, rf);
			connector.query();
		} else {
			MixedMapper map = new MixedMapper(mixedArchive, sqlQuery, providerTable);
			map.query();
		}
	}


	/**
	 * JAVADOC: Description of the Method
	 */
	private SQLQuery createQuery() {
		SQLQuery sqlQuery = new SQLQuery();
		Vector select = sqlBase.createNewSelect();
		Vector from = sqlBase.createNewFrom();
		Vector where = sqlBase.createNewWhere();
		// ### Create the SELECT part ###
		Field[] tmp = null;
		Table table = null;
		String x = null;
		for (Iterator it = providerTable.getFields().iterator() ; it.hasNext() ; ) {
			// For each field required, adds it in the 'select' Vector, and
			// adds its Table in 'from' Vector.
			x = (String) it.next();
			tmp = sqlBase.getMappedFields(x);
			if (!sqlBase.isConcatField(x)) {
				if (tmp != null) {
					if (!select.contains(tmp[0])) {
						select.add(tmp[0]);
					}
					table = tmp[0].getTable();
					if (!from.contains(table)) {
						from.add(table);
					}
					// In case of an interval.
					if (tmp.length == 2) {
						if (!select.contains(tmp[1])) {
							select.add(tmp[1]);
						}
						table = tmp[1].getTable();
						if (!from.contains(table)) {
							from.add(table);
						}
					}
				} else {
					providerTable.notAvailableField(x);
				}
			} else {
				select.add(sqlBase.getMapElement(x).getConcatenationString());
				for (int j = 0 ; j < tmp.length ; j++) {
					table = tmp[j].getTable();
					if (!from.contains(table)) {
						from.add(table);
					}
				}
			}
		}
		// ### Create the FROM part ###

		// ### Create the WHERE part ###
		Vector links = new Vector();
		Link link = null;
		for (int i = 0; i < from.size(); i++) {
//			table = (Table) from.get(i);
			for (int j = (i + 1); j < from.size(); j++) {
				for (Iterator it = sqlBase.getLinkMatrix().getConnection(((Table) from.get(i)).getName(), ((Table) from.get(j)).getName()).iterator(); it.hasNext(); ) {
					link = (Link) it.next();
					if (!links.contains(link)) {
						links.add(link);
					}
				}
			}
		}
		// Need optimization for removing useless link.
		for (Iterator it = links.iterator(); it.hasNext(); ) {
			where.add(((Link) it.next()).print());
		}
		sqlQuery.setSelect(select);
		sqlQuery.setFrom(from);
		StringBuffer sb = new StringBuffer();
		for (int i = 0 ; i < where.size() ; i++) {
			sb.append((String) where.get(i) + " AND ");
		}
		sqlQuery.setWhere(sb.toString());
		return (sqlQuery);
	}

}

