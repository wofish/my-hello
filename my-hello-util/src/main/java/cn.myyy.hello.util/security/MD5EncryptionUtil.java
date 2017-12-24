package cn.myyy.hello.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*

 * MD5，数字摘要算法第五版，MD5会产生一个128位的消息摘要/散列值；

 */
public class MD5EncryptionUtil {

	public static String encrypt(String sourceText){
		byte[] text = sourceText.getBytes();
		String encrpt = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
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
		System.out.println(new MD5EncryptionUtil().encrypt("1023"));
	}

}