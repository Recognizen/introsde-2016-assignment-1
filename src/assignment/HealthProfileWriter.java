package assignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

import healthprofile.generated.HealthProfileType;
import healthprofile.generated.PeopleType;
import healthprofile.generated.PersonType;

public class HealthProfileWriter {  
	
	public void generateXMLDocument(File xmlDocument) throws DatatypeConfigurationException {
		try {
			//set the context to use the generated XJC classes
			JAXBContext jaxbContext = JAXBContext.newInstance("healthprofile.generated");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
			
			//Create and populate the structure that will be fed to the marshaller
			healthprofile.generated.ObjectFactory factory = new healthprofile.generated.ObjectFactory();
			PeopleType people = factory.createPeopleType();
			
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
			
			//output the people list to file
			JAXBElement<PeopleType> peopleElement = factory.createPeople(people);
			marshaller.marshal(peopleElement,
					new FileOutputStream(xmlDocument));
			//output the people list console
			marshaller.marshal(peopleElement, System.out);
			
		} catch (IOException e) {
			System.out.println(e.toString());

		} catch (JAXBException e) {
			System.out.println(e.toString());

		} catch (DatatypeConfigurationException e){
			System.out.println(e.toString());
		}
	}

	public static void main(String[] argv) throws DatatypeConfigurationException {
		String xmlDocument = "people.xml";
		HealthProfileWriter jaxbMarshaller = new HealthProfileWriter();
		jaxbMarshaller.generateXMLDocument(new File(xmlDocument));
	}
}
