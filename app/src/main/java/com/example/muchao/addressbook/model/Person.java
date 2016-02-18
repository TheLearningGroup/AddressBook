package com.example.muchao.addressbook.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by muchao on 16/2/18.
 */
public class Person {
    public static final String JSON_ID = "id";
    public static final String JSON_NAME = "name";
    public static final String JSON_PHONE = "phone";
    public static final String JSON_DATE = "date";

    private UUID id;
    private String name;
    private String phone;
    private Date date;

    public Person() {
        id = UUID.randomUUID();
        date = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone.toString();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public String toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id.toString());
        json.put(JSON_NAME, name);
        json.put(JSON_PHONE, phone);
        json.put(JSON_DATE, date.getTime());
        return json.toString();
    }
}