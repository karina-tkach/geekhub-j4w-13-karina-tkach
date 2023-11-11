package org.geekhub.hw4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;


public class CollectionExpander implements Expander {
    @Override
    public double getMinValue(Collection<? extends Number> collection) {
        double minValue = Double.MAX_VALUE;
        for (Number element : collection) {
            double number = element.doubleValue();
            if (number < minValue) {
                minValue = number;
            }
        }
        return minValue;
    }

    @Override
    public double getMaxValue(Collection<? extends Number> collection) {
        double maxValue = Double.MIN_VALUE;
        for (Number element : collection) {
            double number = element.doubleValue();
            if (number > maxValue) {
                maxValue = number;
            }
        }
        return maxValue;
    }

    @Override
    public double getSum(Collection<? extends Number> collection) {
        double sum = 0.0;
        for (Number number : collection) {
            sum += number.doubleValue();
        }
        return sum;
    }

    @Override
    public String join(Collection<?> collection, char delimiter) {
        StringBuilder resultedString = new StringBuilder();
        boolean isFirst = true;
        for (Object element : collection) {
            if (!isFirst) {
                resultedString.append(delimiter);
            } else {
                isFirst = false;
            }
            resultedString.append(element);
        }
        return resultedString.toString();
    }

    @Override
    public List<Double> reversed(List<? extends Number> collection) {
        List<Double> reversedList = new ArrayList<>();
        for (int i = collection.size() - 1; i >= 0; i--) {
            Number number = collection.get(i);
            reversedList.add(number.doubleValue());
        }
        return reversedList;
    }

    @Override
    public List<List<Object>> chunked(Collection<?> collection, int amount) {
        List<List<Object>> chunks = new ArrayList<>();
        Object[] elements = collection.toArray();
        for (int i = 0; i < amount; i++) {
            int index = i;
            List<Object> chunk = new ArrayList<>();
            while (index < collection.size()) {
                chunk.add(elements[index]);
                index += amount;
            }
            chunks.add(chunk);
        }
        return chunks;
    }

    @Override
    public List<?> dropElements(List<?> list, Object criteria) {
        List<?> newList = new ArrayList<>(list);
        if (criteria instanceof Integer index) {
            newList.remove(index.intValue());
        } else {
            int index = 0;
            while (index < newList.size()) {
                Object element = newList.get(index);
                if (element.equals(criteria)) {
                    newList.remove(element);
                } else {
                    index++;
                }
            }
        }
        return newList;
    }

    @Override
    public <T> List<T> getClassList(T t) {
        List<T> classList = new ArrayList<>();
        classList.add(t);
        return classList;
    }

    @Override
    public <T> List<T> removeDuplicatesAndNull(List<T> collection) {
        List<T> result = new ArrayList<>();
        Set<T> uniqueElements = new HashSet<>();
        for (T element : collection) {
            if (element != null && uniqueElements.add(element)) {
                result.add(element);
            }
        }
        return result;
    }

    @Override
    public <T> Map<T, Collection<T>> grouping(Collection<T> collection) {
        Map<T, Collection<T>> resultMap = new HashMap<>();
        for (T element : collection) {
            resultMap.putIfAbsent(element, new ArrayList<>());
            resultMap.get(element).add(element);
        }
        return resultMap;
    }

    @Override
    public <T, U> Map<T, U> merge(Map<T, U> map1, Map<T, U> map2) {
        Map<T, U> mergedMap = new HashMap<>(map1);
        mergedMap.putAll(map2);
        return mergedMap;
    }

    @Override
    public <T, U> Map<T, U> applyForNull(Map<T, U> map, U defaultValue) {
        Map<T, U> result = new HashMap<>(map);
        for (Map.Entry<T, U> entry : map.entrySet()) {
            U value = entry.getValue();
            if (value == null) {
                result.put(entry.getKey(), defaultValue);
            }
        }
        return result;
    }

    @Override
    public <T> Collection<T> collectingList(Map<T, T> map1, Map<T, T> map2) {
        Collection<T> result = new HashSet<>();
        for (T key : map1.keySet()) {
            if (map2.containsValue(key)) {
                result.add(key);
            }
        }
        for (T key : map2.keySet()) {
            if (map1.containsValue(key)) {
                result.add(key);
            }
        }
        return result;
    }
}
