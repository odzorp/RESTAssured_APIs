import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HTTPPatchRequest {
    @Test
    public void patchUser(){
        JSONObject patchUser = new JSONObject();
        patchUser.put("name", "kwame");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(patchUser.toJSONString())
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();

    }
}
