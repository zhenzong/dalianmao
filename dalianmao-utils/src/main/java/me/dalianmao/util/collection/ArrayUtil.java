package me.dalianmao.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import me.dalianmao.util.Util;
import me.dalianmao.util.function.FunctionUtil;

/**
 *
 * 数组常用操作方法，主要借助Java 8中{@code Stream}的API<br/>
 *
 * 注意：因为各个方法中接受可变长度方法参数，并且不检查null，所以目标数组不要传null，会得到编译警告和{@code NullPointerException}，建议：
 *
 * <pre>{@code
 *  if (java.util.Objects.nonNull(target) {
 *      ArrayUtil.has(t -> t != null, target);
 *      ArrayUtil.hasDuplicate(target);
 *  }}</pre>
 *
 * 另外，方法参数中的generator接受一个int参数，用来创建出合适大小的新数组，如：
 *
 * <pre>{@code
 *     Person[] result = ArrayUtil.filter(predicate, Person[]::new, persons);
 * }</pre>
 *
 * @author xiezhenzong
 *
 * @see java.util.Arrays
 * @see org.apache.commons.lang.ArrayUtils.ArrayUtils
 */
public abstract class ArrayUtil {

    // has方法

    /**
     * 检查数组中是否有符合条件的元素，请保证target内的元素类型一致
     *
     * @param predicate
     *            条件，注意：在调用{@code predicate}之前先调用{@code nonNull}，即： nonNull &&
     *            predicate
     * @param target
     *            目标数组
     * @return true 存在符合条件的元素，false不存在
     */
    @SafeVarargs
    public static <T> boolean has(Predicate<T> predicate, T... target) {
        return Arrays.stream(target).anyMatch(FunctionUtil.<T> nonNull().and(predicate));
    }

    public static boolean hasNull(Object... target) {
        return Arrays.stream(target).anyMatch(FunctionUtil.isNull());
    }

    public static boolean hasEmpty(String... target) {
        return has(t -> t.isEmpty(), target);
    }

    public static boolean hasDuplicate(Object... target) {
        return target.length != toSet(target).size();
    }

    // filter方法

    /**
     * 构造新数组，包含原数组中除了满足过滤条件的所有元素
     *
     * @param predicate
     *            过滤条件，注意：在调用{@code predicate}之前先调用{@code nonNull}，即： nonNull && predicate
     * @param generator
     *            新数组构造方法
     * @param target
     *            目标数组
     * @return 新数组
     *
     * @see Stream#filter(Predicate)
     * @see Stream#toArray(IntFunction)
     * @see ArrayUtil#remove(Predicate, IntFunction, Object...)
     */
    @SafeVarargs
    public static <T> T[] filter(Predicate<T> predicate, IntFunction<T[]> generator, T... target) {
        return Arrays.stream(target).filter(FunctionUtil.<T> nonNull().and(predicate)).toArray(generator);
    }

    public static String[] filter(Predicate<String> predicate, String... target) {
        return filter(predicate, String[]::new, target);
    }

    public static Integer[] filter(Predicate<Integer> predicate, Integer... target) {
        return filter(predicate, Integer[]::new, target);
    }

    public static Long[] filter(Predicate<Long> predicate, Long... target) {
        return filter(predicate, Long[]::new, target);
    }

    public static Double[] filter(Predicate<Double> predicate, Double... target) {
        return filter(predicate, Double[]::new, target);
    }

    public static Integer[] positive(Integer... target) {
        return filter(t -> t >= 0, Integer[]::new, target);
    }

    public static Long[] positive(Long... target) {
        return filter(t -> t >= 0, Long[]::new, target);
    }

    public static Double[] positive(Double... target) {
        return filter(t -> t >= 0, Double[]::new, target);
    }

    public static Integer[] negative(Integer... target) {
        return filter(t -> t < 0, Integer[]::new, target);
    }

    public static Long[] negative(Long... target) {
        return filter(t -> t < 0, Long[]::new, target);
    }

    public static Double[] negative(Double... target) {
        return filter(t -> t < 0, Double[]::new, target);
    }

    // remove方法

