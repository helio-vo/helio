package ch.i4ds.helio.workflows;

import java.io.StringReader;
import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.apache.xerces.parsers.DOMParser;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.StringWriter;

/**
 * Beanshell scripts from Anja LeBlanc's initial workflow. This code was mostly copied 1:1
 * from the Taverna version of the initial workflow. 
 * 
 * @author Simon Felix (de@iru.ch)
 */
public class InitialWorkflowHelpers
{
  private static void write(StringWriter out,Node node, String indent)
  {
    // The output depends on the type of the node
    switch(node.getNodeType()) {
    case Node.DOCUMENT_NODE: {       // If its a Document node
        Document doc = (Document)node;
        out.write(indent + "<?xml version='1.0'?>\n");  // Output header
        Node child = doc.getFirstChild();   // Get the first node
        while(child != null) {              // Loop 'till no more nodes
            write(out,child, indent);           // Output node
            child = child.getNextSibling(); // Get next node
        }
        break;
    } 
    case Node.DOCUMENT_TYPE_NODE: {  // It is a <!DOCTYPE> tag
        DocumentType doctype = (DocumentType) node;
        // Note that the DOM Level 1 does not give us information about
        // the the public or system ids of the doctype, so we can't output
        // a complete <!DOCTYPE> tag here.  We can do better with Level 2.
        out.write("<!DOCTYPE " + doctype.getName() + ">\n");
        break;
    }
    case Node.ELEMENT_NODE: {        // Most nodes are Elements
        Element elt = (Element) node;
        out.write(indent + "<" + elt.getTagName());   // Begin start tag
        NamedNodeMap attrs = elt.getAttributes();     // Get attributes
        for(int i = 0; i < attrs.getLength(); i++) {  // Loop through them
            Node a = attrs.item(i);
            out.write(" " + a.getNodeName() + "='" +  // Print attr. name
                      fixup(a.getNodeValue()) + "'"); // Print attr. value
        }
        out.write(">\n");                             // Finish start tag

        String newindent = indent + "    ";           // Increase indent
        Node child = elt.getFirstChild();             // Get child
        while(child != null) {                        // Loop 
            write(out,child, newindent);                  // Output child
            child = child.getNextSibling();           // Get next child
        }

        out.write(indent + "</" +                   // Output end tag
                    elt.getTagName() + ">\n");
        break;
    }
    case Node.TEXT_NODE: {                   // Plain text node
        Text textNode = (Text)node;
        String text = textNode.getData().trim();   // Strip off space
        if ((text != null) && text.length() > 0)   // If non-empty
            out.write(indent + fixup(text)+"\n");     // print text
        break;
    }
    case Node.PROCESSING_INSTRUCTION_NODE: {  // Handle PI nodes
        ProcessingInstruction pi = (ProcessingInstruction)node;
        out.write(indent + "<?" + pi.getTarget() +
                           " " + pi.getData() + "?>\n");
        break;
    }
    case Node.ENTITY_REFERENCE_NODE: {        // Handle entities
        out.write(indent + "&" + node.getNodeName() + ";\n");
        break;
    }
    case Node.CDATA_SECTION_NODE: {           // Output CDATA sections
        CDATASection cdata = (CDATASection)node;
        // Careful! Don't put a CDATA section in the program itself!
        out.write(indent + "<" + "![CDATA[" + cdata.getData() +
                    "]]" + ">\n");
        break;
    }
    case Node.COMMENT_NODE: {                 // Comments
        Comment c = (Comment)node;
        out.write(indent + "<!--" + c.getData() + "-->\n");
        break;
    }
    default:   // Hopefully, this won't happen too much!
        System.err.println("Ignoring node: " + node.getClass().getName());
        break;
    }
  }

  // This method replaces reserved characters with entities.
  private static String fixup(String s)
  {
          StringBuffer sb = new StringBuffer();
          int len = s.length();
          for(int i = 0; i < len; i++) {
              char c = s.charAt(i);
              switch(c) {
              default: sb.append(c); break;
              case '<': sb.append("&lt;"); break;
              case '>': sb.append("&gt;"); break;
              case '&': sb.append("&amp;"); break;
              case '"': sb.append("&quot;"); break;
              case '\'': sb.append("&apos;"); break;
              }
          }
          return sb.toString();
      }

