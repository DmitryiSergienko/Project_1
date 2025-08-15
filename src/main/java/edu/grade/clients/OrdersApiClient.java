package edu.grade.clients;

import io.restassured.response.Response;

public class OrdersApiClient {

    private final ApiClient apiClient;

    public OrdersApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response create() {
        String token = apiClient.getToken();
        return null;
    }
}
