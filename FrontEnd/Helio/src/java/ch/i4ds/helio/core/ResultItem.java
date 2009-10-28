
package ch.i4ds.helio.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for resultItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resultItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="measurementStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="measurementEnd" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="urlFITS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlCorrectedRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlFrontRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlPartRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlRearRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlPhaseFITSGZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="urlIntensityFITSGZ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultItem", propOrder = {
    "measurementStart",
    "measurementEnd",
    "urlFITS",
    "urlCorrectedRate",
    "urlFrontRate",
    "urlPartRate",
    "urlRate",
    "urlRearRate",
    "urlPhaseFITSGZ",
    "urlIntensityFITSGZ"
})
public class ResultItem {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar measurementStart;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar measurementEnd;
    protected String urlFITS;
    protected String urlCorrectedRate;
    protected String urlFrontRate;
    protected String urlPartRate;
    protected String urlRate;
    protected String urlRearRate;
    protected String urlPhaseFITSGZ;
    protected String urlIntensityFITSGZ;

    /**
     * Gets the value of the measurementStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMeasurementStart() {
        return measurementStart;
    }

    /**
     * Sets the value of the measurementStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMeasurementStart(XMLGregorianCalendar value) {
        this.measurementStart = value;
    }

    /**
     * Gets the value of the measurementEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMeasurementEnd() {
        return measurementEnd;
    }

    /**
     * Sets the value of the measurementEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMeasurementEnd(XMLGregorianCalendar value) {
        this.measurementEnd = value;
    }

    /**
     * Gets the value of the urlFITS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlFITS() {
        return urlFITS;
    }

    /**
     * Sets the value of the urlFITS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlFITS(String value) {
        this.urlFITS = value;
    }

    /**
     * Gets the value of the urlCorrectedRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlCorrectedRate() {
        return urlCorrectedRate;
    }

    /**
     * Sets the value of the urlCorrectedRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlCorrectedRate(String value) {
        this.urlCorrectedRate = value;
    }

    /**
     * Gets the value of the urlFrontRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlFrontRate() {
        return urlFrontRate;
    }

    /**
     * Sets the value of the urlFrontRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlFrontRate(String value) {
        this.urlFrontRate = value;
    }

    /**
     * Gets the value of the urlPartRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPartRate() {
        return urlPartRate;
    }

    /**
     * Sets the value of the urlPartRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPartRate(String value) {
        this.urlPartRate = value;
    }

    /**
     * Gets the value of the urlRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlRate() {
        return urlRate;
    }

    /**
     * Sets the value of the urlRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlRate(String value) {
        this.urlRate = value;
    }

    /**
     * Gets the value of the urlRearRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlRearRate() {
        return urlRearRate;
    }

    /**
     * Sets the value of the urlRearRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlRearRate(String value) {
        this.urlRearRate = value;
    }

    /**
     * Gets the value of the urlPhaseFITSGZ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlPhaseFITSGZ() {
        return urlPhaseFITSGZ;
    }

    /**
     * Sets the value of the urlPhaseFITSGZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlPhaseFITSGZ(String value) {
        this.urlPhaseFITSGZ = value;
    }

    /**
     * Gets the value of the urlIntensityFITSGZ property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlIntensityFITSGZ() {
        return urlIntensityFITSGZ;
    }

    /**
     * Sets the value of the urlIntensityFITSGZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlIntensityFITSGZ(String value) {
        this.urlIntensityFITSGZ = value;
    }

}
