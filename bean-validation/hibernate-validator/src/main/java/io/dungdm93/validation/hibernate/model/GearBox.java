package io.dungdm93.validation.hibernate.model;

public class GearBox<T extends Gear> {
    private final T gear;

    public GearBox(T gear) {
        this.gear = gear;
    }

    public Gear getGear() {
        return this.gear;
    }
}