  private static void addTableHeaders(Document docVO,List<String> instrument, String prefix) throws Exception
  {
    NodeList nodes=null;
    StringReader reader;
    InputSource source;
    for(int i = 0; i < instrument.size(); i++) {
      reader = new StringReader(instrument.get(i));
      source = new InputSource(reader);
      DOMParser parser=new DOMParser();
      parser.parse(source);
      Document doc=parser.getDocument();
      nodes = doc.getElementsByTagName("return");
      if(nodes!= null && nodes.getLength()>=1)
        break;
    }

    NodeList nodesVO = docVO.getElementsByTagName("TABLE");
    if(nodesVO.getLength() >= 1){
      Node voTableNode = nodesVO.item(0);
      if(nodes != null && nodes.getLength()>= 1){
        Node observation = nodes.item(0);
        NodeList obsChilds = observation.getChildNodes();
        for(int i=0; i< obsChilds.getLength(); i++){
          Element voFieldNode = docVO.createElement("FIELD");
          voFieldNode.setAttribute("name",prefix.concat(obsChilds.item(i).getLocalName()));
          voFieldNode.setAttribute("datatype","char");
          voFieldNode.setAttribute("arraysize","3400");
          voTableNode.insertBefore(voFieldNode, voTableNode.getLastChild());
        }
      }
    }
  }

  private static void addTableHeaderValue(Document docVO,String value)
  {
    NodeList nodesVO = docVO.getElementsByTagName("TABLE");
    if(nodesVO.getLength() >= 1){
      Node voTableNode = nodesVO.item(0);
      Element voFieldNode = docVO.createElement("FIELD");
      voFieldNode.setAttribute("name", value);
      voFieldNode.setAttribute("datatype","char");
      voFieldNode.setAttribute("arraysize","3400");
      voTableNode.insertBefore(voFieldNode, voTableNode.getLastChild());
    }
  }

  private static void writeExtraFields(Document docVO, Node nodeVO, Node nodeHessi)
  {
    NodeList listHessi = nodeHessi.getChildNodes();
    for(int i=0; i< listHessi.getLength(); i++)
    {
      Node newNode = nodeVO.getFirstChild().cloneNode(false);
      Text voTextNode = docVO.createTextNode(listHessi.item(i).getFirstChild().getNodeValue());
      newNode.appendChild(voTextNode);
      nodeVO.appendChild(newNode);
    }
  } 

  private static void writeSolarMonitor(List<String> solar_monitor_data,Document docVO,Node nodeVO, int pos) throws Exception
  {
    NodeList nodes=null;
    StringReader reader;
    InputSource source;
    Document docSM;
    DOMParser parserSM = new DOMParser();
    reader = new StringReader(solar_monitor_data.get(pos));
    source = new InputSource(reader);
    parserSM.parse(source);
    docSM=parserSM.getDocument();
    nodes = docSM.getElementsByTagName("urlPreview");
    if(nodes!= null && nodes.getLength()>=1)
    {
      NodeList obsChilds = nodes.item(0).getChildNodes();
      Node newNode = nodeVO.getFirstChild().cloneNode(false);
      Text voTextNode = docVO.createTextNode(obsChilds.item(0).getNodeValue());
      newNode.appendChild(voTextNode);
      nodeVO.appendChild(newNode);
    }
    else
    {
      Node newNode = nodeVO.getFirstChild().cloneNode(false);
      Text voTextNode = docVO.createTextNode("");
      newNode.appendChild(voTextNode);
      nodeVO.appendChild(newNode);
    }
    nodes = docSM.getElementsByTagName("urlPreviewThumb");
    
    if(nodes!= null && nodes.getLength()>=1)
    {
      NodeList obsChilds = nodes.item(0).getChildNodes();
      Node newNode = nodeVO.getFirstChild().cloneNode(false);
      Text voTextNode = docVO.createTextNode(obsChilds.item(0).getNodeValue());
      newNode.appendChild(voTextNode);
      nodeVO.appendChild(newNode);
    }
    else
    {
      Node newNode = nodeVO.getFirstChild().cloneNode(false);
      Text voTextNode = docVO.createTextNode("");
      newNode.appendChild(voTextNode);
      nodeVO.appendChild(newNode);
    }
    reader.close();
  }

