package io.dungdm93.bytecode.cglib;

import net.sf.cglib.core.KeyFactory;
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
}
