package edu.grade.clients;

import io.restassured.RestAssured;

public class ApiClient {

    private UserApiClient users;
    private OrdersApiClient orders;

    private String accessToken;

    public ApiClient() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        users = new UserApiClient(this);
        orders = new OrdersApiClient(this);
    }

    public UserApiClient users() { return users; }

    public OrdersApiClient orders() {
        return orders;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
