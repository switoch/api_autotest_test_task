import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class APIDeleteTest {
    RequestSpecification request;
    Response response;
    JSONObject requestParams;


    @BeforeClass
    public void setUp() {
        baseURI = "https://petstore.swagger.io/v2/pet";
        request = given();
        requestParams = new JSONObject();
        requestParams.put("id", "6");
        request.body(requestParams);
        request.post();
        response = request.delete("/6");

    }

    @Test
    public void deletePet() {
        response.then().statusCode(200);
        request.get("6").then().statusCode(404);
    }
}
