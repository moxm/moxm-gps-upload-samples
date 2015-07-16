package com.newtouchone.example.apis.otto;

import com.squareup.otto.Bus;

/**
 * Created by Richard on 15/7/16.
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    private BusProvider(){}

    public static Bus getInstance(){
        return BUS;
    }
}
