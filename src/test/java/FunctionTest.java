import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class FunctionTest {
    Function function = new Function();
    double DELTA = 0.001;

    @ParameterizedTest
    @CsvFileSource(resources = "/params.csv")
    void testOnTrueValues(double expected, double value) {
        double actual = function.arctg(value);
        Assertions.assertEquals(expected, actual, DELTA, "ОшибОчка вышла: value " + value);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/falseparams.csv")
    void testOnFalseValues(double expected, double value) {
        double actual = function.arctg(value);
        Assertions.assertNotEquals(expected, actual, DELTA, "ОшибОчка вышла: value " + value);
    }

}
