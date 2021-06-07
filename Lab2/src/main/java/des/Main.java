package des;

public class Main {

	public static void main(String[] args) {
		
		try {
			String text = "Algorithm DES-ECB";

			String k = "123456789";
			System.out.println("Text:\t\t\t\t" + text);
			byte[] enc = DES.encrypt(text.getBytes(), k.getBytes());
			System.out.println("Text encrypted DES:\t" + new String(enc));

			byte[] dec = DES.decrypt(enc, k.getBytes());
			System.out.println("Text decrypted DES:\t" + new String(dec));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