    /**
     * 构造新数组，包含原数组中除了满足移除条件的之外的所有元素
     *
     * @param predicate
     *            移除条件，注意：在调用{@code predicate}之前先调用{@code nonNull}，即： nonNull &&
     *            predicate
     * @param generator
     *            新数组构造方法
     * @param target
     *            目标数组
     * @return 新数组
     *
     * @see Stream#filter(Predicate)
     * @see Stream#toArray(IntFunction)
     * @see Predicate#negate()
     */
    @SafeVarargs
    public static <T> T[] remove(Predicate<T> predicate, IntFunction<T[]> generator, T... target) {
        return filter(predicate.negate(), generator, target);
    }

    @SafeVarargs
    public static <T> T[] removeNull(IntFunction<T[]> generator, T... target) {
        return Arrays.stream(target).filter(FunctionUtil.<T> nonNull()).toArray(generator);
    }

    public static Object[] removeNull(Object... target) {
        return removeNull(Object[]::new, target);
    }

    public static String[] removeNull(String... target) {
        return removeNull(String[]::new, target);
    }

    public static Integer[] removeNull(Integer... target) {
        return removeNull(Integer[]::new, target);
    }

    public static Long[] removeNull(Long... target) {
        return removeNull(Long[]::new, target);
    }

    public static Double[] removeNull(Double... target) {
        return removeNull(Double[]::new, target);
    }

    public static Boolean[] removeNull(Boolean... target) {
        return removeNull(Boolean[]::new, target);
    }

    public static String[] removeEmpty(String... target) {
        return remove(t -> t.isEmpty(), String[]::new, target);
    }

    /**
     * 通过{@code HashSet}去除数组中的重复元素
     *
     * @param generator
     *            新数组构造方法
     * @param target
     *            目标数组
     * @return 去除重复元素后的新数组
     *
     * @see ArrayUtil#removeDuplicateGuaranteeOrder(IntFunction, T...)
     */
    @SafeVarargs
    public static <T> T[] removeDuplicate(IntFunction<T[]> generator, T... target) {
        Set<T> set = toSet(target);
        T[] result = generator.apply(set.size());
        return set.toArray(result);
    }

    /**
     * 通过遍历来去重数组中的重复元素，故能维持数组顺序
     *
     * @param generator
     *            新数组构造方法
     * @param target
     *            目标数组
     * @return 去除重复元素后的新数组
     *
     * @see ArrayUtil#removeDuplicate(IntFunction, T...)
     */
    @SafeVarargs
    public static <T> T[] removeDuplicateGuaranteeOrder(IntFunction<T[]> generator, T... target) {
        T[] result = generator.apply(toSet(target).size());
        int index = 0;
        for (int i = 0, n = target.length; i < n; i++) {
            T t = target[i];
            int j = 0;
            while (j < index && Util.nonEqual(t, result[j])) {
                j++;
            }
            if (j == index) {
                result[index++] = t;
            }
        }
        return result;
    }

    // count方法

    /**
     * 计算满足条件的元素个数
     *
     * @param predicate
     *            条件，注意：在调用{@code predicate}之前先调用{@code nonNull}，即： nonNull &&
     *            predicate
     * @param target
     *            目标数组
     * @return 个数
     *
     * @see Stream#filter(Predicate)
     * @see Stream#count()
     */
    @SafeVarargs
    public static <T> long count(Predicate<T> predicate, T... target) {
        return Arrays.stream(target).filter(FunctionUtil.<T> nonNull().and(predicate)).count();
    }

    @SafeVarargs
    public static <T> List<T> toList(T... target) {
        List<T> result = new ArrayList<>(target.length);
        for (T t : target) {
            result.add(t);
        }
        return result;
    }

    @SafeVarargs
    public static <T> Set<T> toSet(T... target) {
        return toSet(new HashSet<>(target.length), target);
    }

    /**
     * 将数组转为有顺序的集合
     *
     * @param target
     *            目标数组，注意：target中不能包含null元素
     * @return 有顺序的集合
     *
     * @see java.util.TreeSet
     */
    @SafeVarargs
    public static <T> Set<T> toOrderedSet(T... target) {
        return toSet(new TreeSet<>(), target);
    }

    @SafeVarargs
    public static <T> Set<T> toSet(Set<T> container, T... target) {
        for (T t : target) {
            container.add(t);
        }
        return container;
    }

}
