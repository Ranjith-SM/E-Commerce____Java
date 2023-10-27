package org.example.Models;

import java.sql.Timestamp;

public class Category {
    private int id;
    private String CName;

    public Category(int id, String category) {
        this.id= id;
        this.CName = category;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCName() {
        return this.CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }


}
