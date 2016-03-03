package io.dungdm93.bytecode.cglib;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BulkBean;
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

    @Test
    public void beanCopier() throws Exception {
        BeanCopier copier = BeanCopier.create(Bean.class, Seed.class, false);

        Bean bean = new Bean();
        bean.setValue("Hello cglib!");

        Seed seed = new Seed();
        copier.copy(bean, seed, null);

        assertEquals("Hello cglib!", seed.getValue());
    }

    @Test
    public void bulkBean() throws Exception {
        BulkBean bulkBean = BulkBean.create(Bean.class,
                new String[]{"getValue"},
                new String[]{"setValue"},
                new Class[]{String.class});
        Bean bean = new Bean();
        bean.setValue("Hello world!");
        assertEquals(1, bulkBean.getPropertyValues(bean).length);
        assertEquals("Hello world!", bulkBean.getPropertyValues(bean)[0]);
        bulkBean.setPropertyValues(bean, new Object[]{"Hello cglib!"});
        assertEquals("Hello cglib!", bean.getValue());
    }
}
