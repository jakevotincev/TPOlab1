import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FunctionTest {
    HashMap<Double, Double> trueTestValues = new HashMap<>();
    HashMap<Double, Double> falseTestValues = new HashMap<>();
    Function function = new Function();
    double DELTA = 0.001;

    @Before
    public void setUp() {
        trueTestValues.put(0.0, 0.0);
        trueTestValues.put(Math.PI / 6, Math.sqrt(3) / 3);
        trueTestValues.put(-Math.PI / 6, -Math.sqrt(3) / 3);
        trueTestValues.put(Math.PI / 4, 1.0);
        trueTestValues.put(-Math.PI / 4, -1.0);
        //>1
        falseTestValues.put(Math.PI / 3, Math.sqrt(3));
        falseTestValues.put(-Math.PI / 3, -Math.sqrt(3));
    }

    @Test
    public void test() {
        double actual, expected, value;
        for (Map.Entry<Double, Double> entry : trueTestValues.entrySet()) {
            value = entry.getValue();
            expected = entry.getKey();
            actual = function.arctg(value);
            Assert.assertEquals("ОшибОчка вышла: value " + value, expected, actual, DELTA);
        }
        for (Map.Entry<Double, Double> entry : falseTestValues.entrySet()) {
            value = entry.getValue();
            expected = entry.getKey();
            actual = function.arctg(value);
            Assert.assertNotEquals("ОшибОчка вышла: value " + value, expected, actual, DELTA);
        }
    }

    @After
    public void tearDown() {
        trueTestValues.clear();
    }
}
