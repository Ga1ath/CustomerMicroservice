package com.example.customer_microservice.controller;

import com.example.customer_microservice.service.CustomerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;


@RestController
public class InputController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, String>> signup(
            @RequestBody Map<String, String> userInfo
    ) {
        String email = userInfo.get("Email");
        if (CustomerService.findByIDCustomer(email).length() > 0) {
            HashMap<String, String> cause = new HashMap<>();
            cause.put("Cause", "Customer with this Email already exists");
            return new ResponseEntity<>(cause, HttpStatus.CONFLICT);
        }

        CustomerService.addCustomer(
                userInfo.get("Email"),
                userInfo.get("FirstName"),
                userInfo.get("LastName"),
                email,
                passwordEncoder.encode(userInfo.get("Password")));

        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, String>> signin(
            @RequestBody Map<String, String> userInfo
    ) {
        String email = userInfo.get("Email");
        JSONObject customer = CustomerService.findByIDCustomer(email);
        if (customer.length() == 0) {
            HashMap<String, String> cause = new HashMap<>();
            cause.put("Cause", "Customer with this Email does not exist");
            return new ResponseEntity<>(cause, HttpStatus.CONFLICT);
        }

        if (!passwordEncoder.matches(userInfo.get("Password"),
                customer.getString("passwordHash"))) {
            HashMap<String, String> cause = new HashMap<>();
            cause.put("Cause", "Wrong password");
            return new ResponseEntity<>(cause, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }
}
