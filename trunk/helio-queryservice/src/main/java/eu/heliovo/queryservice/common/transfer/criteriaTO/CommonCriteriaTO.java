/* #ident	"%W%" */
package eu.heliovo.queryservice.common.transfer.criteriaTO;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;

import uk.ac.starlink.table.StarTable;

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
	private String queryForm;
	private String tableName;
	private StarTable[] tables;
	private String whereClause;
	private String queryStatus;
	private String queryDescription;
	private String startRow;
	private String noOfRows;
	private String updatedQuery;
	private int maxRecordsAllowed;
	
	public CommonCriteriaTO(){
		this.setIPageNumber(0);
		this.setIRowsPerPage(10);
	}
	
	public String getQueryDescription() {
		return queryDescription;
	}

	public void setQueryDescription(String queryDescription) {
		this.queryDescription = queryDescription;
	}

	public StarTable[] getTables() {
		return tables;
	}

	public void setTables(StarTable[] tables) {
		this.tables = tables;
	}

	public String getQueryForm() {
		return queryForm;
	}

	public void setQueryForm(String queryForm) {
		this.queryForm = queryForm;
	}

		
	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public String getStartRow() {
		return startRow;
	}

	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}

	public String getNoOfRows() {
		return noOfRows;
	}

	public void setNoOfRows(String noOfRows) {
		this.noOfRows = noOfRows;
	}

	public String getUpdatedQuery() {
		return updatedQuery;
	}

	public void setUpdatedQuery(String updatedQuery) {
		this.updatedQuery = updatedQuery;
	}

	public int getMaxRecordsAllowed() {
		return maxRecordsAllowed;
	}

	public void setMaxRecordsAllowed(int maxRecordsAllowed) {
		this.maxRecordsAllowed = maxRecordsAllowed;
	}
	
	
	
}
