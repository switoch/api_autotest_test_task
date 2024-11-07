import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class APIPostTest {
    RequestSpecification request;
    Response response;
    JSONObject requestParams;

    @BeforeClass
    public void setUp() {
        baseURI = "https://petstore.swagger.io/v2/pet";
        request = given();
        request.header("Content-Type", "application/json");
        requestParams = new JSONObject();
    }


    @Test
    public void testRequiredFields() {
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("California");
        requestParams.put("id", 6);
        requestParams.put("photoUrls", new JSONArray(photoUrls));
        request.body(requestParams.toString());
        response = request.post();
        int result =
                response
                        .jsonPath()
                        .get("id");
        List<String> photoUrlsResult = response.jsonPath().getList("photoUrls");


        response.then().statusCode(200);
        Assert.assertEquals(result, 6);
        Assert.assertNotNull(photoUrlsResult);
        Assert.assertEquals(photoUrlsResult.size(), photoUrls.size());
        Assert.assertEquals(photoUrlsResult.get(0), photoUrls.get(0));
    }

    @Test
    public void testBodyEmpty() {
        response = request.post();
        response.then().statusCode(405);
    }


    @Test
    public void testGetResponseTime() {
        requestParams.put("id", "132");
        requestParams.put("name", "Fluffy");
        request.body(requestParams);
        response = request.post();
        response
                .then()
                .time(lessThan(500L), TimeUnit.MILLISECONDS);
    }

}
