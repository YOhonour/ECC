package SM2;

import java.math.BigInteger;

public class Dot {
    BigInteger x;
    BigInteger y;

    public boolean isReverse(Dot dot){ // (x,y)的逆元为(x,-y)
        return x.equals(dot.x) & y.equals(dot.y.multiply(BigInteger.valueOf(-1)));
    }
    public Dot(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Dot dot) {
        return x.equals(dot.x) &&
                y.equals(dot.y);
    }
    public Dot reverse(){
        return new Dot(this.x,this.y.multiply(BigInteger.valueOf(-1)));
    }

    @Override
    public String toString() {
        return "Dot{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
