package com.example.navin_pc.smartbucket;

/**
 * Created by NAVIN-PC on 12/11/2017.
 */

public class Item {
    String barcode;
    String name;
    float rate;
    float weight;
    int quantity=0;

    public Item() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "barcode='" + barcode + '\'' +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", weight=" + weight +
                ", quantity=" + quantity +
                '}';
    }
}
