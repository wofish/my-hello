package cn.myyy.hello.util.decimal;

import java.math.BigDecimal;

/**
 * 乘法运算工具类
 */
public class LoanCalculateMultiplyUtil {
    /**
     * 进行乘法运算
     *
     * @param p1
     * @param p2
     * @return
     */
    public static BigDecimal multiply(BigDecimal p1, BigDecimal p2) {
        return p1.multiply(p2);
    }

    /**
     * 进行乘法运算
     *
     * @param p1
     * @param p2
     * @return
     */
    public static BigDecimal multiply(String p1, String p2) {
        return multiply(new BigDecimal(p1), new BigDecimal(p2));
    }

    /**
     * 重载乘法
     *
     * @param args
     * @return
     */
    public static BigDecimal multiply(String... args) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 0; i < args.length; i++) {
            result = multiply(result, new BigDecimal(args[i]));
        }
        return result;
    }

    /**
     * 重载乘法
     *
     * @param args
     * @return
     */
    public static BigDecimal multiply(BigDecimal... args) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 0; i < args.length; i++) {
            result = multiply(result, args[i]);
        }
        return result;
    }
}
