package me.dalianmao.util.function;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import me.dalianmao.util.Util;

/**
 * 常用的{@code Function}
 *
 * @author xiezhenzong
 *
 */
public abstract class FunctionUtil {

    public static <T> Predicate<T> isNull() {
        return t -> t == null;
    }

    public static <T> Predicate<T> nonNull() {
        return t -> t != null;
    }

    public static <T> BiPredicate<T, T> equal() {
        return (a, b) -> Objects.equals(a, b);
    }

    public static <T> BiPredicate<T, T> nonEqual() {
        return (a, b) -> Util.nonEqual(a, b);
    }

}
