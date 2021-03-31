package com.example.customer_microservice.service;


import com.example.customer_microservice.dao.CustomerDAO;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CustomerService {

    public static void addCustomer(String email, String firstName, String lastName,
                                   String loginName, String passwordHash) {
        CustomerDAO.add(email, firstName, lastName, loginName, passwordHash);
    }

    public static void deleteCustomer(String email) {
        CustomerDAO.delete(email);
    }

    public static ArrayList<JSONObject> getAllCustomers() {
        return CustomerDAO.getAll();
    }

    public static JSONObject findByIDCustomer(String email) {
        return CustomerDAO.findByID(email);
    }
}
