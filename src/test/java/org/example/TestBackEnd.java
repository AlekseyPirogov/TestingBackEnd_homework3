package org.example;


import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

/**
 * 1.Автоматизируйте GET /recepies/complexSearch (минимум 5 кейсов) и POST /recipes/cuisine (минимум 5 кейсов),
 * используя rest-assured.
 * 2.Сделать автоматизацию цепочки (хотя бы 1 тест со всеми эндпоинтами) для создания и удаления блюда в MealPlan).
 * Подумайте, как использовать tearDown при тестировании POST /mealplanner/:username/shopping-list/items.
 * Воспользуйтесь кейсами, которые вы написали в ДЗ №2, перенеся всю логику из постман-коллекции в код.
 */

public class TestBackEnd
{
           //alternative api key: "db254b5cd61744d39a2deebd9c361444"
    private final String apiKey = "04fbf7a95bcc4e4199ceb3eea9875548";
    private final String hash = "78565c31a9193281f8031cdb027aeb3cd56b5771";
    private String id;

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
    }

    @Nested
    @DisplayName("Test's for request method GET")
    class getTests {

        // Search Recipes_TC#0_Test invalid token
        @Test
        @DisplayName("Search Recipes_TC#0_Test invalid token")
        @Description("Search Recipes_TC#0_Test invalid token")
        @Link("https://api.spoonacular.com/recipes/complexSearch")
        @Issue("localhost")
        @Tag("getRecipeWithInvalidTokenPositiveTest")
        public void getRecipeWithInvalidTokenPositiveTest() {
            given()
                    .queryParam("apiKey", "3a477221fa3c49e6934574fe84c395c5")
                    .when()
                    .get("https://api.spoonacular.com/recipes/complexSearch")
                    .then()
                    .statusCode(401);
        }

        // Search Recipes_TC#1_Test header and responce data
        @Test
        @DisplayName("Search Recipes_TC#1_Test header and responce data")
        @Description("Search Recipes_TC#1_Test header and responce data")
        @Link("https://api.spoonacular.com/recipes/complexSearch")
        @Issue("localhost")
        @Tag("getRecipeWithValidTokenPositiveTest")
        public void getRecipeWithValidTokenPositiveTest() {
            JsonPath response = (JsonPath) given()
                    .queryParam("apiKey", apiKey)
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .when()
                    .get("https://api.spoonacular.com/recipes/complexSearch")
                    .body()
                    .jsonPath();

            assertThat(response.get("results"), Matchers.<Object>not(equalTo(0)));
            assertThat(response.get("offset"), Matchers.<Object>equalTo(0));
            assertThat(response.get("number"), Matchers.<Object>equalTo(10));
            assertThat(response.get("totalResults"), Matchers.<Object>not(equalTo(0)));
        }

        // Search Recipes_TC#2_Test null data in all params
        @Test
        @DisplayName("Search Recipes_TC#2_Test null data in all params")
        @Description("Search Recipes_TC#2_Test null data in all params")
        @Link("https://api.spoonacular.com/recipes/complexSearch")
        @Issue("localhost")
        @Tag("getRecipeWithValidTokenPositiveTest")
        public void getRecipeWithValidTokenPositiveTest_() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("results", not(equalTo(0)))
                    .body("offset", equalTo(0))
                    .body("number", equalTo(10))
                    .body("totalResults", not(equalTo(0)))
                    .when()
                    .get("https://api.spoonacular.com/recipes/complexSearch")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Search Recipes_TC#3_Test valid string data in query
        @Test
        @DisplayName("Search Recipes_TC#3_Test valid string data in query")
        @Description("Search Recipes_TC#3_Test valid string data in query")
        @Link("https://api.spoonacular.com/recipes/complexSearch")
        @Issue("localhost")
        @Tag("getRecipeWithValidQuery")
        public void getRecipeWithValidQuery() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("results", not(equalTo(1)))
                    .body("offset", equalTo(0))
                    .body("number", equalTo(10))
                    .body("totalResults", not(equalTo(0)))
                    .when()
                    .get("https://api.spoonacular.com/recipes/complexSearch?query=pasta")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Search Recipes_TC#4_ Test invalid data in query = Churchkhella
        @Test
        @DisplayName("Search Recipes_TC#4_ Test invalid data in query = Churchkhella")
        @Description("Search Recipes_TC#4_ Test invalid data in query = Churchkhella")
        @Link("https://api.spoonacular.com/recipes/complexSearch")
        @Issue("localhost")
        @Tag("getRecipeWithInvalidQuery")
        public void getRecipeWithInvalidQuery() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("offset", equalTo(0))
                    .body("number", equalTo(10))
                    .body("totalResults", equalTo(0))
                    .when()
                    .get("https://api.spoonacular.com/recipes/complexSearch?query=Churchkhella")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Search Recipes_TC#5_ Test invalid data in query = null
        @Test
        @DisplayName("Search Recipes_TC#5_ Test invalid data in query = null")
        @Description("Search Recipes_TC#5_ Test invalid data in query = null")
        @Link("https://api.spoonacular.com/recipes/complexSearch")
        @Issue("localhost")
        @Tag("getRecipeWithNullQuery")
        public void getRecipeWithNullQuery() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("offset", equalTo(0))
                    .body("number", equalTo(10))
                    .body("totalResults", equalTo(0))
                    .when()
                    .get("https://api.spoonacular.com/recipes/complexSearch?query=null")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }
    }


    @Nested
    @DisplayName("Test's for request method POST")
    class postTests {

        // Classify Cuisine_TC#0_Null params
        @Test
        @DisplayName("Classify Cuisine_TC#0_Null params")
        @Description("Classify Cuisine_TC#0_Null params")
        @Link("https://api.spoonacular.com/recipes/cuisine")
        @Issue("localhost")
        @Tag("getCuisineWithNullQuery")
        public void getCuisineWithNullQuery() {
            JsonPath response = (JsonPath) given()
                    .queryParam("apiKey", apiKey)
                    .queryParam("title", "Falafel Burgers")
                    .when()
                    .post("https://api.spoonacular.com/recipes/cuisine")
                    .body()
                    .prettyPeek()
                    .jsonPath();

            assertThat(response.get("cuisine"), Matchers.<Object>equalTo("American"));
            assertThat(response.get("confidence"), Matchers.<Object>equalTo(0.85F));
        }

        // Classify Cuisine_TC#1_Ttitle in params "Falafel Burgers"
        @Test
        @DisplayName("Classify Cuisine_TC#1_Ttitle in params \"Falafel Burgers\"")
        @Description("Classify Cuisine_TC#1_Ttitle in params \"Falafel Burgers\"")
        @Link("https://api.spoonacular.com/recipes/cuisine")
        @Issue("localhost")
        @Tag("getCuisineWithNullQuery")
        public void getCuisineWithParam() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .queryParam("title", "Falafel Burgers")
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("cuisine", equalTo("American"))
                    .body("confidence", equalTo(0.85F))
                    .when()
                    .post("https://api.spoonacular.com/recipes/cuisine")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Classify Cuisine_TC#2_Title in body "Falafel Burgers"
        @Test
        @DisplayName("Classify Cuisine_TC#1_Ttitle in params \"Falafel Burgers\"")
        @Description("Classify Cuisine_TC#1_Ttitle in params \"Falafel Burgers\"")
        @Link("https://api.spoonacular.com/recipes/cuisine")
        @Issue("localhost")
        @Tag("getCuisineWithNullQuery")
        public void getCuisineWithParamInBody() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .param("title", "Falafel Burgers")
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("cuisine", equalTo("American"))
                    .body("confidence", equalTo(0.85F))
                    .when()
                    .post("https://api.spoonacular.com/recipes/cuisine")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Classify Cuisine_TC#3_Ttitle in body "Falafel Burger"
        @Test
        @DisplayName("Classify Cuisine_TC#3_Ttitle in body \"Falafel Burger\"")
        @Description("Classify Cuisine_TC#3_Ttitle in body \"Falafel Burger\"")
        @Link("https://api.spoonacular.com/recipes/cuisine")
        @Issue("localhost")
        @Tag("getCuisineWithParamInBody_")
        public void getCuisineWithParamInBody_() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .param("title", "Falafel Burger")
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("cuisine", equalTo("Middle Eastern"))
                    .body("confidence", equalTo(0.85F))
                    .when()
                    .post("https://api.spoonacular.com/recipes/cuisine")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Classify Cuisine_TC#4_Title in body "Falafel Burger" and lang in params
        @Test
        @DisplayName("Classify Cuisine_TC#4_Title in body \"Falafel Burger\" and lang in params")
        @Description("Classify Cuisine_TC#4_Title in body \"Falafel Burger\" and lang in params")
        @Link("https://api.spoonacular.com/recipes/cuisine")
        @Issue("localhost")
        @Tag("getCuisineWithParamsInBody")
        public void getCuisineWithParamsInBody() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .queryParam("language", "de")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .param("title", "Falafel Burger")
                    .param("ingredientList", "some tzatziki")
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("cuisine", equalTo("Middle Eastern"))
                    .body("confidence", equalTo(0.85F))
                    .when()
                    .post("https://api.spoonacular.com/recipes/cuisine")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Classify Cuisine_TC#5_Title in params "$50,000 Burger"
        @Test
        @DisplayName("Classify Cuisine_TC#5_Title in params \"$50,000 Burger\"")
        @Description("Classify Cuisine_TC#5_Title in params \"$50,000 Burger\"")
        @Link("https://api.spoonacular.com/recipes/cuisine")
        @Issue("localhost")
        @Tag("getCuisineWithParamsInBody_")
        public void getCuisineWithParamsInBody_() {
            given()
                    .log()
                    .all()
                    .queryParam("apiKey", apiKey)
                    .queryParam("title", "$50,000 Burger")
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("cuisine", equalTo("American"))
                    .body("confidence", equalTo(0.85F))
                    .when()
                    .post("https://api.spoonacular.com/recipes/cuisine")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }
    }

    @Nested
    @DisplayName("Test's for mealplanner")
    class mealplannerTests {

        // Mealplanner_TC#1_Get mealplanner empty week
        @Test
        @DisplayName("Mealplanner_TC#1_Get mealplanner empty week")
        @Description("Mealplanner_TC#1_Get mealplanner empty week")
        @Link("https://api.spoonacular.com/mealplanner/creatorpi/week/2022-05-23")
        @Issue("localhost")
        @Tag("getMealplanner")
        public void getMealplannerEmptyWeek() {
            given()
                    .log()
                    .all()
                    .queryParam("hash", hash)
                    .queryParam("apiKey", apiKey)
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json;charset=utf-8",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("days", empty())
                    .when()
                    .get("https://api.spoonacular.com/mealplanner/creatorpi/week/2022-05-23")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Mealplanner_TC#2_Get mealplanner not empty week
        @Test
        @DisplayName("Mealplanner_TC#2_Get mealplanner not empty week")
        @Description("Mealplanner_TC#2_Get mealplanner not empty week")
        @Link("https://api.spoonacular.com/mealplanner/creatorpi/week/2022-05-30")
        @Issue("localhost")
        @Tag("getMealplanner")
        public void getMealplannerNotEmptyWeek() {
            given()
                    .log()
                    .all()
                    .queryParam("hash", hash)
                    .queryParam("apiKey", apiKey)
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json;charset=utf-8",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    .body("days", not(empty()))
                    .when()
                    .get("https://api.spoonacular.com/mealplanner/creatorpi/week/2022-05-30")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }

        // Mealplanner_TC#3_Add mealplanner
        @Test
        @DisplayName("Mealplanner_TC#3_Add mealplanner with tearDown")
        @Description("Mealplanner_TC#3_Add mealplanner with tearDown")
        @Link("https://api.spoonacular.com/mealplanner/creatorpi/week/2022-06-01")
        @Issue("localhost")
        @Tag("getMealplanner")
        public void addMealplanner() {
            id = given()
                    .log()
                    .all()
                    .queryParam("hash", hash)
                    .queryParam("apiKey", apiKey)   //  1653916881 - 30/05
                    .body("{\n \"date\": 1654072994,\n"          //  1654072994 - 01/06
                            + " \"slot\": 3,\n"
                            + " \"position\": 1,\n"
                            + " \"type\": \"INGREDIENTS\",\n"
                            + " \"value\": {\n"
                            + " \"ingredients\": [\n"
                            + " {\n"
                            + " \"name\": \"3 apple\"\n"
                            + " }\n"
                            + " ]\n"
                            + " }\n"
                            + "}")
                    .when()
                    .post("https://api.spoonacular.com/mealplanner/creatorpi/items")
                    .prettyPeek()
                    .then()
                    .statusCode(200)
                    .extract()
                    .jsonPath()
                    .get("id")
                    .toString();

            tearDown();
        }

        public void tearDown() {
            given()
                    .queryParam("hash", hash)
                    .queryParam("apiKey", apiKey)
                    .delete("https://api.spoonacular.com/mealplanner/creatorpi/items/" + id)
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }
    }

    @Nested
    @DisplayName("Test's for mealplanner shopping-list")
    class Tests {

        // Mealplanner_TC#1_Get shopping list
        @Test
        @DisplayName("Mealplanner-shopping-list_TC#1_Get shopping list")
        @Description("Mealplanner-shopping-list_TC#1_Get shopping list")
        @Link("https://api.spoonacular.com/mealplanner/creatorpi/shopping-list")
        @Issue("localhost")
        @Tag("getShoppinglistWeek")
        public void getShoppinglistWeek() {
            given()
                    .log()
                    .all()
                    .queryParam("hash", hash)
                    .queryParam("apiKey", apiKey)
                    .expect()
                    .statusLine(equalTo("HTTP/1.1 200 OK"))
                    .headers("Content-Type", "application/json;charset=utf-8",
                            "Connection", "keep-alive",
                            "Content-Encoding", "gzip")
                    //.body("days", empty())
                    .when()
                    .get("https://api.spoonacular.com/mealplanner/creatorpi/shopping-list")
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }


        // Mealplanner_TC#1_Get shopping list
        @Test
        @DisplayName("Mealplanner-shopping-list_TC#1_Add item in shopping list")
        @Description("Mealplanner-shopping-list_TC#1_Add item in shopping list")
        @Link("https://api.spoonacular.com/mealplanner/creatorpi/shopping-list")
        @Issue("localhost")
        @Tag("addItemInShoppinglist")
        public void addItemInShoppinglist() {
            id = given()
                    .log()
                    .all()
                    .queryParam("hash", hash)
                    .queryParam("apiKey", apiKey)
                    .body("{\n \"item\": \"1 package baking powder\",\n"
                            + " \"aisle\": \"Baking\",\n"
                            + " \"parse\": true\n"
                            + "}")
                    .when()
                    .post("https://api.spoonacular.com/mealplanner/creatorpi/shopping-list/items")
                    .prettyPeek()
                    .then()
                    .statusCode(200)
                    .extract()
                    .jsonPath()
                    .get("id")
                    .toString();

            tearDown();
        }

        public void tearDown() {
            given()
                    .queryParam("hash", hash)
                    .queryParam("apiKey", apiKey)
                    .delete("https://api.spoonacular.com/mealplanner/creatorpi/shopping-list/items/" + id)
                    .prettyPeek()
                    .then()
                    .statusCode(200);
        }
    }
}