package services;

import io.cucumber.gherkin.internal.com.eclipsesource.json.Json;
import io.restassured.http.ContentType;
import org.testng.annotations.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingTest {
    private static Response response;
    private static Integer bookingId;
    private static String tokencode;

   @BeforeClass
   public void postCreateBook(){
       String postData="{\n" +
               "    \"firstname\" : \"Jackie\",\n" +
               "    \"lastname\" : \"Danielss\",\n" +
               "    \"totalprice\" : 90,\n" +
               "    \"depositpaid\" : true,\n" +
               "    \"bookingdates\" : {\n" +
               "        \"checkin\" : \"2018-01-01\",\n" +
               "        \"checkout\" : \"2019-01-01\"\n" +
               "    },\n" +
               "    \"additionalneeds\" : \"Travel\"\n" +
               "}";

       response = given().
               body(postData).contentType(ContentType.JSON).
               log().all().
               when().post("https://restful-booker.herokuapp.com/booking");
       String jsonString = response.asString();
       bookingId = JsonPath.from(jsonString).get("bookingid");
   }

   @DataProvider(name = "dataProvider")
    public Object[][] dataProvider(){
        return new Object[][]{
                {bookingId,200}

        };

    }
    @Test(dataProvider = "dataProvider" )
    public void getBooking(int bookingid,int status){
       given().log().all().
               when().get("https://restful-booker.herokuapp.com/booking/"+bookingid).
               then().
               statusCode(status).
               log().all();
    }

    @Test
    public static void postToken(){
        String tokenbody="{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        response = given().
                body(tokenbody).contentType(ContentType.JSON).
                log().all().
                when().post("https://restful-booker.herokuapp.com/auth");
        String jsonString = response.asString();
        tokencode = JsonPath.from(jsonString).get("token");
    }

    @Test(dataProvider = "dataProvider" )
    public void putUpdateBooking( int bookingid,int status){

        String updateData="{\n" +
                "    \"firstname\" : \"Jack\",\n" +
                "    \"lastname\" : \"Danielss\",\n" +
                "    \"totalprice\" : 58,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Travel\"\n" +
                "}";

        given().
                body(updateData).contentType(ContentType.JSON).
                log().all().
                when().
                header("Cookie","token="+tokencode+"").
                put("https://restful-booker.herokuapp.com/booking/"+ bookingid).
                then().log().all().statusCode(status);

   }

   @AfterClass
    public void deleteBooking(){

       given().log().all().header("Cookie","token="+tokencode+"").
                contentType(ContentType.JSON).
                when().
                delete("https://restful-booker.herokuapp.com/booking/"+ bookingId).
                then().log().all();

   }
















}


