package com.utte.oopsixprinciple.lkp;

/**
 * Created by 江婷婷 on 2018/6/6.
 */

public class Room {
    private float area;
    private float price;

    public Room(float area, float price) {
        this.area = area;
        this.price = price;
    }

    public float getArea() {
        return area;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "area=" + area +
                ", price=" + price +
                '}';
    }

}
