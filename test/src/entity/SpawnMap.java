package entity;

import java.util.HashMap;
import java.util.Map;

import test.GamePanel;

public class SpawnMap {
	
	GamePanel gp;
    public Map<Character, Entity> map;
    public int mapSpawnnum[][];
    
    public SpawnMap(GamePanel gp) {
        this.gp = gp;
        map = new HashMap<>();
        mapSpawnnum = new int[gp.maxWorldCol][gp.maxWorldRow];
    }
}
