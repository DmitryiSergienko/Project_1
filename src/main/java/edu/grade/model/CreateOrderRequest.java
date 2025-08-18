package edu.grade.model;

import io.restassured.response.Response;

import java.util.*;

public class CreateOrderRequest {
    private Map<String, String> allIngredients = new HashMap<>();
    private List<String> allIdsList;
    private List<String> idsForOrder = new ArrayList<>();

    public CreateOrderRequest(Response response){
        List<String> ids = response.jsonPath().getList("data._id");
        List<String> names = response.jsonPath().getList("data.name");

        for (int i = 0; i < ids.size(); i++) {
            allIngredients.put(ids.get(i), names.get(i));
        }

        allIdsList = new ArrayList<>(allIngredients.keySet());

        Random random = new Random();
        int randomNumber = random.nextInt(allIdsList.size()) + 1;
        for(int i = 0; i < randomNumber; i++){
            idsForOrder.add(allIdsList.get(i));
        }
    }

    public List<String> getIdsForOrder() {
        return idsForOrder;
    }
}