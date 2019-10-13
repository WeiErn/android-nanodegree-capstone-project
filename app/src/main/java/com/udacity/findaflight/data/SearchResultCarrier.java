package com.udacity.findaflight.data;

public class SearchResultCarrier {

    private int carrierId;
    private String name;

    public SearchResultCarrier(int carrierId, String name) {
        this.carrierId = carrierId;
        this.name = name;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public String getName() {
        return name;
    }
}
