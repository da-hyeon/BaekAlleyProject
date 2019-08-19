package com.hdh.baekalleyproject.data.model;

public class RestaurantMenu {
    private String menuName;
    private String menuPrice;

    public RestaurantMenu(String menuName, String menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuPrice() {
        return menuPrice;
    }
}
