package com.aaronbedra.orchard;

import java.math.BigInteger;
import java.net.InetAddress;

public class CIDR6 {
    private BigInteger start;
    private BigInteger end;

    public CIDR6(InetAddress base, int mask) throws OrchardAddressException {
        BigInteger baseInt = ipv6toint(base);
        BigInteger bigMask = deriveMask(mask);
        start = baseInt.and(bigMask);
        end = baseInt.add(deriveEnd(mask)).subtract(BigInteger.ONE);
    }

    public boolean contains(InetAddress address) throws OrchardAddressException {
        BigInteger addressInt = ipv6toint(address);
        return addressInt.compareTo(start) >= 0 && addressInt.compareTo(end) <= 0;
    }

    private static BigInteger ipv6toint(InetAddress address) {
        return new BigInteger(address.getAddress());
    }

    private static BigInteger deriveMask(int mask) {
        return BigInteger.ONE.shiftLeft(128 - mask).subtract(BigInteger.ONE).not();
    }

    private static BigInteger deriveEnd(int mask) {
        return BigInteger.ONE.shiftLeft(128 - mask);
    }
}
