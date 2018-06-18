package com.lx.demo;

import lombok.Data;
import java.io.Serializable;

@Data
public class DoOrderRequest implements Serializable {
    private static final long serialVersionUID = -6877876822566354190L;
    private String name;
}
