package config;

import entity.Pet;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SetUp {
    static RequestSpecification request;
    static JSONObject requestParams;
    static Pet pet;

    public static Response preSetUp(){
        baseURI = "https://petstore.swagger.io/v2/pet";
        request = given();
        request.header("Content-Type", "application/json");
        requestParams = new JSONObject(petSetUp().getInfo());
        request.body(requestParams.toString());
        return request.post();
    }

    public static Pet petSetUp(){
        pet = new Pet();
        pet.setId(6);
        pet.setName("linaswitoch");
        pet.setPhotoUrl("California");
        return pet;
    }
}
