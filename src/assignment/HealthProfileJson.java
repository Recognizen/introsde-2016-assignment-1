package assignment;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import healthprofile.generated.HealthProfileType;
import healthprofile.generated.PeopleType;
import healthprofile.generated.PersonType;

public class HealthProfileJson {  	

	public static PeopleType people;
	
	//generating 3 random people using the XJC generated classes
	public static void generatePeople() throws DatatypeConfigurationException {
		try{
			//Create and populate the structure that will be fed to the marshaller
			healthprofile.generated.ObjectFactory factory = new healthprofile.generated.ObjectFactory();
			people = factory.createPeopleType();
			
			//Generate 3 random "PersonType"s
			for(int i = 1; i<4; i++){
				
				PersonType person = factory.createPersonType();
				//Adding general information for the person
				person.setId(BigInteger.valueOf(i));
				person.setFirstName("FirstName"+i);
				person.setLastName("LastName"+i);
				
				//BirthDate is set to current time in the proper format
		        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		        XMLGregorianCalendar now = 
		            datatypeFactory.newXMLGregorianCalendar(new GregorianCalendar());
		        person.setBirthDate(now.toString());
				
				//HealthProfile instantiation
				HealthProfileType hp = factory.createHealthProfileType();
				hp.setHeight(Double.valueOf(2));
				hp.setWeight(Double.valueOf(20*i));
				double bmi = hp.getWeight()/(hp.getHeight()*hp.getHeight());
				hp.setBmi(bmi);
				hp.setLastupdate(now.toString()+i);
				
				//Adding the HealthProfile to the person
				person.setHealthProfile(hp);
				people.getPerson().add(person);
			}
		} catch (DatatypeConfigurationException e){
			System.out.println(e.toString());
		}	
	}
	
	public static void main(String[] args) throws Exception {
		
		//Create the same people as for HealthProfileWriter example
		generatePeople();
		
		// Jackson Object Mapper 
		ObjectMapper mapper = new ObjectMapper();
		
		// Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        
		// configure as necessary
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(new File("people.json"), people);
    }
}
