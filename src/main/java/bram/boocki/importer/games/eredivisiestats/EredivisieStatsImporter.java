package bram.boocki.importer.games.eredivisiestats;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import bram.boocki.data.imported.eredivisiestats.EredivisieStats;
import bram.boocki.data.imported.eredivisiestats.EredivisieStatsGame;
import bram.boocki.exceptions.BooCKIException;

@SuppressWarnings("deprecation")
public class EredivisieStatsImporter {


	
	public EredivisieStats importData() {
		String page = readSite();
		List<EredivisieStatsGame> data = extractData(page);
		EredivisieStats stats = new EredivisieStats();
		stats.games = data;
		return stats;
	}

	@SuppressWarnings({ "resource" })
	private String readSite() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(EredivisieStatsConfiguration.eredivisiestatsUrl);

			// add header
			post.setHeader("User-Agent", "Mozilla/5.0");

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("submit", "OK"));
			

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + EredivisieStatsConfiguration.eredivisiestatsUrl);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + 
	                                    response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			return result.toString();
		} catch (Exception e) {
			throw new BooCKIException("Could not read EredivisieStats.nl.", e);
		}
		
	}
	
	private List<EredivisieStatsGame> extractData(String page) {
		List<EredivisieStatsGame> data = new ArrayList<EredivisieStatsGame>();
		String gameTable = extractGameTable(page);
		String[] gameRows = splitGameTable(gameTable);
		for (String row : gameRows) {
			EredivisieStatsGame game = readRow(row); 
			data.add(game);
		}
		return data;
	}





	private String extractGameTable(String page) {
		Pattern p = Pattern.compile("<tr><th>Seizoen</th><th>Datum</th><th>Thuisclub</th><th>Uitclub</th><th>Thuisscore</th><th>Uitscore</th></tr>(.*)</table>");
		Matcher m = p.matcher(page);
		if (m.find()) {
			return m.group(1);
		} else {
			throw new BooCKIException("No game table found on eredivisiestats.nl.");
		}
	}
	
	private String[] splitGameTable(String gameTable) {
		String[] rows = gameTable.split("</tr><tr>");
		for (int i = 0 ; i < rows.length ; i++) {
			if (rows[i].startsWith("<tr>")) {
				rows[i] = rows[i].substring(4, rows[i].length());
			}
			if (rows[i].endsWith("</tr>")) {
				rows[i] = rows[i].substring(0, rows[i].length()-5);
			}
		}
		return rows;
	}
	
	private EredivisieStatsGame readRow(String row) {
		Pattern p = Pattern.compile("<td>([0-9-]+)</td><td>([0-9-]+)</td><td nowrap='nowrap'><img src='[0-9a-zA-Z/.`-]+' alt='[0-9a-zA-Z /.`-]+'/> ([0-9a-zA-Z /.`-]+)</td><td nowrap='nowrap'><img src='[0-9a-zA-Z/.`-]+' alt='[0-9a-zA-Z /.`-]+'/> ([0-9a-zA-Z /.`-]+)</td><td>([0-9]+)</td><td>([0-9]+)</td>");
		Matcher m = p.matcher(row);
		if (m.find()) {
			EredivisieStatsGame game = new EredivisieStatsGame();
			game.season = m.group(1);
			game.date = m.group(2);
			game.homeTeam = m.group(3);
			game.awayTeam = m.group(4);
			game.homeScore = m.group(5);
			game.awayScore = m.group(6);
			return game;
		} else {
			throw new BooCKIException("Could not read row: " + row);
		}
		
	}
}
