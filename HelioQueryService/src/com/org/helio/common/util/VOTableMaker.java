package com.org.helio.common.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import uk.ac.starlink.table.StarTable;

import uk.ac.starlink.table.ColumnInfo;
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
    
    public VOTableMaker(ColumnInfo[] Cols) {
        helioRowList = new RowListStarTable( Cols );
        numCols = Cols.length;
        values = new Object[numCols]; 
       
    }
    
    public void addRow() {
    	helioRowList.addRow( values );    	
        values = new Object[numCols];
    }
    
    public long getRowCount() {
        return helioRowList.getRowCount();
    }
    
    public static void writeVOTableHeader(BufferedWriter out,String status) throws IOException 
    {
    	//Adding response header start for WebService VOTABLE.
		 if(status!=null && !status.equals("")){
			 out.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
		 }
		 out.write( "<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n" );
    	
    }
    
    public static void writeVOTableHeader(OutputStream out,String status) throws IOException 
    {
    	//Adding response header start for WebService VOTABLE.
		 if(status!=null && !status.equals("")){
			 out.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">".getBytes());
		 }
		 out.write( "<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n".getBytes() );
    	
    }
    
    public void writeBeginVOTable(BufferedWriter out, String description) throws IOException {
         out.write( "<RESOURCE>\n" );
         out.write( "<DESCRIPTION>" + description + "</DESCRIPTION>\n" );
    }
    
    public void  writeTable(BufferedWriter out) throws IOException {
        VOSerializer vos = VOSerializer.makeSerializer( DataFormat.TABLEDATA, helioRowList );
        vos.writeInlineTableElement(out);
        out.flush();
        helioRowList.clearRows();
        vos = null;
        values = null;
        values = new Object[numCols];
    }
    
    private void clearValues() {
        for(int i = 0;i < values.length;i++) {
            values[i] = null;
        }
    }
    
    public void writeEndVOTable(BufferedWriter out) throws IOException {
        out.write( "</RESOURCE>\n" );
        
    }
    
    public static void writeVOTableFooter(BufferedWriter out,String status) throws IOException 
    {
    	out.write( "</VOTABLE>\n" );
    	//Adding response header start for WebService VOTABLE.
		 if(status!=null && !status.equals("")){
			 out.write("</helio:queryResponse>");
		 }
		 out.close();
    	
    }
    
    public static void writeVOTableFooter(OutputStream out,String status) throws IOException 
    {
    	out.write( "</VOTABLE>\n".getBytes() );
    	//Adding response header start for WebService VOTABLE.
		 if(status!=null && !status.equals("")){
			 out.write("</helio:queryResponse>".getBytes());
		 }
		 out.close();
    	
    }
    
    public static void writeResultSet( ResultSet rset, BufferedWriter out ) throws Exception {
        StarTable table = new SequentialResultSetStarTable( rset );
        new VOTableWriter().writeInlineStarTable(table, out );
    } 
    
    public static void writeTables( StarTable[] tables, BufferedWriter out,String status ) throws IOException {
        
    	//Adding response header start for WebService VOTABLE.
		if(status!=null && !status.equals("")){
			 out.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
		}
        out.write( "<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n" );
        out.write( "<RESOURCE>\n" );
        out.write( "<DESCRIPTION>Some tables</DESCRIPTION>\n" );
        for ( int i = 0; i < tables.length; i++ ) {
            VOSerializer.makeSerializer( DataFormat.TABLEDATA, tables[ i ] )
                        .writeInlineTableElement( out );
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
    
    public static void setColInfoProperty(StarTable[] tables,String[] listName){
    	   	
    	for ( int i = 0; i < tables.length; i++ ) {
    		//Column Description
    		String[] columnDesc=ConfigurationProfiler.getInstance().getProperty("sql.columndesc."+listName[i]).split("::");
    		//Column UCD's
    		String[] columnUcd=ConfigurationProfiler.getInstance().getProperty("sql.columnucd."+listName[i]).split("::");
    		
    		for(int j=0;j<tables[ i ].getColumnCount();j++){
    			//Setting UCD's for column.
    			tables[ i ].getColumnInfo( j ).setUCD(columnUcd[j]);
    			//Setting Description for column.
    			tables[ i ].getColumnInfo( j ).setDescription(columnDesc[j]);
    			Tables.setUtype( tables[ i ].getColumnInfo( j ), "xx:foo.bar" );
    		}
    	}
    	
    }

    
	public Object[] getValues() {
		return values;
	}
	
	public void setValues(Object[] values) {
		this.values = values;
	}
	
	public int getNumCols() {
		return numCols;
	}
	
	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}
   
}
