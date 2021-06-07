package NybergRueppel;

public class Main {
    public static void main(String[] args) {
        int message = 30;

        System.out.println("Nyberg-Rueppel message: " + message);
        Signature signature = NybergRueppelAlgo.sign(message);

        System.out.println("E: " + signature.getE());
        System.out.println("S: " + signature.getS());

        int result = NybergRueppelAlgo.checkSignature(signature);
        System.out.println("Nyberg-Rueppel result: " + result);
    }
}
