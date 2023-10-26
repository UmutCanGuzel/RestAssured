import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {
    @Test
    public void test1(){
        given()
                //hazirlik islemleri kodlari
                .when()
                //endpoint (url), metod u verip istek gönderiliyor
                .then()
                //assertion,test,data işlemleri
        ;
    }
    @Test
    public void test2(){
        given()
                //hazirlik kismi bos
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()//donen body json data
        ;
    }
    @Test
    public void test2_1(){
        given()
                //hazirlik kismi bos
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().all()//logg.all() :gidip gelen hersey
        ;
    }
    @Test
    public void statusCodeTest(){
        given()
                //hazirlik kismi bos
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()//donen body json data
                .statusCode(200)//test kismi oldugundan assertion status code 200 mu?
        ;
    }
    @Test
    public void contentTypeTest(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()//donen body json data
                .statusCode(200)//test kismi oldugundan assertion status code 200 mu?
                .contentType(ContentType.JSON) //donen datanin tipi JSON mi ?
        ;
    }
    @Test
    public void checkCountryInResponseBody(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .statusCode(200)  // assertion
                .body("country", equalTo("United States")) //assertion
                // body nin country değiskeni "United States" e esit mi ?
        ;
    }
    // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
    // place dizisinin ilk elemanının state değerinin  "California"
    // olduğunu doğrulayınız
    @Test
    public void checkStateInResponseBody(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .statusCode(200)
                .body("places[0].state", equalTo("California")) //assertion
        ;
    }
    // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
    // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
    // olduğunu doğrulayınız
    @Test
    public void checkHasItem(){
        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")
                .then()
                .statusCode(200)
                .body("places.'place name'", hasItem("Dörtağaç Köyü")) //assertion
        ;
    }
    // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
    // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.
    @Test
    public void bodyArraysHasSizeTest(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .body("places", hasSize(1)) //Places ın item size 1 e eşit mi?
        ;
    }
    @Test // Cozum 2
    public void bodyArraysHasSizeTest2(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .body("places.size",equalTo(1)) //Places ın item size 1 e eşit mi?
        ;
    }
}
