package me.dalianmao.util.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集合常用方法
 *
 * @author xiezhenzong
 *
 */
public abstract class SetUtil {

    public static <T> Set<T> empty() {
        return newSet(0);
    }

    public static <T> Set<T> newSet(int size) {
        return new HashSet<>(size);
    }

    public static <T> Set<T> newConcurrentSet(int size) {
        return Collections.newSetFromMap(new ConcurrentHashMap<T, Boolean>(size));
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }

    // convert方法

    // toList

}
