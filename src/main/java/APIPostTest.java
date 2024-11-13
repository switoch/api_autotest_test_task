import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.SetUp;
import entity.Pet;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static config.SetUp.petSetUp;
import static config.SetUp.preSetUp;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.assertEquals;

public class APIPostTest {
    RequestSpecification request;
    Response response;

    @BeforeClass
    public void setUp() {
        response = preSetUp();
    }


    @Test
    public void testRequiredFields() {
        int result =
                response
                        .jsonPath()
                        .get("id");
        String name = response.jsonPath().getString("name");
        List<String> photoUrlsResult = response.jsonPath().getList("photoUrls");


        response.then().statusCode(200);
        assertEquals(result, petSetUp().getId());
        Assert.assertEquals(name, petSetUp().getName());
        Assert.assertEquals(photoUrlsResult.get(0), petSetUp().getPhotoUrl(0));
    }

    @Test
    public void testAllFields() {
        //create a new Pet object
        Pet.Tags tags = new Pet.Tags("short", 1);
        List<Pet.Tags> listOfTags = new ArrayList<>();
        listOfTags.add(tags);
        Pet.Category category = new Pet.Category(1, "cats");
        List<String> urls = new ArrayList<>();
        urls.add("photourls");
        Pet pet = new Pet("fullname", 6, "enabled", urls, listOfTags, category );

        //send a post request with full data
        response = given().header("Content-Type", "application/json").body(pet.getInfo()).post("https://petstore.swagger.io/v2/pet");

        //deserialize JSON from the body response to Java object
        String res = response.asString();
        Gson gson = new Gson();
        Pet petres = gson.fromJson(res, Pet.class);


        //Assertions
        response.then().statusCode(200);
        assertEquals(petres.getId(), pet.getId());
        Assert.assertEquals(petres.getName(), pet.getName());
        Assert.assertEquals(petres.getStatus(), pet.getStatus());
        Assert.assertEquals(petres.getTags().get(0).getTagId(), tags.getTagId());
        Assert.assertEquals(petres.getTags().get(0).getTagName(), tags.getTagName());
        Assert.assertEquals(petres.getCategory().getCategoryId(), category.getCategoryId());
        Assert.assertEquals(petres.getCategory().getCategoryName(), category.getCategoryName());
        Assert.assertEquals(petres.getPhotoUrl(0), pet.getPhotoUrl(0));
    }

    @Test
    public void testNoBody() {
        given().post("https://petstore.swagger.io/v2/pet").then().statusCode(415);
    }

    @Test
    public void testBodyEmpty() {
        given().header("Content-Type", "application/json").body("{}").post("https://petstore.swagger.io/v2/pet").then().statusCode(200);
    }


    @Test
    public void testGetResponseTime() {
        response
                .then()
                .time(lessThan(500L), TimeUnit.MILLISECONDS);
    }

}
