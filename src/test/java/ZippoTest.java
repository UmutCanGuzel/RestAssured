import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
    @Test
    public void combinigTest(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .statusCode(200)
                .body("places",hasSize(1))
                .body("places[0].state",equalTo("California"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))
        ;
    }
    @Test
    public void pathParamTest(){
        given()
                .pathParam("ulke","us")
                .pathParam("postaKod",90210)
                .log().uri()  //request link calismadan onceki hali
                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKod}")
                .then()
                .statusCode(200)
        ;
    }
    @Test
    public void queryParamTest(){
        //https://gorest.co.in/public/v1/users?page=3 bu sekilde query param gibi gelirse karisik gelen kısmı .param() seklinde ekliyoruz
        given()
                .param("page",1) //?page=1 seklinde linke ekleniyor
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")// ?page=1 seklinde eklendi

                .then()
                .statusCode(200)
                .log().body()
        ;
    }
 // https://gorest.co.in/public/v1/users?page=3
 // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
 // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.
    @Test
    public void queryParamTest2(){
        for (int i = 1; i <=10 ; i++) {
            given()

                    .param("page", i)
                    .log().uri()
                    .when()
                    .get("https://gorest.co.in/public/v1/users")
                    .then()
                    .body("meta.pagination.page",equalTo(i))
                    .statusCode(200)
            ;
        }
    }
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    @BeforeClass
    public void setup(){
        baseURI="https://gorest.co.in/public/v1";
        requestSpec=new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI) // log().uri()
                .build();

        responseSpec=new ResponseSpecBuilder()
                .expectStatusCode(200) //statusCode(200)
                .log(LogDetail.BODY) //log().body()
                .expectContentType(ContentType.JSON)
                .build();
    }
    @Test
    public void requestResponseSpecificationn(){
        given()
                .param("page",1)
                .spec(requestSpec)

                .when()
                .get("/users") // http yok ise baseUri bas tarafina gelir.

                .then()
                .spec(responseSpec)
        ;
    }

}
