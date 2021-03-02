public class Function {
    public double arctg(double x) {
        double result = 0;

        for (int n = 0; n < 250; n++) {
            result += (Math.pow(-1, n) * Math.pow(x, 2 * n + 1)) / (2 * n + 1);
        }
        return result;
    }
}
