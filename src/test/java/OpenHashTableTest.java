import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class OpenHashTableTest {
    OpenHashTable hashTable;
    Object[] items;

    @BeforeEach
    public void setup() {
        hashTable = new OpenHashTable();
        items = hashTable.getItems();
        hashTable.put(0);
        hashTable.put(6);
        hashTable.put(12);
        hashTable.put(16);
        hashTable.put(19);
        hashTable.put(32);
    }

    @AfterEach
    public void tearDown() {
        hashTable.clear();
    }

    @Test
    public void testPut() {
        int size = hashTable.getSize();
        int actual0 = (int) items[0];
        int actual12 = (int) items[12];
        int actual16 = (int) items[3];
        LinkedList<Integer> list = (LinkedList<Integer>) items[6];
        int actual6 = list.getLast();
        int actual19 = list.get(1);
        int actual32 = list.getFirst();
        Assertions.assertEquals(0, actual0, "Ошибка в методе put");
        Assertions.assertEquals(6, actual6, "Ошибка в методе put");
        Assertions.assertEquals(12, actual12, "Ошибка в методе put");
        Assertions.assertEquals(16, actual16, "Ошибка в методе put");
        Assertions.assertEquals(19, actual19, "Ошибка в методе put (LinkedList)");
        Assertions.assertEquals(32, actual32, "Ошибка в методе put (LinkedList)");
        Assertions.assertEquals(6, size, "Ошибка в методе put (size)");
    }

    @Test
    public void testGet() {
        Assertions.assertEquals(0, (int) hashTable.get(0), "Ошибка в методе get");
        Assertions.assertEquals(6, (int) hashTable.get(6), "Ошибка в методе get");
        Assertions.assertEquals(12, (int) hashTable.get(12), "Ошибка в методе get");
        Assertions.assertEquals(16, (int) hashTable.get(16), "Ошибка в методе get");
        Assertions.assertEquals(19, (int) hashTable.get(19), "Ошибка в методе get (LinkedList)");
        Assertions.assertEquals(32, (int) hashTable.get(32), "Ошибка в методе get (LinkedList)");
        Object actual = hashTable.get(8);
        Assertions.assertNull(actual, "Ошибка в методе get (not found)");
    }

    @Test
    public void testDeleteSingleItems() {
        boolean result = hashTable.delete(8);
        Assertions.assertFalse(result);
        result = hashTable.delete(0);
        int size = hashTable.getSize();
        Assertions.assertEquals(5, size, "Ошибка в методе delete (size)");
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        Object actual = items[0];
        Assertions.assertNull(actual, "Ошибка в методе delete (not delete item)");
        result = hashTable.delete(12);
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        actual = items[12];
        Assertions.assertNull(actual, "Ошибка в методе delete (not delete item)");
        result = hashTable.delete(16);
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        actual = items[3];
        Assertions.assertNull(actual, "Ошибка в методе delete (not delete item)");
    }


    @Test
    public void testDeleteFromList() {
        boolean result = hashTable.delete(6);
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        LinkedList<Integer> list = (LinkedList<Integer>) items[6];
        result = false;
        for (Integer cur : list) {
            if (cur.equals(6)) {
                result = true;
                break;
            }
        }
        Assertions.assertFalse(result, "Ошибка в методе delete (LinkedList)");
        result = hashTable.delete(32);
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        list = (LinkedList<Integer>) items[6];
        result = false;
        for (Integer cur : list) {
            if (cur.equals(32)) {
                result = true;
                break;
            }
        }
        Assertions.assertFalse(result, "Ошибка в методе delete (LinkedList)");
        result = hashTable.delete(19);
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        Object actual = items[6];
        Assertions.assertNull(actual, "Ошибка в методе delete (LinkedList)");
        int size = hashTable.getSize();
        Assertions.assertEquals(3, size, "Ошибка в методе delete (size)");
    }
}