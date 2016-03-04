package io.dungdm93.bytecode.cglib;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;
import net.sf.cglib.reflect.FastMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReflectionTest {
    private static Object[] dummyArgs;

    @BeforeClass
    public static void setUp() {
        dummyArgs = new Object[0];
    }

    @Test
    public void fastClass() throws Exception {
        FastClass fastClass = FastClass.create(Bean.class);
        FastConstructor fastConstructor = fastClass.getConstructor(Bean.class.getConstructor());
        FastMethod fastMethod = fastClass.getMethod(Bean.class.getMethod("getValue"));
        // no FastField

        Bean bean = (Bean) fastConstructor.newInstance();
        bean.setValue("Hello cglib!");
        assertEquals("Hello cglib!", fastMethod.invoke(bean, dummyArgs));
    }
}
