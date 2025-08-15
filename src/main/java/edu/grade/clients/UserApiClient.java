package edu.grade.clients;

import edu.grade.model.CreateUserRequest;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

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
    public Response auth() {
        // apiClient.setToken();
        return null;
    }

    @Step("Удаление пользователя")
    public void delete() {
        // надо реализовать
    }
}
