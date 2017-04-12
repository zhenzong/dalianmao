package me.dalianmao.utils.collection;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import me.dalianmao.util.collection.IterableUtil;

/**
 * test for {@code IterableUtil}
 *
 * @author xiezhenzong
 *
 */
public class IterableUtilTest {

    @Test
    public void testFilter() {
        List<String> target = new ArrayList<>();
        target.add(null);
        target.add("");
        target.add("123");
        target.add(null);
        target.add("");
        target.add("124");
        IterableUtil.filter(target, t -> t != null);

        assertEquals(4, target.size());
        assertEquals("", target.get(0));
        assertEquals("123", target.get(1));
        assertEquals("", target.get(2));
        assertEquals("124", target.get(3));

        IterableUtil.filter(target, t -> t.length() > 0);
        assertEquals(2, target.size());
        assertEquals("123", target.get(0));
        assertEquals("124", target.get(1));
    }

}
