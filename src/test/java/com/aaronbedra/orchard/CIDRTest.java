package com.aaronbedra.orchard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CIDRTest {
    @Test
    public void testIPv4InDefaultRoute() throws OrchardException {
        assertEquals(true, CIDR.valueOf("0.0.0.0/0").contains("1.42.1.42"));
    }

    @Test
    public void testIPv4InBlock() throws OrchardException {
        assertEquals(true, CIDR.valueOf("1.1.1.0/1").contains("0.0.0.0"));
        assertEquals(true, CIDR.valueOf("1.1.1.0/1").contains("127.255.255.255"));
    }

    @Test
    public void testIPv4NotInBlock() throws OrchardException {
        assertEquals(false, CIDR.valueOf("1.1.1.0/24").contains("1.1.2.15"));
    }

    @Test
    public void testIPv6InBlock() throws OrchardException {
        assertEquals(true, CIDR.valueOf("1fff:0:0a88:85a3:0:0:ac1f:8001/24")
                .contains("1fff:0:0a88:85a3:0:0:ac1f:8002"));
    }

    @Test
    public void testIPv6NotInBlock() throws OrchardException {
        assertEquals(false, CIDR.valueOf("1fff:0:0a88:85a3:0:0:ac1f:8001/128")
                .contains("1fff:0:0a88:85a3:0:0:ac1f:8002"));
    }

    @Test(expected = OrchardException.class)
    public void testNullAddress() throws OrchardException {
        CIDR.valueOf("1.1.1.0/24").contains(null);
    }

    @Test(expected = OrchardException.class)
    public void testNullCIDR() throws OrchardException {
        CIDR.valueOf(null).contains("1.1.1.15");
    }

    @Test(expected = OrchardException.class)
    public void testCIDRWithNoMask() throws OrchardException {
        CIDR.valueOf("1.1.1.0").contains("1.1.1.15");
    }

    @Test(expected = OrchardException.class)
    public void testCIDRWithNaNMask() throws OrchardException {
        CIDR.valueOf("1.1.1.0/foo").contains("1.1.1.15");
    }

    @Test(expected = OrchardException.class)
    public void testIPv4CIDRWithNegativeMask() throws OrchardException {
        CIDR.valueOf("1.1.1.0/-8").contains("1.1.1.15");
    }

    @Test(expected = OrchardException.class)
    public void testIPv4CIDRWithHighMask() throws OrchardException {
        CIDR.valueOf("1.1.1.0/33").contains("1.1.1.15");
    }

    @Test
    public void testIPv4CIDRCanGetMask() throws OrchardException {
        assertEquals(CIDR.valueOf("1.1.1.0/32").getMask(), 32);
        assertEquals(CIDR.valueOf("1.1.1.0/0").getMask(), 0);
    }

    @Test(expected = OrchardException.class)
    public void testIPv6CIDRWithLowMask() throws OrchardException {
        CIDR.valueOf("1fff:0:0a88:85a3:0:0:ac1f:8001/0")
                .contains("1fff:0:0a88:85a3:0:0:ac1f:8002");
    }

    @Test(expected = OrchardException.class)
    public void testIPv6CIDRWithHighMask() throws OrchardException {
        CIDR.valueOf("1fff:0:0a88:85a3:0:0:ac1f:8001/129")
                .contains("1fff:0:0a88:85a3:0:0:ac1f:8002");
    }

    @Test
    public void testIPv6CIDRCanGetMask() throws OrchardException {
        assertEquals(CIDR.valueOf("1fff:0:0a88:85a3:0:0:ac1f:8001/128").getMask(), 128);
        assertEquals(CIDR.valueOf("1fff:0:0a88:85a3:0:0:ac1f:8001/8").getMask(), 8);
    }
}
