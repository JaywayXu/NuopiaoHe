
public class Main {

    public static void main(String[] args) {
        long count = 0;
        for (int i = 0; i < 1; i++) {
            count += test();
        }
        System.out.println(count);
    }

    public static long test() {

        long startMs=System.currentTimeMillis();


        double x = 0.55;
        for (int i = 0; i < 1000000 ; i++) {
            x = x + x;
            x = x/2;
            x = x*x;
            x = Math.sqrt(x);
            x = Math.log(x);
            x = exp(x);
            x = x/(x + 2);
        }


        long endMs=System.currentTimeMillis();
        System.out.println("Time elapsed:"+ms2DHMS(startMs,endMs));
        return  endMs - startMs;
    }

    public static double exp(double val) {
        final long tmp = (long) (1512775 * val + (1072693248 - 60801));
        return Double.longBitsToDouble(tmp << 32);
    }

    static String ms2DHMS(long startMs, long endMs) {
        String retval = null;
        long secondCount = (endMs - startMs) / 1000;
        String ms = (endMs - startMs) % 1000 + "ms";

        long days = secondCount / (60 * 60 * 24);
        long hours = (secondCount % (60 * 60 * 24)) / (60 * 60);
        long minutes = (secondCount % (60 * 60)) / 60;
        long seconds = secondCount % 60;

        if (days > 0) {
            retval = days + "d" + hours + "h" + minutes + "m" + seconds + "s";
        } else if (hours > 0) {
            retval = hours + "h" + minutes + "m" + seconds + "s";
        } else if (minutes > 0) {
            retval = minutes + "m" + seconds + "s";
        } else if(seconds > 0) {
            retval = seconds + "s";
        }else {
            return ms;
        }

        return retval + ms;
    }
}
