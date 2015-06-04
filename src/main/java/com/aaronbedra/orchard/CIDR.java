package com.aaronbedra.orchard;

import java.net.InetAddress;

public class CIDR {
    private static final int MASKSHIFT = 32;
    private static final int ADRSHIFT = 8;
    private static final int ADRAND = 0xFF;
    private int baseInt;
    private int baseEndInt;

    public CIDR(final InetAddress base, final int mask) throws OrchardAddressException {
        this.baseInt = ipv4toint(base);
        this.baseInt &= ~((1 << MASKSHIFT - mask) - 1);
        this.baseEndInt = baseInt + (1 << MASKSHIFT - mask);
    }

    public final boolean contains(final InetAddress address) throws OrchardAddressException {
        int addressInt = ipv4toint(address);
        return addressInt >= baseInt && addressInt <= baseEndInt;
    }

    private static int ipv4toint(final InetAddress address) {
        int net = 0;
        for (byte adr : address.getAddress()) {
            net <<= ADRSHIFT;
            net |= adr & ADRAND;
        }
        return net;
    }
}
