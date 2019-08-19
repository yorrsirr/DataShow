package com.xq.datashow;

public class CarSet {
    private String brandName, carSetName, price;

    public CarSet (String brandName, String carSetName, String price) {
        this.brandName = brandName;
        this.carSetName = carSetName;
        this.price = price;
    }


    public String getBrandName() {
        return brandName;
    }

    public String getCarSetName() {
        return carSetName;
    }

    public String getPrice() {
        return price;
    }
}
