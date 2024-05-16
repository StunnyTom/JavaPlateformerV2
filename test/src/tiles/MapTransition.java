package tiles;

public class MapTransition {
	// Classe auxiliaire pour g�rer les transitions de carte
	    private String mapPath;
	    int startX, startY;
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
