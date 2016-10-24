package assignment;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import healthprofile.generated.PeopleType;
import healthprofile.generated.PersonType;

public class HealthProfileReader {  	

	public void unMarshall(File xmlDocument) {
		try {
			//Using XJC generated classes for context
			JAXBContext jaxbContext = JAXBContext.newInstance("healthprofile.generated");

			//Creating UNMarshaller based on the people.xsd schema provided
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File(
					"people.xsd"));
			unMarshaller.setSchema(schema);
			
			//FIND OUT WHAT IT IS!!!!!!!!!!!
			CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
			unMarshaller.setEventHandler(validationEventHandler);

			@SuppressWarnings("unchecked")
			JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller
					.unmarshal(xmlDocument);
			//Get the elements that were unmarshalled from file and save them in a List structure
			PeopleType people = peopleElement.getValue();
			List<PersonType> peopleList = people.getPerson();
			
			for (int i = 0; i < peopleList.size(); i++) {
				//Getting PersonType elements one by one
				PersonType person = (PersonType) peopleList.get(i);

				//Printing all the relevant information
				System.out.println("Person with id: " + person.getId());
				System.out.println("First Name: " + person.getFirstname());
				System.out.println("Last Name: " + person.getLastname());
				System.out.println("Born on: " + person.getBirthdate());
				System.out.println("Weight: " + person.getHealthprofile().getWeight());
				System.out.println("Height: " + person.getHealthprofile().getHeight());
				System.out.println("Bmi: " + person.getHealthprofile().getBmi());
				System.out.println("Last Update: " + person.getHealthprofile().getLastupdate());
				System.out.println(" ");
				
			}
		} catch (JAXBException e) {
			System.out.println(e.toString());
		} catch (SAXException e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] argv) {
		File xmlDocument = new File("peopleJAXB.xml");
		HealthProfileReader jaxbUnmarshaller = new HealthProfileReader();
		jaxbUnmarshaller.unMarshall(xmlDocument);

	}
	
	class CustomValidationEventHandler implements ValidationEventHandler {
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() == ValidationEvent.WARNING) {
				return true;
			}
			if ((event.getSeverity() == ValidationEvent.ERROR)
					|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

				System.out.println("Validation Error:" + event.getMessage());

				ValidationEventLocator locator = event.getLocator();
				System.out.println("at line number:" + locator.getLineNumber());
				System.out.println("Unmarshalling Terminated");
				return false;
			}
			return true;
		}
	}
}
