package swapisuites;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.given;

public class testActions {
        String endpoint = System.getenv("SWAPI_ENDPOINT");
        String token ="";
    /*@Test(description= "Test enpoint, gold skin and films Appears")
    public void filmsAppears(){
        //Variable definition
        String skinColor = "gold";

        Map<String,String> headers = new HashMap<>();
        headers.put("Autorization","Bearer" + token);
        headers.put("Accept","application/json");

        //Calls API
        Response filmAppearResponse = given().headers(headers).queryParam("skin_color",skinColor).when().get(endpoint);
        Assert.assertEquals(filmAppearResponse.statusCode(),200);

        //Deserealization of the response
        String fillmAppearResponseString = filmAppearResponse.getBody().asString();
        JsonPath js = new JsonPath(fillmAppearResponseString);
        List<String> films = js.getList("films");
        System.out.println("Films appears: " + films.size());
        Assert.assertEquals(films.size(),6);
    }*/

    @Test(description= "Test release date and film description")
    public void infoMovie(){

        String expectedFormat = "yyyy-MM-dd";

        Response filmAppearResponse = given().when().get(endpoint);

        String filmAppearResponseString = filmAppearResponse.getBody().asString();
        JsonPath js = new JsonPath(filmAppearResponseString);

        List<String> films = js.getList("films");

        Response filmsResponse = given().when().get(films.get(1));
        String filmsResponseString = filmsResponse.getBody().asString();
        JsonPath jsFilm = new JsonPath(filmsResponseString);

        String date = jsFilm.getString("release_date");
        Assert.assertTrue(isValidDateFormat(date,expectedFormat));

        List<String> characters = jsFilm.getList("characters");

        List<String> planets = jsFilm.getList("planets");
        List<String> starships = jsFilm.getList("starships");
        List<String> vehicles = jsFilm.getList("vehicles");
        List<String> species = jsFilm.getList("species");

        System.out.println("Characters: " + characters +"\nPlanets: " + planets + "\nStarships: " + starships + "\nVehicles: " + vehicles + "\nSpecies: " + species);
    }

    /*@Test(description= "Test release date and film description")
    public void infoPlanet(){

    }*/

    public static boolean isValidDateFormat(String date, String expectedFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
