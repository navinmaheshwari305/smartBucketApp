package com.example.navin_pc.smartbucket;

import java.util.List;

/**
 * Created by NAVIN-PC on 12/11/2017.
 */

public class Bill {
    private String userId;
    private int itemCount;
    private List<Item> itemList;
    private float total;
    private String phone;

    public Bill() {
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean insertItem(Item item){
         return itemList.add(item);
    }

    public String getUserId() {
        return userId;
    }

    public String toString() {
        return "Bill{" +
                "userId='" + userId + '\'' +
                ", itemCount=" + itemCount +
                ", itemList=" + itemList +
                ", total=" + total +
                ", phone='" + phone + '\'' +
                '}';
    }
}
