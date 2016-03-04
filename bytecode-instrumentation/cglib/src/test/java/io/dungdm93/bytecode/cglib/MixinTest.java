package io.dungdm93.bytecode.cglib;

import io.dungdm93.bytecode.cglib.mixin.*;
import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.Mixin;
import net.sf.cglib.util.StringSwitcher;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MixinTest {
    @Test
    public void keyFactory() throws Exception {
        PeaKeyFactory keyFactory = (PeaKeyFactory) KeyFactory.create(PeaKeyFactory.class);
        Object key = keyFactory.newInstance("foo", 42);

        Map<Object, String> map = new HashMap<>();
        map.put(key, "Hello cglib!");
        assertEquals("Hello cglib!", map.get(keyFactory.newInstance("foo", 42)));
    }

    @Test
    public void mixin() throws Exception {
        WhiteBox mixin = (WhiteBox) Mixin.create(
                new Class[]{Colorable.class, Shapeable.class, WhiteBox.class},
                new Object[]{new White(), new Square()});
        assertEquals("White", mixin.color());
        assertEquals(4, mixin.sides());
    }

    @Test
    public void stringSwitcher() throws Exception {
        String[] strings = new String[]{"one", "two"};
        int[] values = new int[]{1, 2};
        StringSwitcher stringSwitcher = StringSwitcher.create(strings, values, true);

        assertEquals(1, stringSwitcher.intValue("one"));
        assertEquals(2, stringSwitcher.intValue("two"));
        assertEquals(-1, stringSwitcher.intValue("three"));
    }

    @Test
    public void interfaceMaker() throws Exception {
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        Signature signature = new Signature("foo", Type.DOUBLE_TYPE, new Type[]{Type.INT_TYPE});
        interfaceMaker.add(signature, new Type[0]);
        Class iface = interfaceMaker.create();

        assertEquals(1, iface.getMethods().length);
        assertEquals("foo", iface.getMethods()[0].getName());
        assertEquals(double.class, iface.getMethods()[0].getReturnType());
    }
}
