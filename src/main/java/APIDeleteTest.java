import config.SetUp;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class APIDeleteTest {
    RequestSpecification request;
    Response response;


    @BeforeClass
    public void setUp() {
        SetUp.preSetUp();
        request = given();
        response = request.delete("/6");

    }

    @Test
    public void deletePet() {
        response.then().statusCode(200);
        request.get("6").then().statusCode(404);
    }
}