  private static String findOverlaps(List<String> solar_monitor_data,Document docVO,List<String> instrument_data_hessi,List<String> instrument_data_phoenix,List<Integer> position) throws Exception
  {
    DOMParser parser = new DOMParser();
    DOMParser parserPhoenix = new DOMParser();
    Document doc;
    Document docPhoenix;
    StringReader reader;
    StringReader readerPhoenix;
    InputSource source;
    InputSource sourcePhoenix;
    String debug = new String("");
    NodeList dataListVO = docVO.getElementsByTagName("TR");
    if(dataListVO == null) {
      debug="dataListVO = null";
      return debug;
    }
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    if(instrument_data_hessi.size() != instrument_data_phoenix.size()) {
      throw new Exception("data list lengths different -> not from same periodes");
    }

    for(int i = instrument_data_hessi.size()-1; i>=0; i--) {
      reader = new StringReader(instrument_data_hessi.get(i));
      readerPhoenix = new StringReader(instrument_data_phoenix.get(i));
      source = new InputSource(reader);
      sourcePhoenix = new InputSource(readerPhoenix);
      parser.parse(source);
      parserPhoenix.parse(sourcePhoenix);
      doc=parser.getDocument();
      docPhoenix=parserPhoenix.getDocument();
      NodeList dataListHessi = doc.getElementsByTagName("measurementStart");
      NodeList dataListHessi2 = doc.getElementsByTagName("measurementEnd");

      NodeList dataListPhoenix = docPhoenix.getElementsByTagName("measurementStart");
      int pos = position.get(i); 

      Node voDataNode = dataListVO.item(pos);
      if(voDataNode == null)
      {
        reader.close();
        readerPhoenix.close();
        return debug;
      }
      Node parent = voDataNode.getParentNode();
      if(dataListHessi!=null && dataListPhoenix!=null)
        for(int j = 0; j < dataListHessi.getLength(); j++)
        {
          Date startHessi = date.parse(dataListHessi.item(j).getFirstChild().getNodeValue());
          Date endHessi = date.parse(dataListHessi2.item(j).getFirstChild().getNodeValue());
          
          for(int k = 0; k < dataListPhoenix.getLength(); k++) {
            Date startPhoenix = date.parse(dataListPhoenix.item(k).getFirstChild().getNodeValue());
            if(startHessi.getTime()<= startPhoenix.getTime() && endHessi.getTime()>=startPhoenix.getTime()){
              Node voDataNodeCurrent = voDataNode.cloneNode(true);
              writeExtraFields(docVO, voDataNodeCurrent, doc.getElementsByTagName("return").item(j));
              writeExtraFields(docVO, voDataNodeCurrent, docPhoenix.getElementsByTagName("return").item(k));
              writeSolarMonitor(solar_monitor_data, docVO, voDataNodeCurrent, pos);
              parent.insertBefore(voDataNodeCurrent, voDataNode);
            }
          }
        }
      
      parent.removeChild(voDataNode);
      reader.close();
      readerPhoenix.close();
    }
    return debug;
  }


