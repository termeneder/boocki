package bram.boocki.importer.games.eredivisiestats;

import bram.boocki.data.imported.eredivisiestats.EredivisieStats;

public class EredivisieStatsUpdater {

	public static void main(String[] args) {
		new EredivisieStatsUpdater().update();
	}
	
	public void update() {
		EredivisieStatsImporter importer = new EredivisieStatsImporter();
		EredivisieStats data = importer.importData();
		EredivisieStatsSaver saver = new EredivisieStatsSaver();
		saver.save(data, EredivisieStatsConfiguration.xmlLocation);
	}
	
}
