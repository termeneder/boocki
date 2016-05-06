package bram.boocki.data.imported.eredivisiestats;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import bram.boocki.exceptions.BooCKIException;

@XmlRootElement
public class EredivisieNameMapping {

	@XmlElement
	public Map<String, String> nameMap;
	
	public String map(String key) {
		if (nameMap.containsKey(key)) {
			return nameMap.get(key);
		} else {
			throw new BooCKIException("Unknown eredivisie team: " + key + ".");
		}
	}
	
}
