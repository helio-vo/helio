
package eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.queryservice.v0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.heliovo.dpas.ie.services.hqi.service.eu.helio_vo.xml.instruments.v0.Instrument;


/**
 * <p>Java class for Query complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Query">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="STARTTIME" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ENDTIME" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FROM" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="WHERE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INSTRUMENT" type="{http://helio-vo.eu/xml/Instruments/v0.1}instrument" minOccurs="0"/>
 *         &lt;element name="MAXRECORDS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="STARTINDEX" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="JOIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Query", propOrder = {
    "starttime",
    "endtime",
    "from",
    "where",
    "instrument",
    "maxrecords",
    "startindex",
    "join"
})
public class Query {

    @XmlElement(name = "STARTTIME")
    protected List<String> starttime;
    @XmlElement(name = "ENDTIME")
    protected List<String> endtime;
    @XmlElement(name = "FROM", required = true)
    protected List<String> from;
    @XmlElement(name = "WHERE")
    protected String where;
    @XmlElement(name = "INSTRUMENT")
    protected Instrument instrument;
    @XmlElement(name = "MAXRECORDS")
    protected Integer maxrecords;
    @XmlElement(name = "STARTINDEX")
    protected Integer startindex;
    @XmlElement(name = "JOIN")
    protected String join;

    /**
     * Gets the value of the starttime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the starttime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSTARTTIME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSTARTTIME() {
        if (starttime == null) {
            starttime = new ArrayList<String>();
        }
        return this.starttime;
    }

    /**
     * Gets the value of the endtime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the endtime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getENDTIME().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getENDTIME() {
        if (endtime == null) {
            endtime = new ArrayList<String>();
        }
        return this.endtime;
    }

    /**
     * Gets the value of the from property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the from property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFROM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFROM() {
        if (from == null) {
            from = new ArrayList<String>();
        }
        return this.from;
    }

    /**
     * Gets the value of the where property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWHERE() {
        return where;
    }

    /**
     * Sets the value of the where property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWHERE(String value) {
        this.where = value;
    }

    /**
     * Gets the value of the instrument property.
     * 
     * @return
     *     possible object is
     *     {@link Instrument }
     *     
     */
    public Instrument getINSTRUMENT() {
        return instrument;
    }

    /**
     * Sets the value of the instrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link Instrument }
     *     
     */
    public void setINSTRUMENT(Instrument value) {
        this.instrument = value;
    }

    /**
     * Gets the value of the maxrecords property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMAXRECORDS() {
        return maxrecords;
    }

    /**
     * Sets the value of the maxrecords property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMAXRECORDS(Integer value) {
        this.maxrecords = value;
    }

    /**
     * Gets the value of the startindex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSTARTINDEX() {
        return startindex;
    }

    /**
     * Sets the value of the startindex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSTARTINDEX(Integer value) {
        this.startindex = value;
    }

    /**
     * Gets the value of the join property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJOIN() {
        return join;
    }

    /**
     * Sets the value of the join property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJOIN(String value) {
        this.join = value;
    }

    public void setStarttime(List<String> starttime) {
		this.starttime = starttime;
	}

    public void setEndtime(List<String> endtime) {
		this.endtime = endtime;
	}

    public void setFrom(List<String> from) {
		this.from = from;
	}

}
