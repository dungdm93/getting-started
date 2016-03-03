package io.dungdm93.bytecode.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FoobarTest {

    @Test
    public void fixedValue() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Foobar.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Hello cglib!";
                // return 100; // ClassCastException
            }
        });
        Foobar proxy = (Foobar) enhancer.create();
        System.out.println(proxy.getClass());
        assertEquals("Hello cglib!", proxy.doStaff());
    }

    @Test
    public void invocationHandler() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Foobar.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                if (!method.getDeclaringClass().equals(Object.class) && method.getReturnType().equals(String.class)) {
                    return "Hello cglib!";
                } else {
                    return "Do not know what to do.";
                }
            }
        });
        Foobar proxy = (Foobar) enhancer.create();
        assertEquals("Hello cglib!", proxy.doStaff());
        assertNotEquals("Hello cglib!", proxy.toString());
    }
}
