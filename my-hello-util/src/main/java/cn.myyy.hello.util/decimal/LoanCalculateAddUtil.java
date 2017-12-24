package cn.myyy.hello.util.decimal;

import java.math.BigDecimal;

/**
 * 贷款加法工具类
 */
public class LoanCalculateAddUtil {

    /**
     * 进行加法运算
     *
     * @param p1
     * @param p2
     * @return
     */
    public static BigDecimal add(BigDecimal p1, BigDecimal p2) {
        return p1.add(p2);
    }

    /**
     * 进行加法运算
     *
     * @param p1
     * @param p2
     * @return
     */
    public static BigDecimal add(String p1, String p2) {
        return add(new BigDecimal(p1), new BigDecimal(p2));
    }

    /**
     * 重载加法
     *
     * @param args
     * @return
     */
    public static BigDecimal add(String... args) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < args.length; i++) {
            result = add(result, new BigDecimal(args[i]));
        }
        return result;
    }

    /**
     * 进行加法运算
     *
     * @param args
     * @return
     */
    public static BigDecimal add(BigDecimal... args) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                args[i] = BigDecimal.ZERO;
            }
            result = add(result, args[i]);
        }
        return result;
    }
}
