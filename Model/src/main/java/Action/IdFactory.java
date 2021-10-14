package Action;
/*
A class that factory individual id for every
essence in database
*/

public final class IdFactory {
    private IdFactory() {
    }
    private static int generalID = 0;
    public static int idBuilder() {
        generalID++;
        return generalID;
    }
}
