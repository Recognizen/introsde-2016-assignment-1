package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="healthProfile")
@XmlType(propOrder = { "weight", "height", "BMI", "lastupdate" })
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthProfile {
	private double weight; // in kg
	private double height; // in m
	private String lastupdate; //date

	public HealthProfile(double weight, double height, String lastupdate) {
		this.weight = weight;
		this.height = height;
		this.lastupdate = lastupdate;
	}

	public HealthProfile(double weight, double height) {
		this.weight = weight;
		this.height = height;
		this.lastupdate = getRandomDate();
	}

	public HealthProfile() {
		this.weight = 85.5;
		this.height = 1.72;
		this.lastupdate = getRandomDate();
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
		this.setLastUpdate(new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date()));
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
		this.setLastUpdate(new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date()));
	}
	public String toString() {
		return "Height="+height+", Weight="+weight;
	}

	@XmlElement(name="bmi")
	public double getBMI() {
		return this.weight/(Math.pow(this.height, 2));
	}
	
	public void setLastUpdate(String lastupdate){
		this.lastupdate = lastupdate;
	} 
	
	public String getLastUpdate(){
		return lastupdate;
	}
	
	private String getRandomDate() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR); 		// 1. get the current year
		int year = (int) Math.round(Math.random()*(currentYear-2014)+2014); // 2. generate a random year 
																			//    between 2014 and currentYear
		int month = (int) Math.round(Math.floor(Math.random()*11)+1);		// 3. select a random month of the year
		// 4. select a random day in the selected month
		// 4.1 prepare a months array to store how many days in each month
		int[] months = new int[]{31,28,30,30,31,30,31,31,30,31,30,31};
		// 4.2 if it is a leap year, feb (months[1]) should be 29
		if ((currentYear % 4 == 0) && ((currentYear % 100 != 0) || (currentYear % 400 == 0))) {
			months[1] = 29;
		}
		long day = Math.round(Math.floor(Math.random()*(months[month-1]-1)+1));
		return ""+year+"-"+month+"-"+day;
	}
}
