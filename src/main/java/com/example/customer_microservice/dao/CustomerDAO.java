package com.example.customer_microservice.dao;

import com.example.customer_microservice.dao_prototypes.BaseDAO;
import com.example.customer_microservice.dao_prototypes.CustomerPrototype;
import org.json.JSONObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class CustomerDAO extends BaseDAO implements CustomerPrototype {

    public static void add(String email,
                           String firstName,
                           String lastName,
                           String loginName,
                           String passwordHash) {
        openConnection();
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
            } catch (SQLException throwable) {
                System.out.println("Cannot create statement");
                throwable.printStackTrace();
                return;
            }

            String query = "insert into \"Customer\"" +
                    "(\"Email\", \"FirstName\", \"LastName\", \"LoginName\", \"PasswordHash\") " +
                    "values('" +
                    email + "', '" +
                    firstName + "', '" +
                    lastName + "', '" +
                    loginName + "', '" +
                    passwordHash + "');";
            executeQuery(statement, query);
            closeConnection();
        } else {
            System.out.println("Connection had not been opened");
        }
    }

    public static void delete(String email) {
        openConnection();
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
            } catch (SQLException throwable) {
                System.out.println("Cannot create statement");
                throwable.printStackTrace();
                return;
            }

            String query = "delete from \"Customer\" where \"Email\"='" +
                    email + "';";
            executeQuery(statement, query);
            closeConnection();
        } else {
            System.out.println("Connection had not been opened");
        }
    }

    public static ArrayList<JSONObject> getAll() {
        openConnection();
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
            } catch (SQLException throwable) {
                System.out.println("Cannot create statement");
                throwable.printStackTrace();
                return new ArrayList<>();
            }

            String query = "select * from \"Customer\";";
            ResultSet resultSet = executeQuery(statement, query);

            ArrayList<JSONObject> result = new ArrayList<>();
            if (resultSet != null) {
                try {
                    while (resultSet.next()) {
                        JSONObject currentCustomer = new JSONObject();
                        currentCustomer.put("email", resultSet.getString("Email"));
                        currentCustomer.put("firstName", resultSet.getString("FirstName"));
                        currentCustomer.put("lastName", resultSet.getString("LastName"));
                        currentCustomer.put("loginName", resultSet.getString("LoginName"));
                        currentCustomer.put("passwordHash", resultSet.getString("PasswordHash"));
                        result.add(currentCustomer);
                    }
                } catch (SQLException throwable) {
                    System.out.println("Error while getting data from cursor");
                    throwable.printStackTrace();
                }
            }

            closeConnection();
            return result;
        } else {
            System.out.println("Connection had not been opened");
            return new ArrayList<>();
        }
    }

    public static JSONObject findByID(String email) {
        openConnection();
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
            } catch (SQLException throwable) {
                System.out.println("Cannot create statement");
                throwable.printStackTrace();
                return new JSONObject();
            }

            String query = "select * from \"Customer\" where \"Email\"='" + email + "';";
            ResultSet resultSet = executeQuery(statement, query);

            JSONObject result = new JSONObject();
            if (resultSet != null) {
                try {
                    if (resultSet.next()) {
                        result.put("email", resultSet.getString("Email"));
                        result.put("firstName", resultSet.getString("FirstName"));
                        result.put("lastName", resultSet.getString("LastName"));
                        result.put("loginName", resultSet.getString("LoginName"));
                        result.put("passwordHash", resultSet.getString("PasswordHash"));
                    }
                } catch (SQLException throwable) {
                    System.out.println("Error while getting data from cursor");
                    throwable.printStackTrace();
                }
            }

            closeConnection();
            return result;
        } else {
            System.out.println("Connection had not been opened");
            return new JSONObject();
        }
    }
}
