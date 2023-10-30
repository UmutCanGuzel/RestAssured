import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ZippoTestExtract {
    @Test
    public void extractingJsonPath(){
        String countryName=
                given().
                        when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("country") // Path i country olan degeri EXTRACT  yap
                ;
        System.out.println("country = " + countryName);
        Assert.assertEquals(countryName,"United States");//alinan deger buna esit mi
    }
    // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
    // place dizisinin ilk elemanının state değerinin  "California"
    // olduğunu testNG Assertion ile doğrulayınız
    @Test
    public void extractingJsonPath2(){
        String stateName=
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .extract().path("places[0].state")
        ;
        System.out.println("state = " + stateName);
        Assert.assertEquals(stateName,"California");
    }
    // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
    // place dizisinin ilk elemanının place name değerinin  "Beverly Hills"
    // olduğunu testNG Assertion ile doğrulayınız
    @Test
    public void extractingJsonPath3(){
        String placeName=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().path("places[0].'place name'")
                ;
        System.out.println("place = " + placeName);
        Assert.assertEquals(placeName,"Beverly Hills");
    }
    // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
    // limit bilgisinin 10 olduğunu testNG ile doğrulayınız
    @Test
    public void extractingJsonPath4(){
        int limit=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract().path("meta.pagination.limit")
                ;
        System.out.println("limit = " + limit);
        Assert.assertTrue(limit==10);
       // Assert.assertEquals(limit,10);
    }
}
