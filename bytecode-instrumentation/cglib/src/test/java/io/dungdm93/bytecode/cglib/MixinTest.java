package io.dungdm93.bytecode.cglib;

import io.dungdm93.bytecode.cglib.mixin.*;
import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.proxy.Mixin;
import net.sf.cglib.util.StringSwitcher;
import org.junit.Test;

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
}
