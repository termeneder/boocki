package bram.boocki.data.base;

import java.time.LocalDate;

import bram.boocki.exceptions.BooCKIException;

public class Game {
	
	private final Team homeTeam;
	private final Team awayTeam;
	private final int homeScore;
	private final int awayScore;
	private final LocalDate date;
	private final Season season;
	
	private Game(Team homeTeam, Team awayTeam, int homeScore, int awayScore, LocalDate date, Season season) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		this.date = date;
		this.season = season;
	}
	
	public Team getHomeTeam() {
		return homeTeam;
	}
	
	public Team getAwayTeam() {
		return awayTeam;
	}

	public int getHomeScore() {
		return homeScore;
	}
	
	
	public int getAwayScore() {
		return awayScore;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public Season getSeason() {
		return season;
	}
	
	public String toString() {
		return "Season " + season + " (" + date + "): " + homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
	}
	
	public static class Builder {
		private Team homeTeam;
		private Team awayTeam;
		private Integer homeScore;
		private Integer awayScore;
		private LocalDate date;
		private Season season;
		
		public Builder withHomeTeam(Team team) {
			this.homeTeam = team;
			return this;
		}
		
		
		public Builder withAwayTeam(Team team) {
			this.awayTeam = team;
			return this;
		}
		
		
		public Builder withHomeScore(Integer score) {
			this.homeScore = score;
			return this;
		}
		
		
		public Builder withAwayScore(Integer score) {
			this.awayScore = score;
			return this;
		}
		
		
		public Builder withDate(LocalDate date) {
			this.date = date;
			return this;
		}
		
		public Builder withSeason(Season season) {
			this.season = season;
			return this;
		}
		
		public Game build() {
			try {
				if (homeTeam == null) {
					throw new BooCKIException("No home team given.");
				}
				if (awayTeam == null) {
					throw new BooCKIException("No away team given.");
				}
				if (homeScore == null) {
					throw new BooCKIException("No home score given.");
				}
				if (awayScore == null) {
					throw new BooCKIException("No away score given.");
				}
				if (date == null) {
					throw new BooCKIException("No date given.");
				}
				if (season == null) {
					throw new BooCKIException("No season given.");
				}
				Game newGame = new Game(homeTeam, awayTeam, homeScore, awayScore, date, season);
				homeTeam.addPlayedGame(newGame);
				awayTeam.addPlayedGame(newGame);
				season.addGame(newGame);
				return newGame;
			} catch (BooCKIException e) {
				throw new BooCKIException("Couldn't build Game. " + e.getMessage());
			}
		}
	}
	
}
