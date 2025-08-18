package edu.grade.clients;

import edu.grade.model.CreateUserRequest;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private final ApiClient apiClient;

    public UserApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Step("Создание пользователя")
    public Response create(CreateUserRequest user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/api/auth/register");
    }

    @Step("Авторизация пользователя")
    public Response auth(String password, String email) {
        Map<String, String> credentials = Map.of(
                "email", email,
                "password", password
        );

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .body(credentials)
                .post("/api/auth/login");

        // Извлекаем токен и убираем префикс Bearer
        apiClient.setAccessToken(response.jsonPath().getString("accessToken"));

        return response;
    }

    @Step("Редактирование пользователя")
    public Response edit(String email, String name, String password) {
        Map<String, String> credentials = Map.of(
                "email", email,
                "name", name,
                "password", password
        );

        Response response = given().log().all()
                .header("Authorization", apiClient.getAccessToken())
                .contentType(ContentType.JSON)
                .body(credentials)
                .patch("/api/auth/user");

        return response;
    }

    @Step("Удаление пользователя")
    public Response delete() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .delete("/api/auth/user");
    }
}