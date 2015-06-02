package com.aaronbedra.orchard;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CIDR {
    private int mask;
    private InetAddress convertedBase;
    private int baseInt;
    private int baseEndInt;

    public CIDR(final String block) throws InvalidCIDRException {
        String[] parts = block.split("/");
        this.mask = Integer.parseInt(parts[1]);
        try {
            this.convertedBase = InetAddress.getByName(parts[0]);
        } catch (UnknownHostException e) {
            throw new InvalidCIDRException("Invalid Base Address for CIDR Block");
        }
        this.baseInt = addressToInt(convertedBase);
        this.baseInt &= ~((1 << 32 - this.mask) - 1);
        this.baseEndInt = baseInt + (1 << 32 - this.mask);
    }

    public boolean contains(String address) throws InvalidCIDRException {
        InetAddress convertedAddress = null;
        try {
            convertedAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new InvalidCIDRException("Invalid Comparison Address");
        }
        int possible = addressToInt(convertedAddress);
        return possible >= baseInt && possible <= baseEndInt;
    }

    private int addressToInt(InetAddress address) {
        return ipv4toint(address);
    }

    private static int ipv4toint(InetAddress address) {
        int net = 0;
        for (byte adr : address.getAddress()) {
            net <<= 8;
            net |= adr & 0xFF;
        }
        return net;
    }
}
