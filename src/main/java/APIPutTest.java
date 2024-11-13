import entity.Pet;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static config.SetUp.preSetUp;
import static io.restassured.RestAssured.given;

public class APIPutTest {
    Response response;
    RequestSpecification request;
    JSONObject putParams;
    Pet pet;

    @BeforeClass
    public void setUp() {
        preSetUp();

        //put to change it
        pet = new Pet();
        pet.setId(6);
        pet.setName("changed");
        request = given();
        request.header("Content-Type", "application/json");
        putParams = new JSONObject(pet.getInfo());
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
