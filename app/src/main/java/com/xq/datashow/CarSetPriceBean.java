package com.xq.datashow;

import java.util.List;

public class CarSetPriceBean {
    private List<String> carSetNameList, carSetPriceList;

    public CarSetPriceBean (List<String> carSetNameList, List<String> carSetPriceList) {
        this.carSetNameList = carSetNameList;
        this.carSetPriceList = carSetPriceList;
    }

    public List<String> getCarSetNameList() {
        return carSetNameList;
    }

    public List<String> getCarSetPriceList() {
        return carSetPriceList;
    }
}
