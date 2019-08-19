package com.xq.datashow;

public class DemoItem {
    private int demoImageId;
    private String demoName;

    public DemoItem (int demoImageId, String demoName) {
        this.demoImageId = demoImageId;
        this.demoName = demoName;
    }

    public int getDemoImageId() {
        return demoImageId;
    }

    public String getDemoName() {
        return demoName;
    }
}
