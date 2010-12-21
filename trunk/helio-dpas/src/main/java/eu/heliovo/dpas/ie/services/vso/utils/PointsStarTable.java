package eu.heliovo.dpas.ie.services.vso.utils;

import eu.heliovo.dpas.ie.services.vso.provider.VSOProvider;
import eu.heliovo.dpas.ie.services.vso.service.org.virtualsolar.VSO.VSOi.ProviderQueryResponse;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.DescribedValue;
import uk.ac.starlink.table.RandomStarTable;
import uk.ac.starlink.votable.VOStarTable;

public class PointsStarTable  extends RandomStarTable {
	//Start Date
	ColumnInfo colStartDate=new ColumnInfo( "Start Date", String.class, "Measurement Start Date" );
	//End date
	ColumnInfo colEndDate=new ColumnInfo( "End Date", String.class, "Measurement End Date" );
    // Define the metadata object for each of the columns.
    ColumnInfo[] colInfos_ = new ColumnInfo[] {
        new ColumnInfo( "Instrument Name", String.class, "Instrument name" ),
        new ColumnInfo( "URL", String.class, "Dummy URL to fits file" ),
        new ColumnInfo( "Provider", String.class, "Provider Name" ),
        colStartDate,colEndDate,
    };

    // Member variables are arrays holding the actual data.
    String url_;
    ProviderQueryResponse	resp_;
    int nRow_;
    String provider_;
    String status_;
    String[] urlList;
    public PointsStarTable( ProviderQueryResponse	resp,String url,String provider,String status ) {
    	resp_=resp;
    	url_=url;
    	if(resp.getNo_of_records_returned()!=null)
    		nRow_=resp.getNo_of_records_returned();
    	if(provider!=null)
    		provider_=provider;
    	status_=status;
    	//VSO Provider
    	VSOProvider vsoPvr=new VSOProvider();
    	//Getting list of File Id
    	urlList=vsoPvr.getVsoURL(resp, provider);
    	//
    	colStartDate.setAuxDatum( new DescribedValue( VOStarTable.XTYPE_INFO,"iso8601"));
    	//
    	colEndDate.setAuxDatum( new DescribedValue( VOStarTable.XTYPE_INFO,"iso8601"));
    }

    public int getColumnCount() {
        return 5;
    }
      
    public long getRowCount() {
        return nRow_;
    }

    public ColumnInfo getColumnInfo( int icol ) {
        return colInfos_[ icol ];
    }
    
  
    public Object getCell( long lrow, int icol ) {
        int irow = checkedLongToInt( lrow );
        if(resp_!=null && resp_.getRecord()!=null){
	        switch ( icol ) {
	        	case 0: return resp_.getRecord()[irow].getInstrument();
	            case 1: return urlList[irow];
	            case 2: return resp_.getRecord()[irow].getProvider();
	            case 3: return VsoUtils.changeFormat(resp_.getRecord()[irow].getTime().getStart());
	            case 4: return VsoUtils.changeFormat(resp_.getRecord()[irow].getTime().getEnd());
	            default: throw new IllegalArgumentException();
	        }
       }else{
        	return null;
       }
    }
}

