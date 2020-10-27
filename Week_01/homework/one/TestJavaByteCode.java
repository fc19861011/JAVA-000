package homework.one;

public class TestJavaByteCode {

    private final String f = "final";

    public static String b = "static";

    public void main(String[] args) {
        int n1 = 4;
        int n2 = 5;
        int n3 = n1 + n2;
        System.out.println(n3);
        long l1 = 3L;
        long l2 = 7L;
        long l3 = l2 - l1;
        System.out.println(l3);
        double d1 = 2.2D;
        double d2 = 1.1D;
        double d3 = d2 / d1;
        System.out.println(d3);
        this.doLoop();
        System.out.println(f + b);
    }

    public void doLoop() {
        int num = 1;
        for (int i = 0; i < 100; i++) {
            num = num + i;
        }
        System.out.println(num);
    }
}
