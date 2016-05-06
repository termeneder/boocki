package bram.boocki.data.imported.eredivisiestats;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EredivisieStats {

	@XmlElement(name = "game")
	public List<EredivisieStatsGame> games;
	
}
