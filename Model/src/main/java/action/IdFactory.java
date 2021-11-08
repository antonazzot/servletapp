package action;

import java.util.concurrent.atomic.AtomicInteger;

/**
This class factory individual id for every
essence in database
**/
public final class IdFactory {
    private IdFactory() {
    }
    private static volatile int generalID = 0;
    public static synchronized int  idBuilder() {
        AtomicInteger id =  new AtomicInteger(generalID++);
        return id.get();
    }
}
