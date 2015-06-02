package com.aaronbedra.orchard;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CIDR6 {
    private BigInteger start;
    private BigInteger end;

    public CIDR6(String block) throws InvalidCIDRException {
        String[] parts = block.split("/");
        BigInteger base;
        try {
            base = ipv6toint(InetAddress.getByName(parts[0]));
        } catch (UnknownHostException e) {
            throw new InvalidCIDRException("Invalid Base Address for CIDR Block");
        }
        BigInteger mask = deriveMask(parts[1]);
        start = base.and(mask);
        end = base.add(deriveEnd(parts[1])).subtract(BigInteger.ONE);
    }

    public boolean contains(String address) throws InvalidCIDRException {
        InetAddress adr;
        try {
            adr = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new InvalidCIDRException("Invalid Comparison Address");
        }
        BigInteger convertedAddress = ipv6toint(adr);
        return convertedAddress.compareTo(start) >= 0 && convertedAddress.compareTo(end) <= 0;
    }

    private static BigInteger ipv6toint(InetAddress address) {
        return new BigInteger(address.getAddress());
    }

    private static BigInteger deriveMask(String mask) {
        int m = Integer.parseInt(mask);
        return BigInteger.ONE.shiftLeft(128 - m).subtract(BigInteger.ONE).not();
    }

    private static BigInteger deriveEnd(String mask) {
        int m = Integer.parseInt(mask);
        return BigInteger.ONE.shiftLeft(128 - m);
    }
}
