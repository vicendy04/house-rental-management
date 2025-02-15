package com.example.hrm.domain;

public enum RoomStatus {
    AVAILABLE("Available"),
    BOOKING("Booking");

    private final String label;

    RoomStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
