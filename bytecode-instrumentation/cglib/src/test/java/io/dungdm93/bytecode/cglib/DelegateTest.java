package io.dungdm93.bytecode.cglib;

import io.dungdm93.bytecode.cglib.delegate.MethodDeputy;
import net.sf.cglib.reflect.MethodDelegate;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class DelegateTest {
    @Test
    public void methodDelegate() throws Exception {
        Bean bean = new Bean();
        bean.setValue("Hello cglib!");

        MethodDeputy deputy = (MethodDeputy) MethodDelegate.create(bean, "getValue", MethodDeputy.class);
        assertEquals("Hello cglib!", deputy.evaluate());

        @SuppressWarnings("unchecked")
        Supplier<String> supplier = (Supplier) MethodDelegate.create(bean, "getValue", Supplier.class);
        assertEquals("Hello cglib!", supplier.get());
    }
}
