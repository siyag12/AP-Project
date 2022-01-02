package com.example.project_test;

import java.io.Serializable;

public class Helmet implements Serializable {
    Weapon w1, w2;
    private static final long serialVersionUID = 10L;

    Helmet() {
        w1 = null;
        w2 = null;
    }
}
