import org.testng.Assert;
import org.testng.annotations.Test;


import io.restassured.response.Response;
import java.util.List;

import static io.restassured.RestAssured.given;

public class _02_ZippoTestExtract {
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
                        .log().body()
                        .extract().path("meta.pagination.limit")
                ;
        System.out.println("limit = " + limit);
        Assert.assertTrue(limit==10);
       // Assert.assertEquals(limit,10);
    }
    @Test
    public void extractingJsonPath5(){
        List<Integer> idler=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract().path("data.id")
                ;
        System.out.println("idler = " + idler);

    }
    @Test
    public void extractingJsonPath6(){
        List<String> names=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract().path("data.name")
                ;
        System.out.println("names = " + names);

    }
    @Test
    public void extractingJsonPathResponsAll(){

        Response body=
                 given()
                           .when()
                           .get("https://gorest.co.in/public/v1/users")

                           .then()
                           .extract().response()
        ;
        List<Integer> idler= body.path("data.id");
        List<String> isimler= body.path("data.name");
        int limit = body.path("meta.pagination.limit");

        System.out.println("idler = " + idler);
        System.out.println("isimler = " + isimler);
        System.out.println("limit = " + limit);

        Assert.assertTrue(isimler.contains("Mahesh Menon"));
        Assert.assertTrue(idler.contains(5599126));
        Assert.assertTrue(limit==10);




    }
}
