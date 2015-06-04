package com.aaronbedra.orchard;

import java.math.BigInteger;
import java.net.InetAddress;

public class CIDR6 {
    private static final int MASKSHIFT = 128;
    private BigInteger start;
    private BigInteger end;

    public CIDR6(final InetAddress base, final int mask) throws OrchardAddressException {
        BigInteger baseInt = ipv6toint(base);
        BigInteger bigMask = deriveMask(mask);
        start = baseInt.and(bigMask);
        end = baseInt.add(deriveEnd(mask)).subtract(BigInteger.ONE);
    }

    public final boolean contains(final InetAddress address) throws OrchardAddressException {
        BigInteger addressInt = ipv6toint(address);
        return addressInt.compareTo(start) >= 0 && addressInt.compareTo(end) <= 0;
    }

    private static BigInteger ipv6toint(final InetAddress address) {
        return new BigInteger(address.getAddress());
    }

    private static BigInteger deriveMask(final int mask) {
        return BigInteger.ONE.shiftLeft(MASKSHIFT - mask).subtract(BigInteger.ONE).not();
    }

    private static BigInteger deriveEnd(final int mask) {
        return BigInteger.ONE.shiftLeft(MASKSHIFT - mask);
    }
}
