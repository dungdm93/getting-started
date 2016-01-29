package io.dungdm93.validation.hibernate.model;

import javax.validation.constraints.NotNull;

public class RentalCar extends Car {
    @NotNull
    String rentalStation;
}
