package bram.boocki.data.base;

import java.util.ArrayList;
import java.util.List;

import bram.boocki.exceptions.BooCKIException;

public class Season {

	private final String name;
	private List<Game> games;
	
	private Season(String name) {
		this.name = name;
		games = new ArrayList<>();
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
		
		public Season build() {
			try {
				if (name == null) {
					throw new BooCKIException("No name given.");
				}
				return new Season(name);
			} catch (BooCKIException e) {
				throw new BooCKIException("Couldn't build Season. " + e.getMessage());
			}
		}
		
	}

	public void addGame(Game newGame) {
		games.add(newGame);
	}

}
