package com.xq.datashow;

import java.util.List;

public class CarSetPurposeBean {
    private List<String> carSetPurposeList, carSetPurposeCountList;
    private List<NameValue> nameValueList;

    public CarSetPurposeBean(List<NameValue> nameValueList) {
//        this.carSetPurposeList = carSetPurposeList;
//        this.carSetPurposeCountList = carSetPurposeCountList;
        this.nameValueList = nameValueList;
    }

//    public List<String> getCarSetPurposeList() {
//        return carSetPurposeList;
//    }

//    public List<String> getCarSetPurposeCountList() {
//        return carSetPurposeCountList;
//    }

    public static class NameValue {
        private String name;
        private double value;

        public NameValue(String name, double value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "NameValue{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}
