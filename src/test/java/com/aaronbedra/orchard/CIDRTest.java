package com.aaronbedra.orchard;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CIDRTest {
    @Test
    public void testIPv4Contains() throws InvalidCIDRException {
        CIDR cidr = new CIDR("1.1.1.0/24");
        assertEquals(true, cidr.contains("1.1.1.15"));
    }

    @Test
    public void testIPv4ContainsFalse() throws InvalidCIDRException {
        CIDR cidr = new CIDR("1.1.1.0/24");
        assertEquals(false, cidr.contains("2.1.1.15"));
    }
}
