package cn.myyy.hello.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*

 * SHA1，数字摘要算法，SHA1会产生一个160位的消息摘要；

 * 该算法的思想是接收一段明文，然后以一种不可逆的方式将它转换成一段（通常更小）密文；

 * 也可以简单的理解为取一串输入码（称为预映射或信息），并把它们转化为长度较短、位数固定的输出序列即散列值（也称为信息摘要或信息认证代码）的过程；

 * 散列是信息的提炼，通常其长度要比信息小得多，且为一个固定长度；

 * 加密性强的散列一定是不可逆的，这就意味着通过散列结果，无法推出任何部分的原始信息；

 * 任何输入信息的变化，哪怕仅一位，都将导致散列结果的明显变化；

 * 散列还应该是防冲突的，即找不出具有相同散列结果的两条信息；

 */
public class SHA1EncryptionUtil {

	public static String encrypt(String sourceText){
		byte[] text = sourceText.getBytes();
		String encrpt = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA1");
			md.update(text);
			byte[] digest = md.digest();
			encrpt = HexString.bytes2HexStr(digest);
			return encrpt;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static String decrypt(String cipherText) {
		return cipherText;
	}
	
	public static void main(String args[]){
		System.out.println(new SHA1EncryptionUtil().encrypt("12345620141030FD0B753AA470FE880D2E167E087F4CE5"));
	}

}