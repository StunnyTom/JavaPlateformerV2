package tiles;

public class MapTransition {
	// Classe auxiliaire pour gérer les transitions de carte
	    private String mapPath;
	    public int startX;
		public int startY;
		private String spawnMapPath;

	    public MapTransition(String mapPath, String spawnMapPath, int startX, int startY) {
	        this.setMapPath(mapPath);
	        this.setSpawnMapPath(spawnMapPath);
	        this.startX = startX;
	        this.startY = startY;
	    }

		public String getSpawnMapPath() {
			return spawnMapPath;
		}

		public void setSpawnMapPath(String spawnMapPath) {
			this.spawnMapPath = spawnMapPath;
		}

		public String getMapPath() {
			return mapPath;
		}

		public void setMapPath(String mapPath) {
			this.mapPath = mapPath;
		}

}
