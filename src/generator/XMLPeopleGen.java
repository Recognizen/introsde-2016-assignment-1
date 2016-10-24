package generator;

import java.io.File;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import generator.model.HealthProfile;
import generator.model.Person;

import generator.dao.PeopleStore;

public class XMLPeopleGen {	
	
	//constant collections of names
	private final static String[] firstNames = {
		  "John", "Michael", "Jane", "James", "Susan", "Peter", "Alex", "Sarah", "Hanna", "Jacob",
		  "Kelly", "Karen", "Justin", "Rob", "Michelle", "Emma", "Olivia", "Lucy" , "Eva", "Tom"
	};
	private final static String[] lastNames = {
		  "Smith", "Abbot", "Bennet", "Jones", "Williams", "Johnson", "Garcia", "Russel", "Little", "Huang",
		  "Owens", "Rodriguez", "Black", "White", "Jacobs", "Sunny", "Chen", "Jenkings", "Starch","Wolfe" 
	  };
	
	public static PeopleStore people = new PeopleStore();
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Specify number of people to generate (only one number)!");
		} else {
			//Add George to the store as to keep him
			people.getData().add(new Person(Long.valueOf(1), 
						"George R. R.",
						"Martin", 
						"1984-09-20T18:00:00.000+02:00",
						new HealthProfile(90, 1.70, "2014-09-20T18:00:00.000+02:00")));
			
			//Generate n-1 more (getting n from parameter)
			generatePeople(Integer.valueOf(args[0]));
			JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
		    Marshaller m = jc.createMarshaller();
		    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    // marshalling into a file
		    m.marshal(people,new File("peopleXPATH.xml")); 
		    //m.marshal(people, System.out);			  // marshalling into the system default output*/
		}
	}
	
	//Generates n-1 random people
	public static void generatePeople(int n){		
		Random random=new Random();
		
		//id starts at 2 so to account for George R. R. Martin having id 1
		for(int i = 2; i <=n ; i++){
			
			//sample an Array of possible firstNames randomly
			int firstName = random.nextInt(Integer.valueOf(firstNames.length));

			//sample an Array of possible lastNames randomly
			int lastName = random.nextInt(Integer.valueOf(lastNames.length));
			
			people.getData().add(new Person(Long.valueOf(i), 
					firstNames[firstName],
					lastNames[lastName], 
					new HealthProfile((random.nextDouble()*50+50),
									  (random.nextDouble()*2+1))));
		}		
	}
}
