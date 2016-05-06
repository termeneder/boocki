package bram.boocki.data.base;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Competition {

	private final String name;
	private List<Game> games;
	private SortedMap<String, Season> seasonMap;
	private SortedMap<String, Team> teamMap;
	
	public Competition(String name) {
		this.name = name;
		games = new ArrayList<>();
		seasonMap = new TreeMap<>();
		teamMap = new TreeMap<>();
	}
	
	public void addGame(Game game) {
		games.add(game);
		if (!seasonMap.containsKey(game.getSeason().getName())) {
			seasonMap.put(game.getSeason().getName(), game.getSeason());
		}
		if (!teamMap.containsKey(game.getHomeTeam().getName())) {
			teamMap.put(game.getHomeTeam().getName(), game.getHomeTeam());
		}
		if (!teamMap.containsKey(game.getAwayTeam().getName())) {
			teamMap.put(game.getAwayTeam().getName(), game.getAwayTeam());
		}
	}
	
	public Iterable<Game> getGames() {
		return games;
	}
	
	public int getAmountOfGames() {
		return games.size();
	}
	
	public Iterable<Season> getSeasons() {
		return seasonMap.values();
	}
	
	public Iterable<Team> getTeams() {
		return teamMap.values();
	}
	
	public String toString() {
		return name;
	}



}
