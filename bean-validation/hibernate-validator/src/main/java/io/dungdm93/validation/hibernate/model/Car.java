package io.dungdm93.validation.hibernate.model;

import io.dungdm93.validation.hibernate.constant.FuelConsumption;
import io.dungdm93.validation.hibernate.validator.MaxAllowedFuelConsumption;
import io.dungdm93.validation.hibernate.validator.ValidPart;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Car {
    @NotNull
    public String manufacturer;

    @NotNull
    @Size(min = 2, max = 14)
    public String licensePlate;

    @Min(2)
    public int seatCount;

    @Valid
    List<@ValidPart String> parts = new ArrayList<>();

    @Valid
    private EnumMap<FuelConsumption, @MaxAllowedFuelConsumption Integer> fuelConsumption
            = new EnumMap<>(FuelConsumption.class);

    private boolean isRegistered = true;

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

    public void addPart(String part) {
        parts.add(part);
    }

    public void setFuelConsumption(FuelConsumption consumption, int value) {
        fuelConsumption.put(consumption, value);
    }
}
