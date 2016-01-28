package io.dungdm93.validation.hibernate.validator;

import io.dungdm93.validation.hibernate.model.Gear;
import io.dungdm93.validation.hibernate.model.GearBox;
import org.hibernate.validator.spi.valuehandling.ValidatedValueUnwrapper;

import java.lang.reflect.Type;

public class GearBoxUnwrapper extends ValidatedValueUnwrapper<GearBox> {
    @Override
    public Object handleValidatedValue(GearBox gearBox) {
        return gearBox == null ? null : gearBox.getGear();
    }

    @Override
    public Type getValidatedValueType(Type valueType) {
        return Gear.class;
    }
}