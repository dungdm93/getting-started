package io.dungdm93.bytecode.cglib;

import net.sf.cglib.proxy.*;
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

    @Test
    public void methodInterceptor() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Foobar.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                    throws Throwable {
                if (!method.getDeclaringClass().equals(Object.class) && method.getReturnType().equals(String.class)) {
                    return "Hello cglib!";
                } else {
                    return proxy.invokeSuper(obj, args);
                }
            }
        });
        Foobar proxy = (Foobar) enhancer.create();
        assertEquals("Hello cglib!", proxy.doStaff());
        assertNotEquals("Hello cglib!", proxy.toString());
        System.out.println(proxy.hashCode()); // Does not throw an exception or result in an endless loop.
    }

    @Test
    public void lazyLoad() throws Exception {
        LazyLoadContainer c = new LazyLoadContainer();
        System.out.println("Create container done");
        System.out.println(c.foobar.doStaff());
        // System.out.println(c.foobar); // print "Start lazy loaded!" only once
    }

    @Test
    public void dispatcher() throws Exception {
        DispatcherContainer c = new DispatcherContainer();
        System.out.println("Create container done");
        System.out.println(c.foobar.doStaff());
        System.out.println(c.foobar);
    }

    @Test
    public void proxyRefDispatcher() throws Exception {
        ProxyRefDispatcherContainer c = new ProxyRefDispatcherContainer();
        System.out.println("Create container done");
        System.out.println(c.foobar.doStaff());
        System.out.println(c.foobar);
        System.out.println(c);
    }

    @Test
    public void noOp() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Foobar.class);
        enhancer.setCallback(NoOp.INSTANCE);

        Foobar foobar = (Foobar) enhancer.create();
        System.out.println(foobar.doStaff());
        System.out.println(foobar.toString());
        System.out.println(foobar.hashCode());
    }

    static class LazyLoadContainer {
        Foobar foobar;

        LazyLoadContainer() {
            // System.out.println("Start eager load!");
            // foobar = new Foobar();
            foobar = (Foobar) Enhancer.create(Foobar.class, new LazyLoader() {
                @Override
                public Object loadObject() throws Exception {
                    System.out.println("Start lazy loaded!");
                    return new Foobar();
                }
            });
        }
    }

    static class DispatcherContainer {
        Foobar foobar;

        DispatcherContainer() {
            foobar = (Foobar) Enhancer.create(Foobar.class, new Dispatcher() {
                @Override
                public Object loadObject() throws Exception {
                    System.out.println("Start dispatcher!");
                    return new Foobar();
                }
            });
        }
    }

    static class ProxyRefDispatcherContainer {
        Foobar foobar;

        ProxyRefDispatcherContainer() {
            foobar = (Foobar) Enhancer.create(Foobar.class, new ProxyRefDispatcher() {
                @Override
                public Object loadObject(Object proxy) throws Exception {
                    System.out.printf("Start ProxyRefDispatcher proxy: %s%n!", proxy.getClass());
                    return new Foobar();
                }
            });
        }
    }
}
