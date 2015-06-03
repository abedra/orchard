package com.aaronbedra.orchard;

import java.net.InetAddress;

public class CIDR {
    private int baseInt;
    private int baseEndInt;

    public CIDR(final InetAddress base, final int mask) throws OrchardAddressException {
        this.baseInt = ipv4toint(base);
        this.baseInt &= ~((1 << 32 - mask) - 1);
        this.baseEndInt = baseInt + (1 << 32 - mask);
    }

    public boolean contains(InetAddress address) throws OrchardAddressException {
        int addressInt = ipv4toint(address);
        return addressInt >= baseInt && addressInt <= baseEndInt;
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
