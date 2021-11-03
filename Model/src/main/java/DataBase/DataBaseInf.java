package DataBase;
import ThreadModel.*;
import Users.*;
import java.util.*;
/**
A class that imitation  database
**/

public final class DataBaseInf {
    private DataBaseInf() {
    }
    public static Map<Integer, UserImpl> studentHashMap = new HashMap<>();
    public static Map <Integer, UserImpl> trainerHashMap = new HashMap <>();
    public static Map <Integer, Theams> theamsHashMap = new HashMap <>();
    public static Map <Integer, UserImpl> adminHashMap = new HashMap <>();
    public static Map <Integer, Group> groupHashMap = new HashMap <>();

    }
