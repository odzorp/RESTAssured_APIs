import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HTTPPutRequest {
    @Test
    public void updateUser(){
        JSONObject Updateuser = new JSONObject();
        Updateuser.put("name", "albert") ;
        Updateuser.put("job","QA Engineer");

        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(Updateuser.toJSONString())
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .assertThat()
                .statusCode(201)
                .log().all();
    }
}
