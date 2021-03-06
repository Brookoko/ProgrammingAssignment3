package com.labs.introtoprogramming.lab3.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Hash Table implementation.
 *
 * @param <K> the type of keys in this hash table.
 * @param <V> the type of values in this hash table.
 *
 * @author Nikita Volobuev
 * @author Bohdan Fedorchenko
 * @see MyHashTableInterface
 */
@SuppressWarnings("RV_ABSOLUTE_VALUE_OF_HASHCODE")
public class MyHashTable<K, V> implements MyHashTableInterface<K, V> {

  private static final int DEFAULT_CAPACITY = 100;
  private static final int SCALE_FACTOR = 2;
  private static final double LOAD_FACTOR_TO_RESIZE = 0.8;
  private static final double LOAD_FACTOR_TO_DECREASE = 0.2;

  private class MyHashTableElement<T, E> {
    T key;
    E value;

    private MyHashTableElement(T key, E value) {
      this.key = key;
      this.value = value;
    }
  }

  private MyLinkedList<MyHashTableElement<K, V>>[] arr;
  private int size = 0;
  int capacity;

  /** Creates hash table with specified capacity. */
  public MyHashTable(int capacity) {
    // noinspection unchecked
    this.capacity = capacity;
    this.arr = (MyLinkedList<MyHashTableElement<K, V>>[]) new MyLinkedList[capacity];
  }

  /** Creates hash table with default capacity. */
  public MyHashTable() {
    this(DEFAULT_CAPACITY);
  }

  @Override
  public void put(K key, V value) {
    if (size >= capacity * LOAD_FACTOR_TO_RESIZE) {
      resize(capacity * SCALE_FACTOR);
    }

    int pos = Math.abs(key.hashCode() % arr.length);

    if (arr[pos] == null) {
      arr[pos] = new MyLinkedList<>();
    }

    size++;

    arr[pos].add(new MyHashTableElement<>(key, value));
  }

  @Override
  public List<V> get(K key) {
    int pos = Math.abs(key.hashCode() % arr.length);

    if (arr[pos] == null) {
      return Collections.emptyList();
    }

    List<V> result = new ArrayList<>();

    for (MyHashTableElement<K, V> element : arr[pos]) {
      if (element.key.equals(key)) {
        result.add(element.value);
      }
    }

    return result;
  }

  @Override
  public boolean containsKey(K key) {
    int pos = Math.abs(key.hashCode() % arr.length);

    if (arr[pos] == null) {
      return false;
    }

    for (MyHashTableElement<K, V> element : arr[pos]) {
      if (element.key.equals(key)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public List<V> getOrDefault(K key, List<V> defaultValue) {
    List<V> values = get(key);
    if (values.size() == 0) {
      return defaultValue;
    }
    return values;
  }

  @Override
  public void remove(K key) {
    if (size > DEFAULT_CAPACITY && size <= capacity * LOAD_FACTOR_TO_DECREASE) {
      resize(capacity / (2 * SCALE_FACTOR));
    }
    int pos = Math.abs(key.hashCode() % arr.length);

    if (arr[pos] == null) {
      return;
    }

    int indexOfKey = -1;
    for (int i = 0; i < arr[pos].getSize(); i++) {
      if (arr[pos].get(i).key.equals(key)) {
        indexOfKey = i;
      }
    }

    if (indexOfKey != -1) {
      arr[pos].removeElementByIndex(indexOfKey);
      size--;
    }
  }

  @Override
  public void clear() {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] != null) {
        arr[i].clear();
      }
    }
    size = 0;
  }

  @Override
  public boolean isEmpty() {
    return getSize() == 0;
  }

  @Override
  public int getSize() {
    return size;
  }

  private void resize(int capacity) {
    MyHashTable<K, V> temp = new MyHashTable<>(capacity);
    for (MyLinkedList<MyHashTableElement<K, V>> list : arr) {
      if (list != null) {
        for (MyHashTableElement<K, V> el : list) {
          temp.put(el.key, el.value);
        }
      }
    }

    arr = temp.arr;
    this.capacity = capacity;
  }
}
