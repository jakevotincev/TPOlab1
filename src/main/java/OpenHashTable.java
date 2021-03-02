import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OpenHashTable {

    private final int INITIAL_SIZE = 13;
    private Object[] items = new Object[INITIAL_SIZE];
    private int size = 0;

    public OpenHashTable() {
    }

    public void put(int item) {
        int hashCode = getHashCode(item);
        Object current = items[hashCode];
        if (current == null) items[hashCode] = item;
        else if (current.getClass() == Integer.class) {
            LinkedList<Integer> itemsList = new LinkedList<>(Arrays.asList(item, (Integer) current));
            items[hashCode] = itemsList;
        } else {
            List<Integer> list = doSafeCast(current, Integer.class);
            ((LinkedList<Integer>) list).addFirst(item);
            items[hashCode] = list;
        }
        size++;
    }

    public Integer get(int item) {
        int hashCode = getHashCode(item);
        Object current = items[hashCode];
        if (current != null)
            if (current.getClass() == Integer.class) return (Integer) current;
            else {
                List<Integer> list = doSafeCast(current, Integer.class);
                for (Integer cur : list) {
                    if (cur.equals(item)) return cur;
                }
            }
        return null;
    }

    public boolean delete(int item) {
        int hashCode = getHashCode(item);
        Object current = items[hashCode];
        if (current != null)
            if (current.getClass() == Integer.class) {
                if (current.equals(item)) {
                    items[hashCode] = null;
                    size--;
                    return true;
                }
            } else {
                List<Integer> list = doSafeCast(current, Integer.class);
                boolean result = list.remove((Object) item);
                if (list.size() == 0) items[hashCode] = null;
                else items[hashCode] = list;
                size--;
                return result;
            }
        return false;
    }

    public int getSize() {
        return size;
    }

    private int getHashCode(int item) {
        return item % INITIAL_SIZE;
    }

    public void clear() {
        items = null;
        size = 0;
    }

    private <T> List<T> doSafeCast(Object listObject, Class<T> type) {
        List<T> result = new LinkedList<>();

        if (listObject instanceof List) {
            List<?> list = (List<?>) listObject;

            for (Object obj : list) {
                if (type.isInstance(obj)) {
                    result.add(type.cast(obj));
                }
            }
        }

        return result;
    }

}
