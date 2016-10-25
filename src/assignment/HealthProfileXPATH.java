package assignment;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HealthProfileXPATH {
    private Document doc;
    private XPath xpath;
    

    public void loadXML(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        doc = builder.parse(xmlFile);

        //creating xpath object
        getXPathObj();
    }

    public XPath getXPathObj() {
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        return xpath;
    }
    
    //query using XPath inside the people.xml file and return the weight of the person with a given id
    public double getWeight(Long id) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/healthprofile/weight/text()");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        if(node != null){
            return Double.valueOf(node.getTextContent());
        }else{
        	System.out.println("Person with id = "+id+" not found!");
        }
        return -1.0;
    }

    //query using XPath inside the people.xml file and return the height of the person with a given id
    public double getHeight(Long id) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/healthprofile/height/text()");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        if(node != null){
            return Double.valueOf(node.getTextContent());
        }else{
        	System.out.println("Person with id = "+id+" not found!");
        }
        return -1.0;
    }    

    //query people.xml using XPath and print all the "Person"s inside the file
    public void printPeople() throws XPathExpressionException{
    	 XPathExpression expr = xpath.compile("/people/person");
    	 NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    	 System.out.println("Printing people!");
    	 for(int i = 0; i<nodes.getLength() ; i++){
    		 System.out.println("Person #"+ (i+1));
    		 System.out.println(nodes.item(i).getTextContent());
    	 }
    }

    //query people.xml using XPath and print all the HealthProfile information of the person with id
    public void printHealthProfile(Long id) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("/people/person[@id='" + id + "']/healthprofile");
        Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
        if(node != null){
        	System.out.println(node.getTextContent());
        }else{
        	System.out.println("Person with id = "+id+" not found!");
        }
    }

    //query people.xml using XPath and print all the "Person"s information having weight and condition
    public void printPeoplebyWeight(String weight, String condition) throws XPathExpressionException {
    	
    	//whitelisting condition characters, only perform query if >,< or =
    	if(condition.equals(">") || condition.equals("<") || condition.equals("=")){
	        XPathExpression expr = xpath.compile("//person/healthprofile[weight " + condition + "'" + weight + "']/..");
	        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	        for(int i = 0; i<nodes.getLength() ; i++){
	        	System.out.println("Person #"+ (i+1));
	        	System.out.println(nodes.item(i).getTextContent());
	   	 	}
        }
    	else{
        	System.out.println(condition + " different from allowed operations(>,< or =)");
    	}
    }
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException,
    IOException, XPathExpressionException {
		try {
			
			HealthProfileXPATH test = new HealthProfileXPATH();
			test.loadXML("peopleXPATH.xml");
			
			int argSize = args.length;
			
			//Using the first parameter to decide which operation to perform
			if(argSize == 1 && args[0].equals("1")){
				test.printPeople();
			}
			else if(argSize == 2 && args[0].equals("2")){
				Long id = Long.valueOf(args[1]);
				test.printHealthProfile(id);
			}
			else if(argSize == 3 && args[0].equals("3")){
				String weight = args[1];
				String condition = args[2];
				test.printPeoplebyWeight(weight, condition);				
			}	
			else if(argSize == 2 && args[0].equals("4")){
				Long id = Long.valueOf(args[1]);
				System.out.println(test.getWeight(id));				
			}	
			else if(argSize == 2 && args[0].equals("5")){
				Long id = Long.valueOf(args[1]);
				System.out.println(test.getHeight(id));					
			}
			else{
				System.out.println("Specify correct operation!");
				System.out.println("1 - Print people information (printPeople())");
				System.out.println("2 - Print HealthProfile information by <Person id> (printHealthProfile(Long personid))");
				System.out.println("3 - Print all people given <weight> and <condition> (printPeoplebyWeight(weight,condition))");
				System.out.println("4 - Return and print the weight given <Person id> (double getWeight(Long personid))");
				System.out.println("5 - Return and print the height given <Person id> (double getHeight(Long personid))");
			}
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}