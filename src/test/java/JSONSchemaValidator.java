import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class JSONSchemaValidator {
    @Test
    public void getListUsersTest(){
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("jsonschema.json"))
                .statusCode(200)
                .body("data[2].'email'", equalTo("tobias.funke@reqres.in"))
                .log().all();
    }
}