  public static String combineData(List<String> instrument_data_hessi,List<String> instrument_data_phoenix,List<Integer> position,List<String> solar_monitor_data,String voTable) throws Exception
  {
    StringWriter out= new StringWriter(); 
    StringReader reader2 = new StringReader(voTable);
    InputSource source2 = new InputSource(reader2);
    String VOTable_out = new String("");

    DOMParser parser2=new DOMParser();
    parser2.parse(source2);
    Document docVO = parser2.getDocument();
    addTableHeaders(docVO,instrument_data_hessi, "hessi_");
    addTableHeaders(docVO,instrument_data_phoenix, "phoenix2_");
    addTableHeaderValue(docVO,"urlPreview");
    addTableHeaderValue(docVO,"urlPreviewThumb");
    VOTable_out = VOTable_out.concat(findOverlaps(solar_monitor_data,docVO,instrument_data_hessi,instrument_data_phoenix,position));
    write(out,docVO.getDocumentElement(),"");
    VOTable_out = VOTable_out.concat(out.toString());
    reader2.close();
    
    return VOTable_out;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public static void datesSM(String voTable,List<String> startDate,List<String> endDate) throws Exception
  {
    StringReader reader2 = new StringReader(voTable);
    InputSource source2 = new InputSource(reader2);
    DOMParser parser2 = new DOMParser();
    Document docVO;

    int posPeak=-1;
    parser2.parse(source2);
    docVO = parser2.getDocument();
    NodeList nodesVO = docVO.getElementsByTagName("FIELD");
    for(int i=0; i< nodesVO.getLength(); i++)
    {
       Element voFieldNode = (Element)nodesVO.item(i);
       String name = voFieldNode.getAttribute("name");
       if(name.trim().equals("time_peak"))
          posPeak = i;
    }
    
    if(posPeak >= 0)
    {
      NodeList dataListVO = docVO.getElementsByTagName("TR");
      for(int i = 0; i < dataListVO.getLength(); i++)
      {
        NodeList voDataChilds = dataListVO.item(i).getChildNodes();
        String date=voDataChilds.item(posPeak).getFirstChild().getNodeValue().trim();
        startDate.add(date.replaceAll("..:..:..", "00:00:00"));
        endDate.add(date.replaceAll("..:..:..", "23:59:59"));
      }
    }
    reader2.close();
  }

  public static void getAllEventDates(String voTable,List<String> startDates,List<String> endDates,List<Integer> positions) throws Exception
  {
    StringReader reader2 = new StringReader(voTable);
    InputSource source2 = new InputSource(reader2);
    Document docVO;
    int pos_start_vo=0;
    int pos_end_vo=0;
    int pos_startA_vo=0;
    int pos_endA_vo=0;

    DOMParser parser = new DOMParser();
    parser.parse(source2);
    docVO = parser.getDocument();

    //getPositions
    NodeList nodesVO = docVO.getElementsByTagName("FIELD");
    for(int i=0; i< nodesVO.getLength(); i++){
       Element voFieldNode = (Element)nodesVO.item(i);
       String name = voFieldNode.getAttribute("name");
       if(name.trim().equals("ntime_start")){
          pos_start_vo = i;
       } else if(name.trim().equals("ntime_end")){
          pos_end_vo = i;
       } else if(name.trim().equals("time_start")){
          pos_startA_vo = i;
       } else if(name.trim().equals("time_end")){
          pos_endA_vo = i;
       }
    }

    //findDates
    NodeList dataListVO = docVO.getElementsByTagName("TR");
    for(int i = 0; i < dataListVO.getLength(); i++)
    {
       NodeList voDataChilds = dataListVO.item(i).getChildNodes();
       if(voDataChilds.item(pos_start_vo).getFirstChild()!= null)
         startDates.add(voDataChilds.item(pos_start_vo).getFirstChild().getNodeValue());
       else
         startDates.add(voDataChilds.item(pos_startA_vo).getFirstChild().getNodeValue());
       
       if(voDataChilds.item(pos_end_vo).getFirstChild()!= null)
         endDates.add(voDataChilds.item(pos_end_vo).getFirstChild().getNodeValue());
       else
         endDates.add(voDataChilds.item(pos_endA_vo).getFirstChild().getNodeValue());
       
       positions.add(i);
    }
    
    reader2.close();
    
    if(endDates.size()==0)
      endDates.add("2008-00-00 00:00:01");
    
    if(startDates.size()==0)
      startDates.add("2008-00-00 00:00:00");
    
    if(positions.size()==0)
      positions.add(0);
  }
}
