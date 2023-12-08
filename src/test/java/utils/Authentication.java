package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class Authentication {

    public static void main(String[] args) {
        System.out.println("generateToken() = " + generateToken());
    }

    public static String generateToken(){

        String credentials = "{\n" +
                "  \"password\": \"Mark.123\",\n" +
                "  \"rememberMe\": true,\n" +
                "  \"username\": \"mark_twain\"\n" +
                "}";

          Response response = given().
                  contentType(ContentType.JSON).
                  body(credentials).
                  when().
                  post("https://medunna.com/api/authenticate");

          return response.jsonPath().getString("id_token");

    }
}
