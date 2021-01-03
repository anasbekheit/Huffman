package RandomTests;

public class PrimitiveConversion {
    public static void main(String[] args){
        double i = 9999999999999.999999999999;
        System.out.println(i);
        long j = (long) i;
        System.out.println(j);
        double k = (double) j;
        System.out.println(k);
    }
}
