import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class OpenHashTableTest {
    OpenHashTable table;
    Field field;
    Object[] items;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        table = new OpenHashTable();
        field = table.getClass().getDeclaredField("items");
        field.setAccessible(true);
        items = (Object[]) field.get(table);
        table.put(0);
        table.put(6);
        table.put(12);
        table.put(16);
        table.put(19);
        table.put(32);
    }

    @AfterEach
    public void tearDown() {
        table.clear();
    }

    @Test
    public void testPut() {
        int size = table.getSize();
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
        Assertions.assertEquals(0, (int) table.get(0), "Ошибка в методе get");
        Assertions.assertEquals(6, (int) table.get(6), "Ошибка в методе get");
        Assertions.assertEquals(12, (int) table.get(12), "Ошибка в методе get");
        Assertions.assertEquals(16, (int) table.get(16), "Ошибка в методе get");
        Assertions.assertEquals(19, (int) table.get(19), "Ошибка в методе get (LinkedList)");
        Assertions.assertEquals(32, (int) table.get(32), "Ошибка в методе get (LinkedList)");
        Object actual = table.get(8);
        Assertions.assertNull(actual, "Ошибка в методе get (not found)");
    }

    @Test
    public void testDeleteSingleItems() {
        boolean result = table.delete(8);
        Assertions.assertFalse(result);
        result = table.delete(0);
        int size = table.getSize();
        Assertions.assertEquals(5, size, "Ошибка в методе delete (size)");
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        Object actual = items[0];
        Assertions.assertNull(actual, "Ошибка в методе delete (not delete item)");
        result = table.delete(12);
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        actual = items[12];
        Assertions.assertNull(actual, "Ошибка в методе delete (not delete item)");
        result = table.delete(16);
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        actual = items[3];
        Assertions.assertNull(actual, "Ошибка в методе delete (not delete item)");
    }


    @Test
    public void testDeleteFromList() {
        boolean result = table.delete(6);
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
        result = table.delete(32);
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
        result = table.delete(19);
        Assertions.assertTrue(result, "Ошибка в методе delete (result)");
        Object actual = items[6];
        Assertions.assertNull(actual, "Ошибка в методе delete (LinkedList)");
        int size = table.getSize();
        Assertions.assertEquals(3, size, "Ошибка в методе delete (size)");
    }
}