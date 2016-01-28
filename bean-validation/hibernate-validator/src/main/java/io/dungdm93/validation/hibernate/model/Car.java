package io.dungdm93.validation.hibernate.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Car {
    @NotNull
    public String manufacturer;

    @NotNull
    @Size(min = 2, max = 14)
    public String licensePlate;

    @Min(2)
    public int seatCount;

    private boolean isRegistered;

    public Car() {
    }

    public Car(String manufacturer, String licencePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }

    @AssertTrue
    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }
}
