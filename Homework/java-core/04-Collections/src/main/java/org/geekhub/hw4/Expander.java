package org.geekhub.hw4;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Provides methods to perform various operations on different collections.
 */
public interface Expander {

    /**
     * Returns the minimum value from a collection of numbers, or max value of type double if collection is empty.
     *
     * @param collection collection consisting of numbers.
     * @return the minimum value from the collection as a double. If the collection is empty, returns Double.MAX_VALUE.
     */
    double getMinValue(Collection<? extends Number> collection);

    /**
     * Returns the maximum value from a collection of numbers or min value of type double if collection is empty.
     *
     * @param collection collection consisting of numbers.
     * @return the maximum value from the collection as a double. If the collection is empty, returns Double.MIN_VALUE.
     */
    double getMaxValue(Collection<? extends Number> collection);

    /**
     * Returns the sum of all numbers in a collection, or zero, if collection is empty.
     *
     * @param collection collection consisting of numbers.
     * @return the sum of all numbers in the collection as a double. If the collection is empty, returns 0.0.
     */
    double getSum(Collection<? extends Number> collection);

    /**
     * Combines the elements of a collection into a single string, separated by the provided delimiter.
     *
     * @param collection the collection of elements to be joined.
     * @param delimiter  the delimiter used for separating the elements.
     * @return a string that is the result of joining the elements of the collection.
     */
    String join(Collection<?> collection, char delimiter);

    /**
     * Returns a new list with reversed order of elements from provided list.
     *
     * @param collection the list of numbers to be reversed.
     * @return a new list with the elements of type double in reverse order.
     */
    List<Double> reversed(List<? extends Number> collection);

    /**
     * Splits a collection into a list of smaller lists, where the size of each sublist is determined by the amount parameter.
     * Each sublist have the same number of elements, with a maximum difference in size of 1.
     *
     * @param collection the collection to be split.
     * @param amount the size of each sublist.
     * @return a list of lists, where each sublist contains a portion of the original collection.
     */
    List<List<Object>> chunked(Collection<?> collection, int amount);

    /**
     * Removes elements from a list based on a given criterion.
     * If the criterion is an int, only one element at the specified index will be removed.
     * If the criterion is an object, all occurrences of the object will be removed from the list.
     *
     * @param list the list from which elements will be removed.
     * @param criteria the criterion used to determine which elements to remove.
     * @return a new list without the removed elements.
     */
    @SuppressWarnings("java:S1452")
    List<?> dropElements(List<?> list, Object criteria);

    /**
     * Returns a parameterized collection with the type of the input instance and with the passed object inside.
     *
     * @param t the instance of the class. It can be of any type.
     * @return a collection with the passed object inside.
     */
    <T> List<T> getClassList(T t);

    /**
     * Removes all duplicates and null values from the given collection while preserving the order.
     *
     * @param collection the collection from which duplicates and null values need to be removed.
     * @return a new list without duplicates and null values.
     */
    <T> List<T> removeDuplicatesAndNull(List<T> collection);

    /**
     * Groups all items separately and returns a map where the key itself is the value and the value is the collection of all duplicate keys.
     *
     * @param collection the collection of items to be grouped.
     * @return a map where the key is the value itself and the value is the collection of all duplicate keys.
     */
    <T> Map<T, Collection<T>> grouping(Collection<T> collection);

    /**
     * Merges two maps into one.
     *
     * @param map1 The first map to be merged.
     * @param map2 The second map to be merged.
     * @return Returns a new map that is a merge of the two input maps.
     */
    <T, U> Map<T, U> merge(Map<T, U> map1, Map<T, U> map2);

    /**
     * Sets default value for each entry where value is null.
     *
     * @param map the map with elements.
     * @param defaultValue the value to replace null with.
     * @return a new map that has replaced null values.
     */
    <T, U> Map<T, U> applyForNull(Map<T, U> map, U defaultValue);

    /**
     * Returns a collection of elements where each element is key in first or second map.
     * <br>
     * Requirements:
     * 1. If element is the key in the first, then the second map should have this value.
     * 2. If element is the key in second then the first map should have this value.
     *
     * @param map1 first map with elements.
     * @param map2 second map with elements.
     * @return a collection of elements where each element meets specified requirements.
     */
    <T> Collection<T> collectingList(Map<T, T> map1, Map<T, T> map2);
}
