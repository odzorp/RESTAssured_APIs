import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SoapXMLRequest {

    @Test
    public void validateSoapXML() throws IOException {
        File file = new File("src/test/resources/Add.xml");
        if (file.exists()) System.out.println(">>> File Exists");

        FileInputStream Add = new FileInputStream(file);
        String requestbody = IOUtils.toString(Add, "UTF-8");

        baseURI = "http://www.dneonline.com";
        given()
                .contentType("text/xml")
                .accept(ContentType.XML)
                .body(requestbody)
                .when()
                .post("/calculator.asmx")
                .then()
                .statusCode(200)
                .log().all();
    }
}
