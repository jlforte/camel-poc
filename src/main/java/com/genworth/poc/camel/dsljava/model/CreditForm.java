package com.genworth.poc.camel.dsljava.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 501296262
 * Class representation of a credit score form sent by IIMS.
 */
@XmlRootElement (name="creditform")
public class CreditForm implements Serializable {
	private static final long serialVersionUID = 6429279882552580478L;
	String firstName;
	String lastName;
	String city;
	String scoreType;
	int id;
	
	public CreditForm() {
		
	}
	
	public CreditForm(String firstName, String lastName, String city, String scoreType) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.scoreType = scoreType;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getScoreType() {
		return scoreType;
	}
	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public int getId() {
		return id;
	}
	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}
}