package io.dungdm93.bytecode.cglib;

import net.sf.cglib.beans.ImmutableBean;
import org.junit.Test;

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
}