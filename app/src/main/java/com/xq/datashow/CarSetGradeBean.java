package com.xq.datashow;

import java.util.List;

public class CarSetGradeBean {
    private List<String> carSetIndexList, carSetGradeList;

    public CarSetGradeBean(List<String> carSetIndexList, List<String> carSetGradeList) {
        this.carSetIndexList = carSetIndexList;
        this.carSetGradeList = carSetGradeList;
    }

    public List<String> getCarSetIndexList() {
        return carSetIndexList;
    }

    public List<String> getCarSetGradeList() {
        return carSetGradeList;
    }
}
