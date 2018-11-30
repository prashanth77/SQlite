package com.expensemanger.ibx.expense_manager;

/**
 * Created by ibx on 12/7/18.
 */

public class Note {



    private int _id;
    private String Category;
    private String Date;
    private String Name;
    private String Details;
    private int price;
    private String payment;
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;


    }

    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", Category='" + Category + '\'' +
                ", Date='" + Date + '\'' +
                ", Name='" + Name + '\'' +
                ", Details='" + Details + '\'' +
                ", price=" + price +
                ", payment='" + payment + '\'' +
                '}';
    }
}
