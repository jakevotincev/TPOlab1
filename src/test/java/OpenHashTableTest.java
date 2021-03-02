import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class OpenHashTableTest {
    OpenHashTable table;
    Field field;
    Object[] items;

    @Before
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

    @After
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
        Assert.assertEquals("Ошибка в методе put", 0, actual0);
        Assert.assertEquals("Ошибка в методе put", 6, actual6);
        Assert.assertEquals("Ошибка в методе put", 12, actual12);
        Assert.assertEquals("Ошибка в методе put", 16, actual16);
        Assert.assertEquals("Ошибка в методе put (LinkedList)", 19, actual19);
        Assert.assertEquals("Ошибка в методе put (LinkedList)", 32, actual32);
        Assert.assertEquals("Ошибка в методе put (size)", 6, size);
    }

    @Test
    public void testGet() {
        Assert.assertEquals("Ошибка в методе get", 0, (int) table.get(0));
        Assert.assertEquals("Ошибка в методе get", 6, (int) table.get(6));
        Assert.assertEquals("Ошибка в методе get", 12, (int) table.get(12));
        Assert.assertEquals("Ошибка в методе get", 16, (int) table.get(16));
        Assert.assertEquals("Ошибка в методе get (LinkedList)", 19, (int) table.get(19));
        Assert.assertEquals("Ошибка в методе get (LinkedList)", 32, (int) table.get(32));
        Object actual = table.get(8);
        Assert.assertNull("Ошибка в методе get (not found)", actual);
    }

    @Test
    public void testDelete() {
        boolean result = table.delete(8);
        Assert.assertFalse(result);
        result = table.delete(0);
        int size = table.getSize();
        Assert.assertEquals("Ошибка в методе delete (size)", 5, size);
        Assert.assertTrue("Ошибка в методе delete (result)", result);
        Object actual = items[0];
        Assert.assertNull("Ошибка в методе delete (not delete item)", actual);
        result = table.delete(12);
        Assert.assertTrue("Ошибка в методе delete (result)", result);
        actual = items[12];
        Assert.assertNull("Ошибка в методе delete (not delete item)", actual);
        result = table.delete(16);
        Assert.assertTrue("Ошибка в методе delete (result)", result);
        actual = items[3];
        Assert.assertNull("Ошибка в методе delete (not delete item)", actual);
        result = table.delete(6);
        Assert.assertTrue("Ошибка в методе delete (result)", result);
        LinkedList<Integer> list = (LinkedList<Integer>) items[6];
        result = false;
        for (Integer cur : list) {
            if (cur.equals(6)) {
                result = true;
                break;
            }
        }
        Assert.assertFalse("Ошибка в методе delete (LinkedList)", result);
        result = table.delete(32);
        Assert.assertTrue("Ошибка в методе delete (result)", result);
        list = (LinkedList<Integer>) items[6];
        result = false;
        for (Integer cur : list) {
            if (cur.equals(32)) {
                result = true;
                break;
            }
        }
        Assert.assertFalse("Ошибка в методе delete (LinkedList)", result);
        result = table.delete(19);
        Assert.assertTrue("Ошибка в методе delete (result)", result);
        actual = items[6];
        Assert.assertNull("Ошибка в методе delete (LinkedList)", actual);
        size = table.getSize();
        Assert.assertEquals("Ошибка в методе delete (size)",0, size);


    }
}
