package cn.myyy.hello.util.string;

public class HelloStringUtil {

    /**
     * 手机号格式化
     * 13391843775
     * 133****3775
     * @param phoneNo
     * @return
     */
    public static String formatPhoneNo(String phoneNo) {
        return phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    /**
     * 身份证号格式化
     * 345121198809022431
     * 3451****2431
     * @param idNo
     * @return
     */
    public static String formatIdNo(String idNo) {
        return idNo.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1****$2");
    }

    public static void main(String args[]) {
        System.out.println(formatIdNo("345121198809022431"));
    }
 }
