/* #ident	"%W%" */
package com.org.helio.common.transfer.criteriaTO;

import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;

import com.org.helio.common.transfer.CommonTO;

public class CommonCriteriaTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer iPageNumber;
	private Integer iRowsPerPage;
	private Integer noOfPages;
	private String userId;
	private String commonQuery;
	private Integer noOfRecords;
	private String startDateTime;
	private String endDateTime;	
	private boolean paginated = false;
	private String[] commonName;
	private String[] commonDesc;
	private String[] commonUcd;
	private Writer printWriter;
	HashMap<String,CommonTO> hmbColumnList;
	private String status;
	private String instruments;
	private String listName;
	private String query;
	HashMap<String,String> paramData;
	
	public CommonCriteriaTO(){
		this.setIPageNumber(0);
		this.setIRowsPerPage(10);
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	public HashMap<String, String> getParamData() {
		return paramData;
	}
	
	public void setParamData(HashMap<String, String> paramData) {
		this.paramData = paramData;
	}

	public String getInstruments() {
		return instruments;
	}
	public void setInstruments(String instruments) {
		this.instruments = instruments;
	}
	
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HashMap<String, CommonTO> getHmbColumnList() {
		return hmbColumnList;
	}
	public void setHmbColumnList(HashMap<String, CommonTO> hmbColumnList) {
		this.hmbColumnList = hmbColumnList;
	}
	public Writer getPrintWriter() {
		return printWriter;
	}
	public void setPrintWriter(Writer printWriter) {
		this.printWriter = printWriter;
	}
	public String[] getCommonName() {
		return commonName;
	}
	public void setCommonName(String[] commonName) {
		this.commonName = commonName;
	}
	public String[] getCommonDesc() {
		return commonDesc;
	}
	public void setCommonDesc(String[] commonDesc) {
		this.commonDesc = commonDesc;
	}
	public String[] getCommonUcd() {
		return commonUcd;
	}
	public void setCommonUcd(String[] commonUcd) {
		this.commonUcd = commonUcd;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getCommonQuery() {
		return commonQuery;
	}
	public void setCommonQuery(String commonQuery) {
		this.commonQuery = commonQuery;
	}
	public Integer getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}
	public Integer getIPageNumber() {
		return iPageNumber;
	}
	public void setIPageNumber(Integer pageNumber) {
		iPageNumber = pageNumber;
	}
	public Integer getIRowsPerPage() {
		return iRowsPerPage;
	}
	public void setIRowsPerPage(Integer rowsPerPage) {
		iRowsPerPage = rowsPerPage;
	}
	public void setPaginated(boolean paginated){
		this.paginated = paginated;
	}
	
	public boolean isPaginated(){
		return paginated;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	
	
}