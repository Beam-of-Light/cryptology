package NybergRueppel;

import java.math.BigInteger;
import java.util.Random;

public class NybergRueppelAlgo {
    /**
     * Q - big prime <br>
     * P - big prime and (P - 1) mod Q = 0 <br>
     * G - generate H < P and G = H^((P-1)/Q) mod P. If G = 1 => generate another H
     */
    private static int Q = 101;
    private static int P = 607;
    private static int G = 601;

    /**
     * x - secret key on (0; Q) <br>
     * Y = G^x (mod P) - open key <br>
     */
    private static int x = 3;
    private static int Y = pow(G, x, P) % P; // 391

    private static final Random random = new Random();


    private static int pow(int value, int powValue, int m) {
        int result = 1;
        for (int i = 0; i < powValue; i++) {
            result = (result * value) % m;
        }
        return result;
    }

    public static int invGcd(int _a, int _b) {
        BigInteger a = new BigInteger(String.valueOf(_a));
        BigInteger b = new BigInteger(String.valueOf(_b));

        BigInteger prevX = BigInteger.ONE;
        BigInteger x = BigInteger.ZERO;
        BigInteger prevY = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;

        while (b.compareTo(BigInteger.ZERO) != 0){
            BigInteger quotient = a.divide(b),
                    xCopy = new BigInteger(x.toByteArray()),
                    yCopy = new BigInteger(y.toByteArray());

            x = prevX.subtract(quotient.multiply(x));
            prevX = xCopy;

            y = prevY.subtract(quotient.multiply(y));
            prevY = yCopy;

            BigInteger prevA = new BigInteger(a.toByteArray());
            a = b;
            b = prevA.mod(b);
        }

        int result = prevX.intValue();
        if (result < 0) {
            result += _b;
        }
        return result;
    }


    /**
     * Result ∈ [1, P - 1]
     * @param m message
     * @return f(m) = m + (2^4 * m)
     */
    private static int f(int m) {
        return m + (m << 4);
    }

    /**
     * Inverse function to f
     * @param f result of f(m)
     * @return m = f(m) / (1 + 2^4)
     */
    private static int inv_f(int f) {
        if (f % 17 == 0) {
            return f / 17;
        }
        return -1;
    }

    /**
     * 1. R = G^k (mod P) - session key, where k - random (0; Q) <br>
     * 2. E = f(m) * R (mod P) <br>
     * 3. S = x * E + k (mod Q) <br>
     * <p>
     * (E, S) - signature
     *
     * @param m message
     * @return signature
     */
    public static Signature sign(int m) {
        int k = random.nextInt(Q - 1) + 1;
        System.out.println("k: " + k);

        // int k = 45;
        int R = pow(G, k, P) % P;
        int E = (f(m) * R) % P;
        int S = (x * E + k) % Q;

        return new Signature(E, S);
    }


    /**
     * U1 = (G^S * Y^-E) mod P <br>
     * U2 = (E * U1^-1) mod P <br>
     * <p>
     * m = f^-1(U2)
     * @return if is signature message m, else -1
     */
    public static int checkSignature(Signature signature) {
        int invYE = invGcd(pow(Y, signature.getE(), P), P);
        int U1 = (pow(G, signature.getS(), P) * invYE) % P;

        int invU1 = invGcd(U1, P);
        int U2 = (signature.getE() * invU1) % P;

        return inv_f(U2);
    }
}
