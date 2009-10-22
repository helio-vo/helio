package ch.i4ds.helio.dpas.output;

import ch.i4ds.helio.dpas.ResultItem;

public class CSVOutput
{
  private String convertResult(ResultItem _result)
  {
    return "";
  }
  
  public Object convert(ResultItem[] _results)
  {
    String result="";
    for(ResultItem r:_results)
      result+=convertResult(r)+"/n";
    
    return result;
  }
}
