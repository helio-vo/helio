package ch.i4ds.helio.dpas;

import javax.swing.text.html.HTMLEditorKit;

public class HTMLParserFactory extends HTMLEditorKit
{
  private HTMLParserFactory()
  {
    
  }
  
  public static HTMLEditorKit.Parser createParser()
  {
    return new HTMLParserFactory().getParser();
  }
}
