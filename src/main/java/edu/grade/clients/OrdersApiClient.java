package edu.grade.clients;

import edu.grade.model.CreateOrderRequest;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class OrdersApiClient {

    private final ApiClient apiClient;

    public OrdersApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Step("Создание заказа")
    public Response create(boolean trueCase, List<String> ingredients) {
        Response response = given().log().all()
                .header("Authorization", apiClient.getAccessToken())
                .contentType(ContentType.JSON)
                .get("api/ingredients");

        if (trueCase) {
            var allIngredients = new CreateOrderRequest(response);
            ingredients = allIngredients.getIdsForOrder();
        }

        return given().log().all()
                .header("Authorization", apiClient.getAccessToken())
                .contentType(ContentType.JSON)
                .body("{\"ingredients\": [\"" + String.join("\", \"", ingredients) + "\"]}")
                .post("/api/orders");
    }

    @Step("Получение заказов конкретного пользователя")
    public Response takeOrdersUser(int countOrders) {
        for(int i = 0; i < countOrders - 1; i++){
            create(true, null);
        }

        return given().log().all()
                .header("Authorization", apiClient.getAccessToken())
                .contentType(ContentType.JSON)
                .get("/api/orders");
    }
}