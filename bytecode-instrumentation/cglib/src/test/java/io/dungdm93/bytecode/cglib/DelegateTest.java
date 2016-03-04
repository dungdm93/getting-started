package io.dungdm93.bytecode.cglib;

import io.dungdm93.bytecode.cglib.delegate.MethodDeputy;
import io.dungdm93.bytecode.cglib.delegate.MulticastDeputy;
import net.sf.cglib.reflect.MethodDelegate;
import net.sf.cglib.reflect.MulticastDelegate;
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

    @Test
    public void multicastDelegate() throws Exception {
        MulticastDelegate delegate = MulticastDelegate.create(MulticastDeputy.class);
        Bean first = new Bean();
        Bean second = new Bean();
        delegate = delegate.add(first);
        delegate = delegate.add(second);

        MulticastDeputy provider = (MulticastDeputy) delegate;
        provider.setValue("Hello world!");

        assertEquals("Hello world!", first.getValue());
        assertEquals("Hello world!", second.getValue());
    }
}
