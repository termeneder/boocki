package bram.boocki.sandbox;

import bram.boocki.data.base.Competition;
import bram.boocki.importer.games.eredivisiestats.EredivisieStatsLoader;

public class RunSandbox {

	public static void main(String[] args) {
		EredivisieStatsLoader loader = new EredivisieStatsLoader();
		Competition eredivisie = loader.loadCompetition();
		System.out.println(eredivisie.getAmountOfGames());
	}

}
