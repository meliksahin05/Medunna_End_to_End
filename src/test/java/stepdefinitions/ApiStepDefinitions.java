package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static baseurls.MedunnaBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static stepdefinitions.RoomCreationStepDefinitions.roomIdStr;
import static stepdefinitions.RoomCreationStepDefinitions.roomNumber;

public class ApiStepDefinitions {

    Response response;
    @Given("Admin send Get request for all Rooms")
    public void admin_send_get_request_for_all_rooms() {
        // Set Url
        spec.pathParams("first","api"
                        ,"second","rooms").
                queryParam("sort","createdDate,desc");

        //Set Expected Data

        // Send request And Response:
        response = given(spec).when().get("{first}/{second}");
//        response.prettyPrint();
    }
    @Then("Verify body for the create room")
    public void verify_body_for_the_create_room() {
     JsonPath json = response.jsonPath();
        Object actualRoomId = json.getList("findAll{it.roomNumber == "+roomNumber+"}.id").get(0);
//        int actualRoomId = (int)json.getList("findAll{it.roomNumber == 52113}.id").get(0); type casting olarakta yapabilirsin
        /*
        Yukarida get(0) yapmazsak eger
        biz yukaridaki satiri yazdirdigimizda list icerisinde bir id numarasi elde edecegiz
        Bunu da assert yapamadigimiz icin get(index) kullandik sonrasinda da
        bunu kontainere attik assert yapalim diye

         */
        Object actualRoomType =json.getList("findAll{it.roomNumber == "+roomNumber+"}.roomType").get(0);
        Object actualRoomStatus =json.getList("findAll{it.roomNumber == "+roomNumber+"}.status").get(0);
        Object actualRoomPrice =json.getList("findAll{it.roomNumber == "+roomNumber+"}.price").get(0);
        Object actualRoomDescription =json.getList("findAll{it.roomNumber == "+roomNumber+"}.description").get(0);

//        assertEquals(87416, actualRoomId);

        assertEquals(roomIdStr,actualRoomId+"");
        /*
        Yukaridaki gibi assert yapmamizin sebebide dynamic olmasi icin.
        Bunu nasil yaptik? Ilk once RoomCreation stepdefinition kisminda roomNumber ve roomIdstr i
        class levelinde public static int ve string olarak olarak tanimladik
        Buraya hemen cagirabilmek icin boylelikle dynamic bir sekilde kullandik.

        Burda onemli kisim su biz bir room olusturduk Ui ile bu room i ulasmak icinde   query parametresi kullandik
        Ve bu query parametresi sayesinde filtereledik aradigmizi sonrasinda da assert kisminda bunu assert etiik
        Assert ederken groovy language ile bize verilen roomnumberi ile id mizi elde ettik
        Sonra da bu id ile stepdefinitions da olusturdugumuz alert kisminda yazan id yi karsilastirdik.

         */
        assertEquals("SUITE",actualRoomType);
        assertEquals(true,actualRoomStatus);
        assertEquals(210.00+"",actualRoomPrice+"");
        assertEquals("Batch 179",actualRoomDescription);
    }
}
