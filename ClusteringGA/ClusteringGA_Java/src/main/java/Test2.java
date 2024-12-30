import java.io.IOException;
import java.util.*;

public class Test2 {
    public static void main(String[] args) throws IOException {
        long[][] dp = new long[6][11];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = i + 1;
        }
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = i + 1;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
    }
}
