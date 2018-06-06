package com.utte.oopsixprinciple.lkp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江婷婷 on 2018/6/6.
 */

public class Mediator {
    private List<Room> mRooms = new ArrayList<>();

    public Mediator() {
        for (int i = 0; i < 5; i++) {
            mRooms.add(new Room(20 + i, (20 + i) * 200));
        }
    }

    private boolean isSuitable(float area, float price, Room room) {
        return room.getArea() >= area && room.getPrice() <= price;
    }

    public Room rentOut(float area, float price) {
        for (Room room : mRooms) {
            if (isSuitable(area, price, room)) {
                return room;
            }
        }
        return null;
    }

}
