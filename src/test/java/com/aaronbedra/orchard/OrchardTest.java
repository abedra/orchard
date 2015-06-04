package com.aaronbedra.orchard;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrchardTest {
    @Test
    public void testIPv4InBlock() throws OrchardAddressException {
        assertEquals(true, Orchard.isAddressInCidr("1.1.1.15", "1.1.1.0/24"));
    }

    @Test
    public void testIPv4NotInBlock() throws OrchardAddressException {
        assertEquals(false, Orchard.isAddressInCidr("1.1.2.15", "1.1.1.0/24"));
    }

    @Test
    public void testIPv6InBlock() throws OrchardAddressException {
        assertEquals(true, Orchard.isAddressInCidr("1fff:0:0a88:85a3:0:0:ac1f:8002",
                                                   "1fff:0:0a88:85a3:0:0:ac1f:8001/24"));
    }

    @Test
    public void testIPv6NotInBlock() throws OrchardAddressException {
        assertEquals(false, Orchard.isAddressInCidr("1fff:0:0a88:85a3:0:0:ac1f:8002",
                                                    "1fff:0:0a88:85a3:0:0:ac1f:8001/128"));
    }

    @Test(expected = OrchardAddressException.class)
    public void testNullAddress() throws OrchardAddressException {
        Orchard.isAddressInCidr(null, "1.1.1.0/24");
    }

    @Test(expected = OrchardAddressException.class)
    public void testNullCIDR() throws OrchardAddressException {
        Orchard.isAddressInCidr("1.1.1.15", null);
    }

    @Test(expected = OrchardAddressException.class)
    public void testCIDRWithNoMask() throws OrchardAddressException {
        Orchard.isAddressInCidr("1.1.1.15", "1.1.1.0");
    }

    @Test(expected = OrchardAddressException.class)
    public void testCIDRWithNaNMask() throws OrchardAddressException {
        Orchard.isAddressInCidr("1.1.1.15", "1.1.1.0/foo");
    }

    @Test(expected = OrchardAddressException.class)
    public void testIPv4CIDRWithNegativeMask() throws OrchardAddressException {
        Orchard.isAddressInCidr("1.1.1.15", "1.1.1.0/-8");
    }

    @Test(expected = OrchardAddressException.class)
    public void testIPv4CIDRWithHighMask() throws OrchardAddressException {
        Orchard.isAddressInCidr("1.1.1.15", "1.1.1.0/33");
    }

    @Test(expected = OrchardAddressException.class)
    public void testIPv6CIDRWithLowMask() throws OrchardAddressException {
        Orchard.isAddressInCidr("1fff:0:0a88:85a3:0:0:ac1f:8002",
                                "1fff:0:0a88:85a3:0:0:ac1f:8001/0");
    }

    @Test(expected = OrchardAddressException.class)
    public void testIPv6CIDRWithHighMask() throws OrchardAddressException {
        Orchard.isAddressInCidr("1fff:0:0a88:85a3:0:0:ac1f:8002",
                                "1fff:0:0a88:85a3:0:0:ac1f:8001/129");
    }
}
