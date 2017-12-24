package cn.myyy.hello.util.security;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/*

 * DES加密，对称加密算法，知道秘钥可以解密密文；

 * 密钥至少为8位字符，56位的密钥以及附加的 8位奇偶校验位，每组的第8位作为奇偶校验位；

 */
public class DESEncryptionUtil {

    private String desKey;

	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}

	public String encrypt(String sourceText) {
		return encrypt(sourceText, desKey);
	}

	public String decrypt(String cipherText) {
		return decrypt(cipherText, desKey);
	}
	
	public static String encrypt(String sourceText,String keyString) {
		if (sourceText == null || sourceText.length() == 0) {
			return null;
		}
		try {
			SecureRandom sr = new SecureRandom();					 // DES算法要求有一个可信任的随机数源

			DESKeySpec dks = new DESKeySpec(keyString.getBytes());	 // 从原始密钥数据创建一个DESKeySpec对象

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);			 // 生产密钥

			Cipher cipher = Cipher.getInstance("DES");				 // Cipher对象实际完成加密操作

			cipher.init(Cipher.ENCRYPT_MODE, key, sr);				 // 使用密钥初始化Cipher对象

			byte[] data = sourceText.getBytes();
			byte[] encryptedData = cipher.doFinal(data);			 // 加密

			String hexString = HexString.bytes2HexStr(encryptedData);// 转成16进制串

			return hexString;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decrypt(String cipherText,String keyString) {
		if (cipherText == null || cipherText.length() == 0) {
			return null;
		}
		try {
			SecureRandom sr = new SecureRandom();  				// DES算法要求有一个可信任的随机数源

			DESKeySpec dks = new DESKeySpec(keyString.getBytes()); 	// 从原始密钥数据创建一个DESKeySpec对象

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");  	// Cipher对象实际完成解密操作

			cipher.init(Cipher.DECRYPT_MODE, key, sr);  	// 使用密钥初始化Cipher对象

			byte[] data = HexString.hex2Byte(cipherText);  	// 将十六进制串转成字节数组

			byte[] decryptedData = cipher.doFinal(data); 	// 解密

			return new String(decryptedData);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]){
		System.out.println(decrypt(encrypt("1231","12345678abcqwe123"), "12345678abcqwe123"));
	}

}