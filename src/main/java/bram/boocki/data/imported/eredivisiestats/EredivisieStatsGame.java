package bram.boocki.data.imported.eredivisiestats;

import javax.xml.bind.annotation.XmlElement;

public class EredivisieStatsGame {

	@XmlElement
	public String season;
	@XmlElement
	public String date;
	@XmlElement
	public String homeTeam;
	@XmlElement
	public String awayTeam;
	@XmlElement
	public String homeScore;
	@XmlElement
	public String awayScore;

	public String toString() {
		return season + " (" + date + ") " + homeTeam + " " + homeScore + " - " + awayScore + " " + awayTeam;
	}
	
}
