package Repository;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum RepositoryTypes {
    MEMORY ("memory"),
    POSTGRES ("postgres");
    private static Map<String, RepositoryTypes> value2Enum = initValue2Enum();
    private static Map<RepositoryTypes, String> enum2Value = initEnum2Value();

    private final String type;

    RepositoryTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    private static Map<String,RepositoryTypes> initValue2Enum() {
        RepositoryTypes[] repositoryTypes = RepositoryTypes.values();
        Map<String,RepositoryTypes> result = new HashMap<>(repositoryTypes.length);
        for (RepositoryTypes value : repositoryTypes) {
            result.put(value.getType(), value);

        }
        return Collections.unmodifiableMap(result);
    }

    private static Map<RepositoryTypes,String> initEnum2Value() {
        Map<RepositoryTypes,String> result = new EnumMap<RepositoryTypes, String>(RepositoryTypes.class);
        for (RepositoryTypes value : RepositoryTypes.values()) {
            result.put(value, value.getType());

        }
        return Collections.unmodifiableMap(result);
    }

    public static RepositoryTypes getTypeByStr(String str) {
        return value2Enum.get(str);
    }

    public static String getTypeByType (RepositoryTypes type) {
        return enum2Value.get(type);
    }

}
