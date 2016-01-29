package io.dungdm93.validation.hibernate.model;

import javax.validation.constraints.NotNull;

public class RentalCar extends Car {
    @NotNull
    String rentalStation;

    public RentalCar() {
        super();
    }

    public RentalCar(String manufacturer, String licencePlate, int seatCount) {
        super(manufacturer, licencePlate, seatCount);
    }
}
