package objects;

import java.util.HashMap;
import java.util.Map;

public class Objet_Map {
    private Map<Character, Object> objectMap;

    public Objet_Map() {
        objectMap = new HashMap<>();
    }

    public Map<Character, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<Character, Object> newMap) {
        objectMap = newMap;
    }

    public void remove(char objectKey) {
        objectMap.remove(objectKey); // Suppression de l'objet
        System.out.println("Objet retiré du Map");
    }
}
