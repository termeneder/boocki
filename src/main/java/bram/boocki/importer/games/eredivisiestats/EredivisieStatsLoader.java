package bram.boocki.importer.games.eredivisiestats;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import bram.boocki.data.base.Competition;
import bram.boocki.data.base.Game;
import bram.boocki.data.base.Season;
import bram.boocki.data.base.Team;
import bram.boocki.data.imported.eredivisiestats.EredivisieNameMapping;
import bram.boocki.data.imported.eredivisiestats.EredivisieStats;
import bram.boocki.data.imported.eredivisiestats.EredivisieStatsGame;
import bram.boocki.exceptions.BooCKIException;

/**
 * Loads data from eredivisiestats.nl that has been saved 
 */
public class EredivisieStatsLoader {

	public Competition loadCompetition() {
		EredivisieStats stats = loadStats();
		EredivisieNameMapping mapping = loadMapping();
		Competition competition = readStats(stats,mapping);
		return competition;
	}



	private EredivisieStats loadStats() {
		 try {
				
			 File file = new File(EredivisieStatsConfiguration.xmlLocation);
			 JAXBContext jaxbContext = JAXBContext.newInstance(EredivisieStats.class);
	
			 Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 EredivisieStats stats = (EredivisieStats) jaxbUnmarshaller.unmarshal(file);
			 
			 return stats;
		 } catch (JAXBException e) {
			 throw new BooCKIException("Couldn't read EredivisieStats xml.", e);
		 }
	}
	
	private EredivisieNameMapping loadMapping() {
		 try {
			 File file = new File(EredivisieStatsConfiguration.xmlMappingLocation);
			 JAXBContext jaxbContext = JAXBContext.newInstance(EredivisieNameMapping.class);
	
			 Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 EredivisieNameMapping mapping = (EredivisieNameMapping) jaxbUnmarshaller.unmarshal(file);
			 
			 return mapping;
		 } catch (JAXBException e) {
			 throw new BooCKIException("Couldn't read Eredivisie team name mapping xml.", e);
		 }
	}
	
	private Competition readStats(EredivisieStats stats, EredivisieNameMapping mapping) {
		Competition competition = new Competition("Eredivisie");
		Map<String, Team> teams = new HashMap<>();
		Map<String, Season> seasons = new HashMap<>();
		for (EredivisieStatsGame statsGame : stats.games) {
			Game game = readGame(statsGame, mapping, teams, seasons);
			competition.addGame(game);
		}
		return competition;
	}

	private Game readGame(EredivisieStatsGame statsGame, EredivisieNameMapping mapping, Map<String, Team> teams, Map<String, Season> seasons) {
		Team homeTeam = getTeam(statsGame.homeTeam, mapping, teams);
		Team awayTeam = getTeam(statsGame.awayTeam, mapping, teams);
		LocalDate date = LocalDate.parse(statsGame.date);
		Season season = getSeason(statsGame.season, seasons);
		Game.Builder builder = new Game.Builder()
				.withHomeTeam(homeTeam)
				.withAwayTeam(awayTeam)
				.withHomeScore(Integer.parseInt(statsGame.homeScore))
				.withAwayScore(Integer.parseInt(statsGame.awayScore))
				.withDate(date)
				.withSeason(season);
		return builder.build();
	}

	private Team getTeam(String teamName, EredivisieNameMapping mapping, Map<String, Team> teams) {
		String mappedName = mapping.map(teamName);
		if (!teams.containsKey(mappedName)) {
			Team newTeam = new Team.Builder()
					.withName(mappedName)
					.build();
			teams.put(mappedName, newTeam);
		}
		Team team = teams.get(mappedName);
		return team;
	}

	
	private Season getSeason(String seasonName, Map<String, Season> seasons) {
		if (!seasons.containsKey(seasonName)) {
			Season newSeason = new Season.Builder()
					.withName(seasonName)
					.build();
			seasons.put(seasonName, newSeason);
		}
		Season season = seasons.get(seasonName);
		return season;
	}
	
}
