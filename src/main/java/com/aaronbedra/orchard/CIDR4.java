package com.aaronbedra.orchard;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CIDR4 implements CIDR {
    private static final int MASKSHIFT = 32;
    private static final int ADRSHIFT = 8;
    private static final int ADRAND = 0xFF;
    private InetAddress address;
    private long baseLong;
    private long baseEndLong;
    private int mask;

    public CIDR4(final InetAddress address, final int mask) {
        this.address = address;
        this.mask = mask;
        this.baseLong = ipv4tolong(address);
        this.baseLong &= -(1L << MASKSHIFT - mask);
        this.baseEndLong = baseLong + (1L << MASKSHIFT - mask);
    }

    @Override
    public final boolean contains(final String address) throws OrchardException {
        if (address == null) {
            throw new OrchardException("Address cannot be null");
        }

        try {
            InetAddress adr = InetAddress.getByName(address);
            long addressInt = ipv4tolong(adr);
            return addressInt >= baseLong && addressInt <= baseEndLong;
        } catch (UnknownHostException e) {
            throw new OrchardException("Invalid IP address");
        }
    }

    @Override
    public final InetAddress getAddress() {
        return address;
    }

    @Override
    public final int getMask() {
        return mask;
    }

    private static long ipv4tolong(final InetAddress address) {
        int net = 0;
        for (byte adr : address.getAddress()) {
            net <<= ADRSHIFT;
            net |= adr & ADRAND;
        }
        return net;
    }

}
