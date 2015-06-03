package com.aaronbedra.orchard;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Orchard {
    public static boolean isAddressInCidr(final String address, final String cidr) throws OrchardAddressException {
        String[] cidrParts = cidr.split("/");
        int mask = Integer.parseInt(cidrParts[1]);
        InetAddress adr;
        InetAddress cidrBase;
        try {
            adr = InetAddress.getByName(address);
            cidrBase = InetAddress.getByName(cidrParts[0]);
        } catch (UnknownHostException e) {
            throw new OrchardAddressException("Invalid comparison or CIDR base address");
        }
        if (adr instanceof Inet4Address) {
            CIDR c = new CIDR(cidrBase, mask);
            return c.contains(adr);
        } else if (adr instanceof Inet6Address) {
            CIDR6 c = new CIDR6(cidrBase, mask);
            return c.contains(adr);
        } else {
            throw new OrchardAddressException("Not a valid IPv4 or IPv6 address");
        }
    }
}
