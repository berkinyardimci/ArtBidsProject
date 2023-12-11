package com.artbids.entity.enums;

public enum Status {

    ACTIVE("Devam ediyor"),
    COMPLETED("Müzayede sona erdi"),
    PENDING("İleri tarihte başlayacak");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
