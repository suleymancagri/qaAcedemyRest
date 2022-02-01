package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.startsWith;

public class SampleTest {



    @Test
    public void getWalletMain(){
    String token = "1639576855605b0ef152a5d92438b6295e55d2975bb47d2c14c0efcfc6b33477240038a005c9f" ;
    String suggestedcountrycode = "tr";
     given()
             .log().all().
             when()
             .header("token", token)
             .header("suggestedcountrycode", "tr")
             .when()
             .get("https://mobile-client-api-gateway.fintechdev.getirapi.com/wallet/main").
              then()
             .statusCode(200)
             .log().all();

    }

    @Test
    public void postOtpSend(){
    String token = "1639576855605b0ef152a5d92438b6295e55d2975bb47d2c14c0efcfc6b33477240038a005c9f" ;
    given()
            .log().all().
            when()
            .header("token", token)
            .post("https://mobile-client-api-gateway.fintechdev.getirapi.com/otp/send").
            then()
            .statusCode(200)
            .log().all();

    }
}
