import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HTTPGetRequest {
    @Test
    public void getListUsers(){
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("data[2].'email'", equalTo("tobias.funke@reqres.in"))
                .log().all();
    }
@Test
    public void getSingleUser(){
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id",equalTo(2))
                .body("data.email",equalTo("janet.weaver@reqres.in"))
                .log().all();
    }
@Test
    public void getSingleUserNotFound(){
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .body(equalTo("{}"))
                .statusCode(404);
    }

    @Test
    public void listResources(){
        given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2));


    }

    @Test
    public void singleResource(){
        given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("support.url",equalTo("https://reqres.in/#support-heading"));
    }
@Test
    public void singleResourceNotFound(){
    given()
            .when()
            .get("https://reqres.in/api/unknown/23")
            .then()
            .body(equalTo("{}"))
            .statusCode(404);
}

    @Test
    public void delayedResponse(){
        given()
                .when()
                .queryParam("delay",3)
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .body("data",notNullValue())
                .time(lessThan(5L), TimeUnit.SECONDS);

    }
}