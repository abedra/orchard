# Orchard [![Build Status](https://secure.travis-ci.org/abedra/orchard.png)](http://travis-ci.org/abedra/orchard?branch=master) [![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) 

A CIDR processing library for Java. Supports IPv4 and IPv6 Addresses.

## Installation

TODO: Add this after maven central release

## Usage

The `Orchard` class has just one method, `isAddressInCidr`, that takes an address to compare and a CIDR block to compare it against. For example:

```java
import com.aaronbedra.orchard.Orchard;

if (Orchard.isAddressInCidr("1.1.1.20", "1.1.1.0/24") {
  // do stuff
} else {
  // do other stuff
}
```
