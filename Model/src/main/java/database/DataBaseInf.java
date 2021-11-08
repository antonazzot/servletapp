package database;
import threadmodel.*;
import users.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
A class that imitation  database
**/

public final class DataBaseInf {
    private DataBaseInf() {
    }
    private static final Map<Integer, UserImpl> studentHashMap = new ConcurrentHashMap<>();
    private static final Map <Integer, UserImpl> trainerHashMap = new ConcurrentHashMap<>();
    private static final Map <Integer, Theams> theamsHashMap = new ConcurrentHashMap<>();
    private static final Map <Integer, UserImpl> adminHashMap = new ConcurrentHashMap<>();
    private static final Map <Integer, Group> groupHashMap = new ConcurrentHashMap<>();

    public static Map<Integer, UserImpl> getStudentHashMap() {
        return studentHashMap;
    }

    public static Map<Integer, UserImpl> getTrainerHashMap() {
        return trainerHashMap;
    }

    public static Map<Integer, Theams> getTheamsHashMap() {
        return theamsHashMap;
    }

    public static Map<Integer, UserImpl> getAdminHashMap() {
        return adminHashMap;
    }

    public static Map<Integer, Group> getGroupHashMap() {
        return groupHashMap;
    }
}
