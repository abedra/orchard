package com.aaronbedra.orchard;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public interface CIDR {
    int IP4LOW = 0;
    int IP4HIGH = 32;
    int IP6LOW = 8;
    int IP6HIGH = 128;

    static CIDR valueOf(String block) throws OrchardException {
        if (block == null) {
            throw new OrchardException("CIDR cannot be null");
        }
        String[] cidrParts = parseBlock(block);
        int mask = parseMask(cidrParts[1]);
        InetAddress cidrBase;

        try {
            cidrBase = InetAddress.getByName(cidrParts[0]);
        } catch (UnknownHostException e) {
            throw new OrchardException("Invalid comparison or CIDR base address");
        }

        if (cidrBase instanceof Inet4Address) {
            if (mask < IP4LOW || mask > IP4HIGH) {
                throw new OrchardException("IPv4 mask must be between 0 and 32");
            }
            return new CIDR4(cidrBase, mask);
        } else if (cidrBase instanceof Inet6Address) {
            if (mask < IP6LOW || mask > IP6HIGH) {
                throw new OrchardException("IPv6 mask must be between 8 and 128");
            }

            return new CIDR6(cidrBase, mask);
        } else {
            throw new OrchardException("Not a valid IPv4 or IPv6 address");
        }
    }

    static String[] parseBlock(final String block) throws OrchardException {
        String[] cidrParts = block.split("/");
        if (cidrParts.length != 2) {
            throw new OrchardException("Invalid CIDR block");
        }
        return cidrParts;
    }

    static int parseMask(final String mask) throws OrchardException {
        int m;
        try {
            m = Integer.parseInt(mask);
        } catch (NumberFormatException e) {
            throw new OrchardException("CIDR mask must be an integer");
        }
        return m;
    }

    boolean contains(String address) throws OrchardException;

    int getMask();
}
