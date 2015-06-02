package com.aaronbedra.orchard;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CIDR6Test {
    @Test
    public void testIPv6Contains() throws InvalidCIDRException {
        CIDR6 cidr = new CIDR6("1fff:0:0a88:85a3:0:0:ac1f:8001/24");
        assertEquals(true, cidr.contains("1fff:0:0a88:85a3:0:0:ac1f:8002"));
    }

    @Test
    public void testIPv6ContainsFalse() throws InvalidCIDRException {
        CIDR6 cidr = new CIDR6("1fff:0:0a88:85a3:0:0:ac1f:8001/128");
        assertEquals(false, cidr.contains("1fff:0:0a88:85a3:0:0:ac1f:8002"));
    }

}
