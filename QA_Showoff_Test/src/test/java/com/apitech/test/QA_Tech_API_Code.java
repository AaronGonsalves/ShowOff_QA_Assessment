package com.apitech.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class QA_Tech_API_Code extends BaseClassURL {

	public static String accessToken;
	public static int userID;
	String generatedString = RandomStringUtils.randomAlphabetic(8);
	String emailRandom = generatedString + "@gmail.com";

	@Test(priority = 1, enabled = true, description = "This API helps to create user")
	public void testUsersCreatePost() {

		JSONObject jsonBody = new JSONObject();
		JSONObject user = new JSONObject();

		user.put("first_name", "Aaron");
		user.put("last_name", "Gonsalves");
		user.put("password", "password");
		user.put("email", emailRandom);
		user.put("image_url", "https://static.thenounproject.com/png/961-200.png");
		
		jsonBody.put("client_id", "277ef29692f9a70d511415dc60592daf4cf2c6f6552d3e1b769924b2f2e2e6fe");
		jsonBody.put("client_secret", "d6106f26e8ff5b749a606a1fba557f44eb3dca8f48596847770beb9b643ea352");
		jsonBody.put("user", user);

		Response response = RestAssured.given().headers("Content-Type", "application/json").body(jsonBody)
				.post("users");

		accessToken = response.jsonPath().get("data.token.access_token");

		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 2, enabled = true, description = "This API updates the user created in the post method")
	public void testUsersUpdatePut() {

		JSONObject jsonBody = new JSONObject();
		JSONObject user = new JSONObject();

		user.put("first_name", "Aaron");
		user.put("last_name", "Gonsalves");
		user.put("date_of_birth", 1464083530);
		user.put("image_url", "https://static.thenounproject.com/png/961-200.png");

		jsonBody.put("user", user);

		Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.header("Content-Type", "application/json").body(jsonBody)
				.put("users/me");

		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 3, enabled = true, description = "This API shows the user which is created")
	public void testUsersShowMeGet() {

		Response response = RestAssured.given().headers("Authorization", "Bearer " + accessToken)
				.get("users/me");

		userID = response.jsonPath().get("data.user.id");
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 4, enabled = true, description = "This API shows the user ID which is created")
	public void testUsersShowIDGet() {

		Response response = RestAssured.given().headers("Authorization", "Bearer " + accessToken)
				.get("users/" + userID);

		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 5, enabled = true, description = "This API creates a visible widget")
	public void testWidgetCreateVisiblePost() {

		JSONObject jsonBody = new JSONObject();
		JSONObject user = new JSONObject();

		user.put("name", "A Visible Widget");
		user.put("description", "Widget 1");
		user.put("kind", "visible");

		jsonBody.put("widget", user);

		Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.header("Content-Type", "application/json").body(jsonBody)
				.post("widgets");

		Assert.assertEquals(response.getStatusCode(), 200);

	}
}
