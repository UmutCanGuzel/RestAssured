package GoRest;
import Model.User;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class _06_GoRestUsersTest {
   //   endpoint :https://gorest.co.in/public/v2/users
   // {
   //     "name":"{{$randomFullName}}",
   //         "gender":"male",
   //         "email":"{{$randomEmail}}",
   //         "status":"active"
   // }

    // Bearer 4845eef8dee8c6bdaf6a0d6050b50e01cc5848613fdd7c82b1eeaacdb00a893d

    //dönüşte test 201
    //id excract

    Faker randomGenerator=new Faker();
    int userID=0;
    RequestSpecification reqSpec;

    @BeforeClass
    public void setup(){

        baseURI="https://gorest.co.in/public/v2/users";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 4845eef8dee8c6bdaf6a0d6050b50e01cc5848613fdd7c82b1eeaacdb00a893d")
                .setContentType(ContentType.JSON)
                .build();
    }
    @Test(enabled = false)
    public void createUserJson(){

        String rndFullName=randomGenerator.name().fullName();
        String rndEmail=randomGenerator.internet().emailAddress();

        userID=
        given()// giden body ,token ,contentType
                .header("Authorization","Bearer 4845eef8dee8c6bdaf6a0d6050b50e01cc5848613fdd7c82b1eeaacdb00a893d")
                .body("{ \"name\":\""+rndFullName+"\", \"gender\":\"male\",\"email\":\""+rndEmail+"\",\"status\":\"active\"}")//giden body
                .contentType(ContentType.JSON)

                .when()
                .post("https://gorest.co.in/public/v2/users")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;
        System.out.println("userID = " + userID);
    }

    @Test
    public void createUserMAP(){

        String rndFullName=randomGenerator.name().fullName();
        String rndEmail=randomGenerator.internet().emailAddress();

        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",rndFullName);
        newUser.put("gender","male");
        newUser.put("email",rndEmail);
        newUser.put("status","active");

        userID=
                given()// giden body ,token ,contentType
                        .spec(reqSpec)
                        .body(newUser) //giden body
                        .contentType(ContentType.JSON)

                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
        System.out.println("userID = " + userID);
    }
    @Test(enabled = false)
    public void createUserClass(){
        //bunun icin User classında private dan publice cevirdik.

        String rndFullName=randomGenerator.name().fullName();
        String rndEmail=randomGenerator.internet().emailAddress();

        User newUser=new User();
        newUser.name=rndFullName;
        newUser.email=rndEmail;
        newUser.gender="male";
        newUser.status="active";

        userID=
                given()// giden body ,token ,contentType
                        .header("Authorization","Bearer 4845eef8dee8c6bdaf6a0d6050b50e01cc5848613fdd7c82b1eeaacdb00a893d")
                        .body(newUser) //giden body
                        .contentType(ContentType.JSON)

                        .when()
                        .post("https://gorest.co.in/public/v2/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
        System.out.println("userID = " + userID);
    }
    @Test(dependsOnMethods = "createUserMAP")
    public void getUserById(){
        given()
                .spec(reqSpec)


                .when()
                .get(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))

        ;
    }
    @Test(dependsOnMethods ="getUserById")
    public void updateUser(){
        Map<String,String> updateUser=new HashMap<>();
        updateUser.put("name","umut can guzel");

        given()
                .spec(reqSpec)
                .body(updateUser)

                .when()
                .put(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name",equalTo("umut can guzel"))
        ;
    }
    @Test(dependsOnMethods ="updateUser")
    public void deleteUser(){
        given()
                .spec(reqSpec)
                .when()
                .delete(""+userID)

                .then()
                .statusCode(204)
        ;

    }
    @Test(dependsOnMethods ="deleteUser")
    public void deleteUserNegatif(){
        given()
                .spec(reqSpec)
                .when()
                .delete(""+userID)

                .then()
                .statusCode(404)
        ;
    }
}
