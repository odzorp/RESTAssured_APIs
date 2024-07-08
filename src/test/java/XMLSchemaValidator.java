import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.baseURI;
import io.restassured.RestAssured;
import org.apache.commons.io.IOUtils;

public class XMLSchemaValidator {

    @Test
    public void validateSoapXML() throws IOException {
        File file = new File("src/test/resources/Add.xml");
        if(file.exists()) System.out.println(">>> File Exists: " + file.getAbsolutePath());
        FileInputStream Add = new FileInputStream(file);
        String requestBody = IOUtils.toString(Add, "UTF-8");

        File schemaFile = new File("src/test/resources/SchemaValidator.xsd");
        if(schemaFile.exists()) System.out.println(">>> Schema File Exists: " + schemaFile.getAbsolutePath());

        File tempSchemaFile = new File("src/test/resources/tempuri.org.xsd");
        if(tempSchemaFile.exists()) System.out.println(">>> Temp Schema File Exists: " + tempSchemaFile.getAbsolutePath());

        // Debugging content of schemas
        FileInputStream schemaStream = new FileInputStream(schemaFile);
        String schemaContent = IOUtils.toString(schemaStream, "UTF-8");
        System.out.println(">>> Schema Content:\n" + schemaContent);

        FileInputStream tempSchemaStream = new FileInputStream(tempSchemaFile);
        String tempSchemaContent = IOUtils.toString(tempSchemaStream, "UTF-8");
        System.out.println(">>> Temp Schema Content:\n" + tempSchemaContent);

        baseURI = "http://www.dneonline.com/";

        given()
                .contentType("text/xml")
                .accept(ContentType.XML)
                .body(requestBody)
                .when()
                .post("/calculator.asmx")
                .then()
                .statusCode(200)
                .log().all()
                .and()
                .body("//*:AddResult.text()", equalTo("5"))
                .and()
                .assertThat()
                .body(RestAssuredMatchers.matchesXsdInClasspath("SchemaValidator.xsd"));
    }
}
