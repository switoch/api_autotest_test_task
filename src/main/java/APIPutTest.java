import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class APIPutTest {
    Response response;
    RequestSpecification request;
    JSONObject postParams;
    JSONObject putParams;

    @BeforeClass
    public void setUp() {
        baseURI = "https://petstore.swagger.io/v2/pet/";

        request = given();
        request.header("Content-Type", "application/json");

        //post to create an entity
        postParams = new JSONObject();
        postParams.put("id", "6");
        postParams.put("name","linaswitoch");
        request.body(postParams.toString());
        request.post();

        //put to change it
        putParams = new JSONObject();
        putParams.put("id", "6");
        putParams.put("name","changed");
        request.body(putParams.toString());
        response = request.put();

    }

    @Test
    public void testGetRequest() {
        String result = response.jsonPath().get("name");
        Assert.assertEquals(result, "changed");
        response
                .then()
                .statusCode(200);
    }
}
