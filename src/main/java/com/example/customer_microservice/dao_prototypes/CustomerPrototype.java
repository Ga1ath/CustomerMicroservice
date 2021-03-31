package com.example.customer_microservice.dao_prototypes;

import org.json.JSONObject;
import java.util.ArrayList;


public interface CustomerPrototype {

    static void add(String email,
                    String firstName,
                    String lastName,
                    String loginName,
                    String passwordHash) {

    }

    static void delete(String loginName) {

    }

    static ArrayList<JSONObject> getAll() {
        return null;
    }

    static JSONObject findByID(String email) {
        return null;
    }
}