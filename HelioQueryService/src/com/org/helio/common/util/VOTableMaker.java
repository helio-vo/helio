package com.org.helio.common.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.org.helio.common.transfer.criteriaTO.CommonCriteriaTO;

import uk.ac.starlink.table.StarTable;

import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.DescribedValue;
import uk.ac.starlink.table.RowListStarTable;
import uk.ac.starlink.table.Tables;
import uk.ac.starlink.table.formats.HTMLTableWriter;
import uk.ac.starlink.table.jdbc.SequentialResultSetStarTable;
import uk.ac.starlink.votable.DataFormat;
import uk.ac.starlink.votable.VOSerializer;
import uk.ac.starlink.votable.VOTableWriter;

public class VOTableMaker {

	private Object [] values = null;
    private RowListStarTable helioRowList = null;    
    private  int numCols;
    
    protected final  Logger logger = Logger.getLogger(this.getClass());
    
    public VOTableMaker(){
    	
    }
    
    
    
    public static void writeResultSet( ResultSet rset, BufferedWriter out ) throws Exception {
        StarTable table = new SequentialResultSetStarTable( rset );
        new VOTableWriter().writeInlineStarTable(table, out );
    } 
    
    //Writing all the details into VOtable.
    public static void writeTables( CommonCriteriaTO comCriteriaTO ) throws IOException {
    	BufferedWriter out = new BufferedWriter( comCriteriaTO.getPrintWriter() );
    	StarTable[] tables=comCriteriaTO.getTables();
    	String status=comCriteriaTO.getStatus();
    	    		
    	//Adding response header start for WebService VOTABLE.
		if(status!=null && !status.equals("")){
			 out.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
		}
        out.write( "<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n" );
        out.write( "<RESOURCE>\n" );
        out.write( "<DESCRIPTION>"+ConfigurationProfiler.getInstance().getProperty("sql.votable.head.desc")+"</DESCRIPTION>\n" );
        out.write( "<INFO name=\"QUERY_STATUS\" value=\""+comCriteriaTO.getQueryStatus()+"\"/>");
        if(comCriteriaTO.getQueryStatus().equals("ERROR")){
        	 out.write( "<INFO name=\"QUERY_STATUS\" value=\""+comCriteriaTO.getQueryDescription()+"\"/>");
        	 //out.write( "<DESCRIPTION>"+comCriteriaTO.getUpdatedQuery()+"</DESCRIPTION>\n" );
        	// out.write("</INFO>");
        }
       	//out.write("<INFO ID=\""+comCriteriaTO.getQueryStatus()+"\" name=\""+comCriteriaTO.getQueryStatus()+"\" value=\""+comCriteriaTO.getQueryDescription()+"\"/>");
        if(tables!=null){
	        for ( int i = 0; i < tables.length; i++ ) {
	            VOSerializer.makeSerializer( DataFormat.TABLEDATA, tables[ i ] )
	                        .writeInlineTableElement( out );
	        }
        }
        out.write( "</RESOURCE>\n" );
        out.write( "</VOTABLE>\n" );
      //Adding response header start for WebService VOTABLE.
		if(status!=null && !status.equals("")){
			 out.write("</helio:queryResponse>");
		}
        out.flush();
        out.close();
    }
    
    //Setting column property.
    public static void setColInfoProperty(StarTable[] tables,String[] listName){
    	   	
    	for ( int i = 0; i < tables.length; i++ ) {
    		//Column Description
    		//String[] columnDesc=ConfigurationProfiler.getInstance().getProperty("sql.columndesc."+listName[i]).split("::");
    		//Column UCD's
    		//String[] columnUcd=ConfigurationProfiler.getInstance().getProperty("sql.columnucd."+listName[i]).split("::");
    		
    		for(int j=0;j<tables[ i ].getColumnCount();j++){
    			//Setting UCD's for column.
    			//tables[ i ].getColumnInfo( j ).setUCD(columnUcd[j]);
    			//Setting Description for column.
    			//tables[ i ].getColumnInfo( j ).setDescription(columnDesc[j]);
    			Tables.setUtype( tables[ i ].getColumnInfo( j ), "xx:foo.bar" );
    		}
    	}
    	
    }

    
    
	
   
}
