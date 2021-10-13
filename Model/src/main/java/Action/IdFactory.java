package Action;

public class IdFactory {
    private static int generalID = 0;
    public static int idBuilder() {
        generalID++;
        return generalID;
    }
}
