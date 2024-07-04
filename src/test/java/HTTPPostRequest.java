import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class HTTPPostRequest {
    @Test
    public void createUser() {
        JSONObject createuser = new JSONObject();
        createuser.put("name", "alberta");
        createuser.put("job", "tester");

        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(createuser.toJSONString())
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .assertThat()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void registerUserSucccessful() {
        JSONObject registerUserSuccessful = new JSONObject();
        registerUserSuccessful.put("email", "eve.holt@reqres.in");
        registerUserSuccessful.put("password", "pistol");
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(registerUserSuccessful.toString())
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());
    }

    @Test
    public void singleUserUnsuccessful() {
        JSONObject registerUserSuccessful = new JSONObject();
        registerUserSuccessful.put("email", "sydney@fife");
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(registerUserSuccessful.toString())
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("id", equalTo(null))
                .body("token", equalTo(null))
                .body("error", equalTo("Missing password"));

    }

    @Test
    public void loginUsersuccessful() {
        JSONObject loginUserSuccessful = new JSONObject();
        loginUserSuccessful.put("email", "eve.holt@reqres.in");
        loginUserSuccessful.put("password", "cityslicka");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginUserSuccessful.toString())
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .assertThat()
                .statusCode(200)
                .body("token", notNullValue(null));

    }

    @Test
    public void loginUserUnsuccessful() {
        JSONObject loginUserunSuccessful = new JSONObject();
        loginUserunSuccessful.put("email", "eve.holt@reqres.in");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginUserunSuccessful.toString())
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .assertThat()
                .statusCode(400)
                .body("id", equalTo(null))
                .body("token", equalTo(null))
                .body("error", equalTo("Missing password"));
    }

}
