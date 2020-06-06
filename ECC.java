package SM2;

import java.math.BigInteger;

public class ECC {
    public Dot ZERO = new Dot(BigInteger.ZERO,BigInteger.ZERO);
    private Dot rootDot;

    //判断点是否是椭圆曲线上的点
    public static boolean isOnTheLine2(BigInteger a,BigInteger b,BigInteger x,BigInteger y,BigInteger p){
        if (!p.testBit(0) ){//p首先是一个奇数
            return false;
        }
        // Z = x^3 + ax + b
        BigInteger Z = x.pow(3).add((a.multiply(x))).add(b).mod(p);
        BigInteger yy = y.multiply(y).mod(p);
        return Z.equals(yy);
    }
    public static void main(String[] args) {
        BigInteger a = new BigInteger("1");
        BigInteger b = new BigInteger("6");
        BigInteger p = new BigInteger("11");
        Dot G = new Dot(BigInteger.valueOf(2),BigInteger.valueOf(7));//椭圆曲线上的一个
        long d = 7;//私钥
        ECC sm2 = new ECC(a, b, p,new Dot(BigInteger.valueOf(2),BigInteger.valueOf(7)));
        Dot Y = sm2.multiply(d,G);//公钥
        System.out.println("ECC公钥为："+Y.toString());
        Dot M = new Dot(new BigInteger("9"),new BigInteger("1"));//明文
        System.out.println("原文M:"+M);
        long k = 6;//加密随机数
        Dot c1 = sm2.multiply(k,G);
        Dot c2 =  sm2.add(M,sm2.multiply(k,Y));
        System.out.println("C1:"+c1);
        System.out.println("C2:"+c2);
        ///解密过程 M' = c2 - xc1
        Dot M2 = sm2.subtract(c2,sm2.multiply(d,c1));
        System.out.println("解密结果M':"+M2);
    }
    //result = x time D1
    public Dot multiply(long x,Dot d1){
        Dot result = ZERO;
        for (long i = 0;i < x; i++){
            result = add(result,d1);
        }
        return result;
    }
    // result = d1 - d2
    public Dot subtract(Dot d1,Dot d2){
        return add(d1,d2.reverse());
    }
    public Dot add(Dot d1,Dot d2){
        if (d1.equals(ZERO)) return d2;
        if (d2.equals(ZERO)) return d1;
        if (d1.isReverse(d2)) return ZERO;
        BigInteger x1 = d1.x;
        BigInteger y1 = d1.y;
        BigInteger x2 = d2.x;
        BigInteger y2 = d2.y;
        BigInteger aa;
        if (!d1.equals(d2)){// (y2 -y1)/(x2-x1)
            aa = y2.subtract(y1).multiply(x2.subtract(x1).modPow(p.subtract(BigInteger.valueOf(2)),p)).mod(p);
        }else {
            aa = ((x1.pow(2)).multiply(BigInteger.valueOf(3)).add(a)).multiply(y1.multiply(BigInteger.valueOf(2)).modPow(p.subtract(BigInteger.valueOf(2)),p)).mod(p);
        }
        BigInteger x3 = aa.pow(2).subtract(x1).subtract(x2).mod(p);
        BigInteger y3 = aa.multiply(x1.subtract(x3)).subtract(y1).mod(p);
        return new Dot(x3,y3);
    }
    public boolean isOnTheLine(BigInteger x,BigInteger y){
        if (!p.testBit(0) ){//p首先是一个奇数
            return false;
        }
        // Z = x^3 + ax + b
        BigInteger Z = x.pow(3).add((a.multiply(x))).add(b).mod(p);
        BigInteger yy = y.multiply(y).mod(p);
        return Z.equals(yy);
    }
    private
    BigInteger a = new BigInteger("1");
    private
    BigInteger b = new BigInteger("1");
    private BigInteger p = new BigInteger("23");

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }

    public BigInteger getP() {
        return p;
    }

    public ECC(BigInteger a, BigInteger b, BigInteger p, Dot rootDot) {
        this.rootDot = rootDot;
        this.a = a;
        this.b = b;
        this.p = p;
    }
}
