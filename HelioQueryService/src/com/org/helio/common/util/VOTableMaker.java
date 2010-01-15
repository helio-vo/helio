package com.org.helio.common.util;

import java.io.BufferedWriter;
import java.io.IOException;

import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.RowListStarTable;
import uk.ac.starlink.votable.DataFormat;
import uk.ac.starlink.votable.VOSerializer;

public class VOTableMaker {

	private Object [] values = null;
    private RowListStarTable helioRowList = null;    
    private  int numCols;
    
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
    
    public void writeBeginVOTable(BufferedWriter out, String description,String status) throws IOException {
    	 //Adding response header start for WebService VOTABLE.
		 if(status!=null && !status.equals("")){
			 out.write("<helio:queryResponse xmlns:helio=\"http://helio-vo.eu/xml/QueryService/v0.1\">");
		 }
         out.write( "<VOTABLE version='1.1' xmlns=\"http://www.ivoa.net/xml/VOTable/v1.1\">\n" );
         out.write( "<RESOURCE>\n" );
         out.write( "<DESCRIPTION>" + description + "</DESCRIPTION>\n" );
    }
    
    public void writeTable(BufferedWriter out) throws IOException {
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
    
    public void writeEndVOTable(BufferedWriter out,String status) throws IOException {
        out.write( "</RESOURCE>\n" );
        out.write( "</VOTABLE>\n" );
        if(status!=null && !status.equals("")){
        	out.write("</helio:queryResponse>");
        } 
       out.close();
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
