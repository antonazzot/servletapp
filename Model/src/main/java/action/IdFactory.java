package action;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class factory individual id for every
 * essence in database
 **/
public final class IdFactory {
    private IdFactory() {
        // to prevent entity creation
    }

    public static final AtomicInteger generalID = new AtomicInteger(0);

    public static synchronized int idBuilder() {
        return generalID.incrementAndGet();
    }
}
