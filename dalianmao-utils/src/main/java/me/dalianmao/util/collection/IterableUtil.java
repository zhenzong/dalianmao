package me.dalianmao.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.dalianmao.util.function.FunctionUtil;

/**
 * {@code Iterable}常用的方法，而{@code List}和{@code Set}都是{@code Iterable}，所以这里的方法适用于{@code List}和{@code Set}
 *
 * @author xiezhenzong
 *
 * @see Collection#removeIf(Predicate)
 */
public abstract class IterableUtil {

    private static final Logger LOG = LoggerFactory.getLogger(IterableUtil.class);

    // join 方法

    /**
     * 将对象拼成字符串，并且跳过null元素
     *
     * @param target
     *            拼接的目标
     * @param separator
     *            分隔符
     * @param printer
     *            打印器
     * @return 拼接后的字符串
     *
     * @see ListUtil#split(String, String, Function)
     */
    public static <T> String join(Iterable<T> target, String separator, Function<T, String> printer) {
        Iterator<T> iterator = target.iterator();
        StringBuilder builder = new StringBuilder();
        iterator.forEachRemaining(item -> {
            if (item != null) {
                builder.append(printer.apply(item)).append(separator);
            } else {
                LOG.debug("[IterableUtil#join] skip a null item ");
            }
        });
        if (builder.length() > 0) {
            int length = builder.length();
            builder.delete(length - separator.length(), length);
        }
        return builder.toString();
    }

    /**
     * 使用{@code toString}方法来将对象拼到一起
     *
     * @param target
     *            目标
     * @param separator
     *            分隔符
     * @return 拼接后的字符串
     *
     * @see IterableUtil#join(Iterable, String, Function)
     */
    public static <T> String join(Iterable<T> target, String separator) {
        return join(target, separator, t -> t.toString());
    }

    public static <T> String join(Iterable<T> target, char separator) {
        return join(target, String.valueOf(separator));
    }

    /**
     * 将对象拼成字符串，并且当遇到null元素，使用{@code defaultValue}替代
     *
     * @param target
     *            拼接的目标
     * @param separator
     *            分隔符
     * @param printer
     *            打印器
     * @param defaultValue
     *            默认字符串
     * @return 拼接后的字符串
     *
     */
    public static <T> String joinWithDefault(Iterable<T> target, String separator, Function<T, String> printer, String defaultValue) {
        Iterator<T> iterator = target.iterator();
        StringBuilder builder = new StringBuilder();
        iterator.forEachRemaining(t -> {
            if (t != null) {
                builder.append(printer.apply(t));
            } else {
                builder.append(defaultValue);
            }
            builder.append(separator);
        });
        if (builder.length() > 0) {
            int length = builder.length();
            builder.delete(length - separator.length(), length);
        }
        return builder.toString();
    }

    public static <T> String joinWithDefault(Iterable<T> target, String separator, String defaultValue) {
        return joinWithDefault(target, separator, t -> t.toString(), defaultValue);
    }

    public static <T> String joinWithDefault(Iterable<T> target, char separator, String defaultValue) {
        return joinWithDefault(target, String.valueOf(separator), defaultValue);
    }

    // has方法

    /**
     * 检查迭代器中是否有符合条件的元素
     *
     * @param predicate
     *            条件，注意：在调用{@code predicate}之前先调用{@code nonNull}，即： nonNull && predicate
     * @param target
     *            目标数组
     * @return true 存在符合条件的元素，false不存在
     */
    public static <T> boolean has(Iterable<T> target, Predicate<T> predicate) {
        return StreamSupport.stream(target.spliterator(), false).anyMatch(FunctionUtil.<T> nonNull().and(predicate));
    }

    public static <T> boolean hasNull(Iterable<T> target) {
        return StreamSupport.stream(target.spliterator(), false).anyMatch(FunctionUtil.isNull());
    }

    /**
     * 检查是否有空的字符串
     *
     * @param target
     *            目标迭代器
     * @return true 有空字符串，false没有
     */
    public static boolean hasEmpty(Iterable<String> target) {
        return has(target, t -> t.isEmpty());
    }

    // filter方法

    /**
     * 筛选迭代器中符合条件的元素
     *
     * @param predicate
     *            条件，注意：在调用{@code predicate}之前先调用{@code nonNull}，即： nonNull && predicate
     * @param target
     *            目标迭代器
     */
    public static <T> void filter(Iterable<T> target, Predicate<T> predicate) {
        StreamSupport.stream(target.spliterator(), false).filter(FunctionUtil.<T> nonNull().and(predicate));
    }

    public static <T> void filterNonNull(Iterable<T> target) {
        StreamSupport.stream(target.spliterator(), false).filter(FunctionUtil.<T> nonNull());
    }

    public static void filterNonEmpty(Iterable<String> target) {
        filter(target, t -> t.length() > 0);
    }

    // toMap方法

    /**
     * 将链表中的元素转化为map，并且跳过null元素，如果key一样的则使用最新的值
     *
     * @param <T>
     *            原类型
     * @param <K>
     *            key类型
     * @param <V>
     *            value类型
     * @param target
     *            目标链表
     * @param keyMapper
     *            key映射
     * @param valueMapper
     *            value映射
     * @return 结果map
     *
     * @see java.util.stream.Stream#collect(java.util.stream.Collector)
     * @see java.util.stream.Collectors#toMap(Function, Function,
     *      java.util.function.BinaryOperator)
     *
     * @apiNote 本方法除了会跳过null元素之外，大概等同于：
     *
     *          <pre>
     * {@code StreamSupport.stream(target.spliterator(), false).collect(Collectors.toMap(keyMapper, valueMapper, (o, n) -> n));}
     *          </pre>
     * 
     *          收集元素到map中也可以使用{@code Collectors}中各种{@code toMap}方法
     */
    public static <K, V, T> Map<K, V> toMap(Iterable<T> target, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        Iterator<T> iterator = target.iterator();
        Map<K, V> result = new HashMap<>();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (item != null) {
                result.put(keyMapper.apply(item), valueMapper.apply(item));
            }
        }
        return result;
    }
}
