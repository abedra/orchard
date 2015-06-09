package com.aaronbedra.orchard;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CIDR6 implements CIDR {
    private static final int MASKSHIFT = 128;
    private BigInteger start;
    private BigInteger end;

    public CIDR6(final InetAddress base, final int mask) throws OrchardException {
        BigInteger baseInt = ipv6toint(base);
        BigInteger bigMask = deriveMask(mask);
        start = baseInt.and(bigMask);
        end = baseInt.add(deriveEnd(mask)).subtract(BigInteger.ONE);
    }

    @Override
    public final boolean contains(final String address) throws OrchardException {
        if (address == null) {
            throw new OrchardException("Address cannot be null");
        }

        try {
            InetAddress adr = InetAddress.getByName(address);
            BigInteger addressInt = ipv6toint(adr);
            return addressInt.compareTo(start) >= 0 && addressInt.compareTo(end) <= 0;
        } catch (UnknownHostException e) {
            throw new OrchardException("Invalid IP Address");
        }
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
