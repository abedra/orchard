package com.aaronbedra.orchard;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CIDR4 implements CIDR {
    private static final int MASKSHIFT = 32;
    private static final int ADRSHIFT = 8;
    private static final int ADRAND = 0xFF;
    private int baseInt;
    private int baseEndInt;

    public CIDR4(final InetAddress base, final int mask) throws OrchardException {
        this.baseInt = ipv4toint(base);
        this.baseInt &= ~((1 << MASKSHIFT - mask) - 1);
        this.baseEndInt = baseInt + (1 << MASKSHIFT - mask);
    }

    @Override
    public final boolean contains(final String address) throws OrchardException {
        if (address == null) {
            throw new OrchardException("Address cannot be null");
        }

        try {
            InetAddress adr = InetAddress.getByName(address);
            int addressInt = ipv4toint(adr);
            return addressInt >= baseInt && addressInt <= baseEndInt;
        } catch (UnknownHostException e) {
            throw new OrchardException("Invalid IP address");
        }
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
