package com.socket9.tsl.Utils;

import com.socket9.tsl.Events.Bus.StartApiEvent;
import com.squareup.otto.Bus;

/**
 * Created by Euro on 10/16/2015 AD.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }

    public static void post(Object object){
        BUS.post(new StartApiEvent());
        BUS.post(object);
    }
}
