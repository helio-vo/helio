
package ws.clients.HEC;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.clients.HEC package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CountRowsResponse_QNAME = new QName("http://dpas.helio.i4ds.ch/", "countRowsResponse");
    private final static QName _Sql_QNAME = new QName("http://dpas.helio.i4ds.ch/", "sql");
    private final static QName _DescribeCatalogue_QNAME = new QName("http://dpas.helio.i4ds.ch/", "describeCatalogue");
    private final static QName _DescribeTable_QNAME = new QName("http://dpas.helio.i4ds.ch/", "describeTable");
    private final static QName _DescribeTableResponse_QNAME = new QName("http://dpas.helio.i4ds.ch/", "describeTableResponse");
    private final static QName _CountRows_QNAME = new QName("http://dpas.helio.i4ds.ch/", "countRows");
    private final static QName _DescribeCatalogueResponse_QNAME = new QName("http://dpas.helio.i4ds.ch/", "describeCatalogueResponse");
    private final static QName _SqlResponse_QNAME = new QName("http://dpas.helio.i4ds.ch/", "sqlResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.clients.HEC
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DescribeCatalogueResponse }
     * 
     */
    public DescribeCatalogueResponse createDescribeCatalogueResponse() {
        return new DescribeCatalogueResponse();
    }

    /**
     * Create an instance of {@link DescribeTable }
     * 
     */
    public DescribeTable createDescribeTable() {
        return new DescribeTable();
    }

    /**
     * Create an instance of {@link DescribeCatalogue }
     * 
     */
    public DescribeCatalogue createDescribeCatalogue() {
        return new DescribeCatalogue();
    }

    /**
     * Create an instance of {@link Sql }
     * 
     */
    public Sql createSql() {
        return new Sql();
    }

    /**
     * Create an instance of {@link CountRowsResponse }
     * 
     */
    public CountRowsResponse createCountRowsResponse() {
        return new CountRowsResponse();
    }

    /**
     * Create an instance of {@link DescribeTableResponse }
     * 
     */
    public DescribeTableResponse createDescribeTableResponse() {
        return new DescribeTableResponse();
    }

    /**
     * Create an instance of {@link CountRows }
     * 
     */
    public CountRows createCountRows() {
        return new CountRows();
    }

    /**
     * Create an instance of {@link SqlResponse }
     * 
     */
    public SqlResponse createSqlResponse() {
        return new SqlResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountRowsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dpas.helio.i4ds.ch/", name = "countRowsResponse")
    public JAXBElement<CountRowsResponse> createCountRowsResponse(CountRowsResponse value) {
        return new JAXBElement<CountRowsResponse>(_CountRowsResponse_QNAME, CountRowsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Sql }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dpas.helio.i4ds.ch/", name = "sql")
    public JAXBElement<Sql> createSql(Sql value) {
        return new JAXBElement<Sql>(_Sql_QNAME, Sql.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DescribeCatalogue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dpas.helio.i4ds.ch/", name = "describeCatalogue")
    public JAXBElement<DescribeCatalogue> createDescribeCatalogue(DescribeCatalogue value) {
        return new JAXBElement<DescribeCatalogue>(_DescribeCatalogue_QNAME, DescribeCatalogue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DescribeTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dpas.helio.i4ds.ch/", name = "describeTable")
    public JAXBElement<DescribeTable> createDescribeTable(DescribeTable value) {
        return new JAXBElement<DescribeTable>(_DescribeTable_QNAME, DescribeTable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DescribeTableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dpas.helio.i4ds.ch/", name = "describeTableResponse")
    public JAXBElement<DescribeTableResponse> createDescribeTableResponse(DescribeTableResponse value) {
        return new JAXBElement<DescribeTableResponse>(_DescribeTableResponse_QNAME, DescribeTableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountRows }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dpas.helio.i4ds.ch/", name = "countRows")
    public JAXBElement<CountRows> createCountRows(CountRows value) {
        return new JAXBElement<CountRows>(_CountRows_QNAME, CountRows.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DescribeCatalogueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dpas.helio.i4ds.ch/", name = "describeCatalogueResponse")
    public JAXBElement<DescribeCatalogueResponse> createDescribeCatalogueResponse(DescribeCatalogueResponse value) {
        return new JAXBElement<DescribeCatalogueResponse>(_DescribeCatalogueResponse_QNAME, DescribeCatalogueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SqlResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dpas.helio.i4ds.ch/", name = "sqlResponse")
    public JAXBElement<SqlResponse> createSqlResponse(SqlResponse value) {
        return new JAXBElement<SqlResponse>(_SqlResponse_QNAME, SqlResponse.class, null, value);
    }

}
