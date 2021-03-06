package com.labs.introtoprogramming.lab3.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import org.junit.Test;

public class HashTableTests {

  @Test
  public void testPut() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();

    assertEquals(0, myHashTable.getSize());
    myHashTable.put("a", 1);
    assertEquals(1, myHashTable.getSize());
    myHashTable.put("b", 2);
    assertEquals(2, myHashTable.getSize());

    assertEquals(2, myHashTable.get("b").get(0).intValue());
    assertEquals(1, myHashTable.get("a").get(0).intValue());
    assertEquals(2, myHashTable.getSize());
  }

  @Test
  public void testPutSameKeys() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    myHashTable.put("a", 1);
    myHashTable.put("a", 2);
    myHashTable.put("a", 3);
    assertEquals(3, myHashTable.getSize());
    assertTrue(myHashTable.get("a").contains(1));
    assertTrue(myHashTable.get("a").contains(2));
    assertTrue(myHashTable.get("a").contains(3));
  }

  @Test
  public void testGetNonExistingKey() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    assertEquals(0, myHashTable.getSize());
    assertEquals(0, myHashTable.get("foobar").size());
  }

  @Test
  public void testGetNonExistingKeyByExistingHash() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>(2);
    assertEquals(0, myHashTable.getSize());
    myHashTable.put("a", 1);
    myHashTable.put("b", 2);
    myHashTable.put("c", 3);
    assertEquals(3, myHashTable.getSize());
    assertEquals(0, myHashTable.get("foobar").size());
  }

  @Test
  public void testGetOrDefault() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    assertEquals(0, myHashTable.getSize());
    myHashTable.put("a", 1);
    myHashTable.put("b", 2);
    myHashTable.put("c", 3);
    assertEquals(3, myHashTable.getSize());
    assertEquals(42, myHashTable.getOrDefault("foobar", Collections.singletonList(42)).get(0).intValue());
  }

  @Test
  public void testRemoveKey() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    assertEquals(0, myHashTable.getSize());
    myHashTable.put("a", 1);
    myHashTable.put("b", 2);
    myHashTable.put("c", 3);
    assertEquals(3, myHashTable.getSize());
    myHashTable.remove("b");
    assertEquals(2, myHashTable.getSize());
    assertFalse(myHashTable.containsKey("b"));
  }

  @Test
  public void testContainsKey() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    assertEquals(0, myHashTable.getSize());
    myHashTable.put("a", 1);
    assertTrue(myHashTable.containsKey("a"));
    assertFalse(myHashTable.containsKey("b"));
  }
  
  @Test
  public void testRemovingNonexistentKey() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    assertEquals(0, myHashTable.getSize());
    myHashTable.put("a", 1);
    myHashTable.put("b", 2);
    myHashTable.put("c", 3);
    assertEquals(3, myHashTable.getSize());
    myHashTable.remove("d");
    assertEquals(3, myHashTable.getSize());
  }

  @Test
  public void testIsEmpty() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    assertTrue(myHashTable.isEmpty());
    assertEquals(0, myHashTable.getSize());
    myHashTable.put("a", 1);
    assertFalse(myHashTable.isEmpty());
    myHashTable.put("b", 2);
    myHashTable.put("c", 3);
    assertEquals(3, myHashTable.getSize());
  }

  @Test
  public void testClear() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>();
    assertEquals(0, myHashTable.getSize());
    myHashTable.put("a", 1);
    myHashTable.put("b", 2);
    myHashTable.put("c", 3);
    assertEquals(3, myHashTable.getSize());
    myHashTable.clear();
    assertEquals(0, myHashTable.getSize());
    assertFalse(myHashTable.containsKey("a"));
  }

  @Test
  public void testResize() {
    MyHashTable<String, Integer> myHashTable = new MyHashTable<>(1);
    assertEquals(1, myHashTable.capacity);
    myHashTable.put("A", 1);
    assertEquals(1, myHashTable.capacity);
    myHashTable.put("B", 2);
    assertEquals(2, myHashTable.capacity);
    myHashTable.put("C", 3);
    assertEquals(4, myHashTable.capacity);
  }
}
