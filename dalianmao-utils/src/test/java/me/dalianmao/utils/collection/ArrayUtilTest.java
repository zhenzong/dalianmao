package me.dalianmao.utils.collection;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import me.dalianmao.util.collection.ArrayUtil;

/**
 * test for {@code ArrayUtil}
 *
 * @author xiezhenzong
 *
 */
public class ArrayUtilTest {

    @Test
    public void testHas() {
        assertTrue(ArrayUtil.has(t -> t.isEmpty(), "", "", "123"));
        assertFalse(ArrayUtil.has(t -> t.isEmpty(), "1", null));
    }

    @Test(expected = NullPointerException.class)
    public void testHasNullCase() {
        assertTrue(ArrayUtil.has(t -> t != null, null));
    }

    @Test
    public void testHasDuplicate() {
        assertTrue(ArrayUtil.hasDuplicate("", "", "abs"));
        assertFalse(ArrayUtil.hasDuplicate());

    }

    @Test(expected = NullPointerException.class)
    public void testHasDuplicateNullCase() {
        assertTrue(ArrayUtil.hasDuplicate(null));

    }

    @Test
    public void testRemove() {
        String[] result = ArrayUtil.<String> remove(t -> t == null, String[]::new, "", null, "", "abc");
        assertArrayEquals(new String[] { "", "", "abc" }, result);

        result = ArrayUtil.remove(t -> t.isEmpty(), String[]::new, result);
        assertArrayEquals(new String[] { "abc" }, result);
    }

    @Test
    public void testRemoveDuplicate() {
        String[] result = ArrayUtil.removeDuplicateGuaranteeOrder(String[]::new, "", "", "1", "2", null, null);
        assertArrayEquals(new String[] { "", "1", "2", null }, result);
    }

}
