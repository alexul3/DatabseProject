package org.dbms.models;

import org.springframework.data.annotation.Id;

public class Parking {
    @Id
    private String id;
    private String address;
    private int capacity;

    public Parking() {}

    public Parking(String id, String address, int capacity) {
        this.id = id;
        this.address = address;
        this.capacity = capacity;
    }

    public Parking(Long id, String address, int capacity) {
        this.address = address;
        this.capacity = capacity;
    }

    public Parking(String address, int capacity) {
        this.address = address;
        this.capacity = capacity;
    }

    public Long getId() {
        return 1l;
    }

    public String getMongoId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAddress() {
        return address;
    }
}
