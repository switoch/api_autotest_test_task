import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertNotNull;

public class APIGetTest {
    Response response;
    RequestSpecification request;
    JSONObject requestParams;

    @BeforeClass
    public void setUp() {
        baseURI = "https://petstore.swagger.io/v2/pet/";

        request = given();
        requestParams = new JSONObject();
        requestParams.put("id", "6");
        requestParams.put("name","linaswitoch");
        request.body(requestParams);
        request.post();

        response = given()
                .when()
                .get("6");
    }

    @Test
    public void testGetRequest() {
        response
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetResponseTime() {
        response
                .then()
                .time(lessThan(500L), TimeUnit.MILLISECONDS);
    }

    @Test
    public void testGetIDmatchesRequestedID() {
        int result =
                response
                        .jsonPath()
                        .get("id");
        Assert.assertEquals(result, 6);

    }

    @Test
    public void testGetAllExpectedFields() {
        assertNotNull(response.jsonPath().get("id"), "Response should have an 'id' field.");
        assertNotNull(response.jsonPath().get("name"), "Response should have a 'name' field.");
        assertNotNull(response.jsonPath().get("photoUrls"), "Response should have a 'status' field.");
    }
}
