package io.github.mfaisalkhatri.soapapitests;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import org.testng.annotations.Test;

/**
 * @author Faisal Khatri
 * @since 7/07/2023
 **/

public class NumberToWordsTest {

    private static final String BASE_URL = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso";

    @Test
    public void testNumberToWordsAPI () {
        URI file = null;
        try {
            file = Objects.requireNonNull (getClass ().getClassLoader ()
                    .getResource ("numbertowordrequest.xml"))
                .toURI ();
        } catch (URISyntaxException e) {
            throw new Error ("Error in URI syntax", e);
        }
        File body = new File (file);

        String response = given ().when ()
            .body (body)
            .contentType ("text/xml; charset=utf-8")
            .log ()
            .all ()
            .post (BASE_URL)
            .then ()
            .log ()
            .all ()
            .statusCode (200)
            .extract ()
            .response ()
            .asString ();

        XmlPath xmlpath = new XmlPath (response).using (new XmlPathConfig ("UTF-8"));
        assertEquals (xmlpath.getString ("m:NumberToWordsResult"), "five thousand nine hundred and fifty eight ");
    }
}
