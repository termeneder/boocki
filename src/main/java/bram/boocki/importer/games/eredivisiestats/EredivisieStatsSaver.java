package bram.boocki.importer.games.eredivisiestats;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import bram.boocki.data.imported.eredivisiestats.EredivisieStats;

public class EredivisieStatsSaver {

	public void save(EredivisieStats stats, String targetLocation) {
		try {

			File file = new File(targetLocation);
			JAXBContext jaxbContext = JAXBContext.newInstance(EredivisieStats.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(stats, file);
			jaxbMarshaller.marshal(stats, System.out);

		      } catch (JAXBException e) {
			e.printStackTrace();
		      }

	}
	
}
