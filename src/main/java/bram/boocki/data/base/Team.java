package bram.boocki.data.base;

import java.util.ArrayList;
import java.util.List;

import bram.boocki.exceptions.BooCKIException;

public class Team {

	private final String name;
	private List<Game> playedGames;
	
	
	private Team(String name) {
		this.name = name;
		playedGames = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public static class Builder {
		
		String name;
		
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Team build() {
			try {
				if (name == null) {
					throw new BooCKIException("No name given.");
				}
				return new Team(name);
			} catch (BooCKIException e) {
				throw new BooCKIException("Couldn't build Team. " + e.getMessage());
			}
		}
		
	}

	public void addPlayedGame(Game newGame) {
		playedGames.add(newGame);
	}

}
