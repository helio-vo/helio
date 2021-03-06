package eu.heliovo.sms.ontology;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyIRIMapperImpl;

/*
 * Test the functionality of helio-sms against unchanging ontology
 */
public class SMSTest{
	private OWLOntology ont=null;
	
	@Before
    public void loadOntology() throws OWLException, IOException {
		    String dir1;
			dir1 = new File(".").getCanonicalPath().replaceAll("\\\\", "/");
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager(); 
			IRI flareOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/10/helio-flare1.owl"); 
			IRI flareOntologyFile = IRI.create(new File(dir1+"/src/test/resources/helio-flare1.owl"));  
			IRI upperOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/5/HELIO_upperOntology.owl"); 
			IRI upperOntologyFile = IRI.create(new File(dir1+"/src/test/resources/HELIO_upperOntology.owl"));  
			IRI physicisOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2009/10/HELIO_physicsOntology.owl"); 
			IRI physicsOntologyFile = IRI.create(new File(dir1+"/src/test/resources/HELIO_physicsOntology.owl"));  
			IRI coordinateSystemOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2009/10/CoordinateSystems.owl"); 
			IRI coordinateSystemOntologyFile = IRI.create(new File(dir1+"/src/test/resources/CoordinateSystems.owl"));  
			IRI organisationalOntologyIRI = IRI.create("http://www.semanticweb.org/ontologies/2010/2/HELIO_organisationalOntology.owl"); 
			IRI organisationalOntologyFile = IRI.create(new File(dir1+"/src/test/resources/HELIO_organisationalOntology.owl"));
			
			OWLOntologyIRIMapperImpl mapper = new  OWLOntologyIRIMapperImpl(); 
			mapper.addMapping(flareOntologyIRI, flareOntologyFile);
			mapper.addMapping(upperOntologyIRI, upperOntologyFile);
			mapper.addMapping(physicisOntologyIRI, physicsOntologyFile);
			mapper.addMapping(coordinateSystemOntologyIRI, coordinateSystemOntologyFile);
			mapper.addMapping(organisationalOntologyIRI, organisationalOntologyFile);
			manager.addIRIMapper(mapper);
			
			File file1= new File(dir1+"/src/test/resources/helio-flare1.owl");
			
			
			ont= manager.loadOntologyFromOntologyDocument(file1);

	}
	
	@Test
	/**
	 * Test correct input
	 */
	public void getOwlClassTest(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		expectedResult.add("ActiveRegionNumber");
		expectedResult.add("RhessiFlareNumber");
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getOwlClass("IdentificationParameter");
		
		
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test non-existen class term
	 */
	public void getOwlClassTestWrong(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getOwlClass("ABC");
		
		
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test null input
	 */
	public void getOwlClassTestWrong2(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getOwlClass(null);
		
		
		assertTrue(testListEqual(result,expectedResult));
	}
	
	private boolean testListEqual(List<String> result,
			List<String> expectedResult) {
		for(String value : expectedResult){
			if(result.contains(value)==false){
				return false;
			}
			result.remove(value);
		}
		if(result.size()!=0){
			return false;
		}
		return true;
	}

	@Test
	/**
	 * Test correct input
	 */
	public void getRelatedclasses(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		expectedResult.add("Service");
		expectedResult.add("HelioEventCatalog");
		expectedResult.add("HelioFeatureCatalog");
		expectedResult.add("InfrastructureResource");
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getRelated("Service");
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test non-existen class term
	 */
	public void getRelatedWrong(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getRelated("ABC");
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test null input
	 */
	public void getRelatedWrong2(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
	List<String> result = query.getRelated(null);
		assertTrue(testListEqual(result,expectedResult));
	}

	@Test
	/**
	 * Test correct input
	 */
	public void getEquivalents(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		expectedResult.add("CME");
		expectedResult.add("CoronalMassEjection");
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getEquivalents("CME");
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test non-existen class term
	 */
	public void getEquivalentsWrong(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getEquivalents("ABC");
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test null input
	 */
	public void getEquivalentsWrong2(){
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getEquivalents(null);
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test correct input
	 */
	public void getEventCatalogues() throws ParserException{
		ArrayList<String> expectedResult = new ArrayList<String>();
		expectedResult.add("lasco_cme_cat");
		expectedResult.add("ulysses_swoops_icme");
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getEventCatalogues("CME");
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test non-existen class term
	 */
	public void getEventCataloguesWrong() throws ParserException{
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getEventCatalogues("ABC");
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test null input
	 */
	public void getEventCataloguesWrong2() throws ParserException{
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getEventCatalogues(null);
		assertTrue(testListEqual(result,expectedResult));
	}
	
	@Test
	/**
	 * Test valid Class term but non-phenomenon
	 */
	public void getEventCataloguesWrong3() throws ParserException{
		ArrayList<String> expectedResult = new ArrayList<String>();
		QueryOntology query = new QueryOntology(ont);
		List<String> result = query.getEventCatalogues("Antenna");
		assertTrue(testListEqual(result,expectedResult));
	}
}