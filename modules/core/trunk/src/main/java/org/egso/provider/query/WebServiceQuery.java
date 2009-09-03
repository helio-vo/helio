package org.egso.provider.query;

import java.util.Iterator;
import java.util.Vector;


public class WebServiceQuery extends ArchiveQuery {
	
    //***********************
    // ATTRIBUTES
    //***********************
	private Vector calls = null;
    private Vector selectedFields = null;
	private int index = 0;
	
    //***********************
    // CONSTRUCTORS
    //***********************
    public WebServiceQuery() {
    	super(ArchiveQuery.WEB_SERVICE_ARCHIVE);
		calls = new Vector();
		index = 0;
		selectedFields = new Vector();
    }


	public void setAllCalls(Vector v) {
		calls = v;
	}
	
	public void addCall(String call) {
		calls.add(call);
	}
	
	
	public String getNextCall() {
		if (index < calls.size()) {
			index++;
			return ((String) calls.get(index - 1));
		}
		return (null);
	}

	public Vector getAllCalls() {
		return (calls);
	}
	
	
    public void setSelectedFields(Vector fields){
        selectedFields=fields;
    }
	
	public void addField(String field) {
		selectedFields.add(field);
	}
    
	public void setFields(Vector v) {
		selectedFields = v;
	}
	
    public Vector getSelectedFields(){
        return (selectedFields);
    }
    
    public String getSelectedFieldsAsString(){
        String output="";
        for(int i=0;i<selectedFields.size();i++)
        {
            if(i==0) output=(String)selectedFields.get(i);
            else     output=output+" | "+selectedFields.get(i);
        }
        return (output);
    }

    public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator it = calls.iterator() ; it.hasNext() ; ) {
			sb.append((String) it.next());
		}
		return (sb.toString());
    }
}
