package com.aaronbedra.orchard;

public class InvalidCIDRException extends Exception {
    public InvalidCIDRException(String message) {
        super(message);
    }
}
