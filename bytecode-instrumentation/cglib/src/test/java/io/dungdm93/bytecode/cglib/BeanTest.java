package io.dungdm93.bytecode.cglib;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.ImmutableBean;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class BeanTest {

    @Test(expected = IllegalStateException.class)
    public void immutableBean() throws Exception {
        Bean bean = new Bean();
        bean.setValue("Hello world!");
        Bean immutableBean = (Bean) ImmutableBean.create(bean);

        assertEquals("Hello world!", immutableBean.getValue());

        bean.setValue("Hello world, again!");
        assertEquals("Hello world, again!", immutableBean.getValue());

        immutableBean.setValue("Hello cglib!"); // Causes exception.
    }

    @Test
    public void beanGenerator() throws Exception {
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value", String.class);
        Object bean = beanGenerator.create();
        System.out.println(bean.toString());

        Method setter = bean.getClass().getMethod("setValue", String.class);
        setter.invoke(bean, "Hello cglib!");
        Method getter = bean.getClass().getMethod("getValue");
        assertEquals("Hello cglib!", getter.invoke(bean));
    }
}
