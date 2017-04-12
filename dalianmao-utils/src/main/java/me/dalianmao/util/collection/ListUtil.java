package me.dalianmao.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 链表常用方法
 *
 * @author xiezhenzong
 *
 * @see com.google.common.collect.Lists
 * @see org.apache.commons.collections.ListUtils
 */
public abstract class ListUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ListUtil.class);

    public static <T> List<T> empty() {
        return newList(0);
    }

    public static <T> List<T> newList(int size) {
        return new ArrayList<>(size);
    }

    /**
     * 检查是否有重复元素，包括重复的null
     *
     * @param target
     *            目标集合
     * @return true 有重复元素，false 没有
     */
    public static <T> boolean hasDuplicate(List<T> target) {
        return target.size() != toSetWithDefaultFactory(target, i -> null).size();
    }

    // split方法

    /**
     * 将字符串{@code target}进行拆分，并将每个item用{@code parser}进行转化，最后放到{@code List}中
     *
     * @param target
     *            目标字符串, null或者empty则返回一个empty链表
     * @param separator
     *            分隔符
     * @param parser
     *            解析器
     * @return 结果链表
     */
    public static <T> List<T> split(String target, String separator, Function<String, T> parser) {
        if (StringUtils.isEmpty(target)) {
            return new ArrayList<>(0);
        }
        String[] items = target.split(separator);
        List<T> result = new ArrayList<>(items.length);
        Arrays.stream(items).forEach(i -> result.add(parser.apply(i)));
        return result;
    }

    public static List<String> split(String string, char separator) {
        return split(string, String.valueOf(separator), Function.identity());
    }

    public static List<String> split(String string, String separator) {
        return split(string, separator, Function.identity());
    }

    // convert方法

    /**
     * 转化链表，跳过原链表中的null
     *
     * @param <S>
     *            原类型
     * @param <T>
     *            目标类型
     * @param source
     *            原链表, null则返回一个empty链表
     * @param converter
     *            转换器
     * @return target 目标链表
     *
     * @see ListUtil#convert(List, Function)
     */
    public static <S, T> List<T> convert(List<S> source, Function<S, T> converter) {
        if (CollectionUtils.isEmpty(source)) {
            return empty();
        }
        List<T> targets = newList(source.size());
        for (S s : source) {
            if (s != null) {
                targets.add(converter.apply(s));
            } else {
                LOG.debug("[ListUtil#convert] skip a null item");
            }
        }
        return targets;
    }

    /**
     * 一对一转化链表，检查原链表中的null，遇到null时，调用{@code defaultFactory}生成一个默认值加到链表中
     *
     * @param <S>
     *            原类型
     * @param <T>
     *            目标类型
     * @param source
     *            原链表, null则返回一个empty链表
     * @param converter
     *            转换器
     * @param defaultFactory
     *            默认值生成器, 类型为{@code IntFunction}，其中{@code apply}的参数为null元素的下标
     * @return target 目标链表
     *
     * @see ListUtil#convert(List, Function)
     * @see IterableUtil#filterNonNull(Iterable)
     * @see IntFunction
     */
    public static <S, T> List<T> convertWithDefaultFactory(List<S> source, Function<S, T> converter, IntFunction<T> defaultFactory) {
        if (CollectionUtils.isEmpty(source)) {
            return empty();
        }
        List<T> targets = newList(source.size());
        int index = 0;
        for (S s : source) { // 这里使用迭代器，不使用下标迭代来访问链表，某些情况下效率更好
            if (s != null) {
                targets.add(converter.apply(s));
            } else {
                targets.add(defaultFactory.apply(index));
            }
            index++;
        }
        return targets;
    }

    // toSet

    /**
     * 将{@code source}转化为一个集合，并且跳过null元素。
     *
     * <ol>
     * <li>没有处理重复元素
     * <li>{@code Stream.collect}方法配合{@code Collectors.toSet}也可以从{@code stream}中收集元素到集合中。
     * </ol>
     *
     * @param <S>
     *            原类型
     * @param <T>
     *            目标类型
     * @param source
     *            目标链表, null则返回一个empty集合
     * @param converter
     *            转换器
     * @return 结果集合
     *
     * @see ListUtil#toSetWithDefaultFactory(List, Function, IntFunction)
     * @see java.util.stream.Stream#collect(java.util.stream.Collector)
     * @see java.util.stream.Collectors#toSet()
     */
    public static <S, T> Set<T> toSet(List<S> source, Function<S, T> converter) {
        if (CollectionUtils.isEmpty(source)) {
            return SetUtil.empty();
        }
        Set<T> targets = SetUtil.newSet(source.size());
        for (S s : source) {
            if (s != null) {
                targets.add(converter.apply(s));
            } else {
                LOG.debug("[ListUtil#toSet] skip a null item");
            }
        }
        return targets;
    }

    /**
     * 将链表中的元素加到结果集合中，并且跳过null元素
     *
     * @param <S>
     *            原类型
     * @param source
     *            目标链表
     * @return 结果集合
     */
    public static <T> Set<T> toSet(List<T> source) {
        return toSet(source, Function.identity());
    }

    /**
     * 将{@code source}转化为一个集合，遇到null时，调用{@code defaultFactory}生成一个默认值加到集合中
     *
     * @param <S>
     *            原类型
     * @param <T>
     *            目标类型
     * @param source
     *            原链表, null则返回一个empty集合
     * @param converter
     *            转换器
     * @param defaultFactory
     *            默认值生成器, 类型为{@code IntFunction}，其中{@code apply}的参数为null元素的下标
     * @return target 结果集合
     *
     * @see ListUtil#toSet(List, Function)
     * @see IterableUtil#filterNonNull(Iterable)
     * @see IntFunction
     */
    public static <S, T> Set<T> toSetWithDefaultFactory(List<S> source, Function<S, T> converter, IntFunction<T> defaultFactory) {
        if (CollectionUtils.isEmpty(source)) {
            return SetUtil.empty();
        }
        Set<T> targets = SetUtil.newSet(source.size());
        int index = 0;
        for (S s : source) { // 这里使用迭代器，不使用下标迭代来访问链表，某些情况下效率更好
            if (s != null) {
                targets.add(converter.apply(s));
            } else {
                targets.add(defaultFactory.apply(index));
            }
            index++;
        }
        return targets;
    }

    /**
     * 将链表中的元素加到结果集合中，遇到null时，调用{@code defaultFactory}生成一个默认值加到集合中
     *
     * @param <T>
     *            原类型
     * @param source
     *            目标链表
     * @param defaultFactory
     *            默认值生成器, 类型为{@code IntFunction}，其中{@code apply}的参数为null元素的下标
     * @return 结果集合
     */
    public static <T> Set<T> toSetWithDefaultFactory(List<T> source, IntFunction<T> defaultFactory) {
        return toSetWithDefaultFactory(source, Function.identity(), defaultFactory);
    }
}
