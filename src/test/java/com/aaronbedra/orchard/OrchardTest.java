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
}
