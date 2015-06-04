package com.aaronbedra.orchard;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class Orchard {
    private static final int IP4LOW = 0;
    private static final int IP4HIGH = 32;
    private static final int IP6LOW = 8;
    private static final int IP6HIGH = 128;

    private Orchard() { }

    public static boolean isAddressInCidr(final String address, final String cidr) throws OrchardAddressException {
        if (address == null || cidr == null) {
            throw new OrchardAddressException("Comparision and CIDR base address must be provided");
        }

        String[] cidrParts = parseBlock(cidr);
        int mask = parseMask(cidrParts[1]);
        InetAddress adr;
        InetAddress cidrBase;

        try {
            adr = InetAddress.getByName(address);
            cidrBase = InetAddress.getByName(cidrParts[0]);
        } catch (UnknownHostException e) {
            throw new OrchardAddressException("Invalid comparison or CIDR base address");
        }

        if (adr instanceof Inet4Address) {
            if (mask < IP4LOW || mask > IP4HIGH) {
                throw new OrchardAddressException("IPv4 mask must be between 0 and 32");
            }

            CIDR c = new CIDR(cidrBase, mask);
            return c.contains(adr);
        } else if (adr instanceof Inet6Address) {
            if (mask < IP6LOW || mask > IP6HIGH) {
                throw new OrchardAddressException("IPv6 mask must be between 8 and 128");
            }

            CIDR6 c = new CIDR6(cidrBase, mask);
            return c.contains(adr);
        } else {
            throw new OrchardAddressException("Not a valid IPv4 or IPv6 address");
        }
    }

    private static String[] parseBlock(final String block) throws OrchardAddressException {
        String[] cidrParts = block.split("/");
        if (cidrParts.length != 2) {
            throw new OrchardAddressException("Invalid CIDR block");
        }
        return cidrParts;
    }

    private static int parseMask(final String mask) throws OrchardAddressException {
        int m;
        try {
            m = Integer.parseInt(mask);
        } catch (NumberFormatException e) {
            throw new OrchardAddressException("CIDR mask must be an integer");
        }
        return m;
    }
}
