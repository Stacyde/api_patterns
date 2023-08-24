package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;


import static io.restassured.RestAssured.given;

public class Generator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private Generator() {
    }

    public static String generateUsername(String Locale) {
        var faker = new Faker(new Locale("en"));
        String username = faker.name().username();
        return username;
    }

    public static String generatePassword(String Locale) {
        var faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto generateUser(String locale,String status) {
            var user = new RegistrationDto(generateUsername(locale), generatePassword(locale), status);
            sendRequest(user);
            return user;
        }
        private static void sendRequest(RegistrationDto user) {
                    given()
                            .spec(requestSpec)
                            .body(user)
                            .when()
                            .post("/api/system/users")
                            .then()
                            .statusCode(200);
        }
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}




