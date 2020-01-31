package com.apitech.test;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class BaseClassURL {

	@BeforeClass
	public void setup() {

		RestAssured.baseURI = "https://showoff-rails-react-production.herokuapp.com/api/v1/";

	}
}
