package edu.grade.clients;

import io.restassured.RestAssured;

public class ApiClient {

    private UserApiClient users;
    private OrdersApiClient orders;

    private String token;

    public ApiClient() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        users = new UserApiClient(this);
        orders = new OrdersApiClient(this);
    }

    public UserApiClient users() {
        return users;
    }

    public OrdersApiClient orders() {
        return orders;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
