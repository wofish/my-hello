package cn.myyy.hello.util.security;

public class HexString {

    private static final String[] HEX = new String[] { "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static final String[] HEXALL =  { "00", "01", "02", "03", "04",
			"05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1a",
			"1b", "1c", "1d", "1e", "1f", "20", "21", "22", "23", "24", "25",
			"26", "27", "28", "29", "2a", "2b", "2c", "2d", "2e", "2f", "30",
			"31", "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b",
			"3c", "3d", "3e", "3f", "40", "41", "42", "43", "44", "45", "46",
			"47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f", "50", "51",
			"52", "53", "54", "55", "56", "57", "58", "59", "5a", "5b", "5c",
			"5d", "5e", "5f", "60", "61", "62", "63", "64", "65", "66", "67",
			"68", "69", "6a", "6b", "6c", "6d", "6e", "6f", "70", "71", "72",
			"73", "74", "75", "76", "77", "78", "79", "7a", "7b", "7c", "7d",
			"7e", "7f", "80", "81", "82", "83", "84", "85", "86", "87", "88",
			"89", "8a", "8b", "8c", "8d", "8e", "8f", "90", "91", "92", "93",
			"94", "95", "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e",
			"9f", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9",
			"aa", "ab", "ac", "ad", "ae", "af", "b0", "b1", "b2", "b3", "b4",
			"b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd", "be", "bf",
			"c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ca",
			"cb", "cc", "cd", "ce", "cf", "d0", "d1", "d2", "d3", "d4", "d5",
			"d6", "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df", "e0",
			"e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb",
			"ec", "ed", "ee", "ef", "f0", "f1", "f2", "f3", "f4", "f5", "f6",
			"f7", "f8", "f9", "fa", "fb", "fc", "fd", "fe", "ff" };
	
	/*

	 * 十六进制字符串转换为字节数组

	 */
	public static byte[] hex2Byte(String paramString) {
		int i = paramString.length() / 2;
		byte[] arrayOfByte = new byte[i];
		for (int j = 0; j < i; ++j) {
			int k = getHexValue(paramString.charAt(j * 2));
			int l = getHexValue(paramString.charAt(j * 2 + 1));
			arrayOfByte[j] = (byte) (k << 4 | l);
		}
		return arrayOfByte;
	}
	/*

	 * 得到相应十六进制字母的值，比如说a=10,b=11,c=12,d=13,e=14,f=15

	 */
	public static int getHexValue(char paramChar) {
		if ((paramChar >= '0') && (paramChar <= '9'))
			return (paramChar - '0');
		if ((paramChar >= 'a') && (paramChar <= 'f'))
			return (paramChar - 'a' + 10);
		if ((paramChar >= 'A') && (paramChar <= 'F'))
			return (paramChar - 'A' + 10);
		throw new RuntimeException("invalid_char");
	}
	/*

	 * 字节数组转换为十六进制字符串

	 */
	public static String byte2Hex(byte[] paramArrayOfByte) {
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramArrayOfByte.length; ++i) {
			String str = HEXALL[(paramArrayOfByte[i] & 0xFF)];
			localStringBuffer.append(str);
		}
		return new String(localStringBuffer);
	}
	
	/*

	 * 字节数组转换为十六进制字符串，效果与byte2Hex一样，随便选哪一个方法

	 */
	public static String bytes2HexStr(byte[] bytes) {
		StringBuffer buffer = new StringBuffer(bytes.length * 2);
		for (byte b : bytes) {
			buffer.append(byte2HexStr(b));
		}
		return buffer.toString();
	}

	public static String byte2HexStr(byte b) {
		return HEX[(b & 0xf0) >> 4] + HEX[b & 0x0f];
	}
}