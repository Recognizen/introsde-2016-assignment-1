# introsde-2016-assignment-1

#About the Code

The code is self-containing to run the assignment. 

It first includes a people generator, XMLPeopleGen.java, which is used to create the .xml file in the requested format. It takes as parameter an integer n specifying the number of "person"s to generate. This will be set to 20 for the purposes of this assignment.
This file, peopleXPATH.xml, will then be used for the first tasks (based on lab3) and is generated using JAXB marshalling from model classes Person.java and HealthProfile.java.

* HealthProfileXPATH.java (includes all the tasks required for the XPath based tasks) 

Secondly, a people.xsd xml schema is provided. This is then used by JAXB XJC module to automatically generate healthprofile.generated classes to be used in marshalling/unmarshalling operations (based on lab4).

Those classes are then used for the remaining points in:
* HealthProfileWriter.java (marshals 3 people to peopleJAXB.xml file)
* HealthProfileReader.java (unmarshals the 3 people from the previous peopleJAXB.xml file)
* HealthProfileJson.java (outputs the same 3 people to people.json file)


#Tasks
As mentioned above the tasks can be divided into XPath tasks (based on lab3) and JAXB tasks (based on lab4).

All the XPath tasks are defined in HealthProfileXPATH.java and feature:
* double getWeight(Long id): takes as input a person id and returns their weight from an xml file (not used in the assignment)
* double getHeight(Long id): analogous to previous for height 

* void printPeople(): prints all relevant information pertaining to "person"s from inside an xml file System.out
* void printHealthProfile(Long id): takes as input a person id and prints information pertaining to the corresponding person's HealthProfile from inside the xml file to System.out
* void printPeoplebyWeight(String weight, String condition): takes as input a weight and a condition and prints all the people that satisfy that condition from inside the xml file (condition can be >,< or =) to System.out

The JAXB tasks are the following:
* HealthProfileWriter.java: instantiate and fill 3 person java objects(using the XJC generated classes), marshall them to personJAXB.xml file and print them to System.out
* HealthProfileReader.java: unmarshall the previous 3 person objects from the personJAXB.xml file (using the XJC generated classes) and print them to System.out
* HealthProfileJson.java: instantiate and fill 3 person java objects(using the XJC generated classes), output them to person.json file and print them to System.out

#Running

To run the code a build.xml ant file and ivy.xml file are provided. As such Ant is required on the system along with Ivy. 
The ant file includes targets for compiling the code, generating XJC classes, generating peopleXPATH.xml as well as running the requested tasks. (See build.xml targets for more examples)

Since dependencies are managed by ivy that is required to run the project is ant execute.evaluation.












