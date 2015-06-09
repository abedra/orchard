# Orchard [![Build Status](https://secure.travis-ci.org/abedra/orchard.png)](http://travis-ci.org/abedra/orchard?branch=master) [![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) 

A CIDR processing library for Java. Supports IPv4 and IPv6 Addresses.

## Installation

```xml
<dependency>
    <groupId>com.aaronbedra</groupId>
    <artifactId>orchard</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage

```java
import com.aaronbedra.orchard.CIDR;

if (CIDR.valueOf("1.1.1.0/24").contains("1.1.1.2")) {
  // do stuff
} else {
  // do other stuff
}
```
