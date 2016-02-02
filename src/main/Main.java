/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Starbuck;
import logic.DataPersistenceLayer;
import logic.NS;
import logic.Starbucks;
import logic.Tweet;
import nl.pvanassen.ns.error.NsApiException;
import nl.pvanassen.ns.model.storingen.Storing;

/**
 * @author martijn
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws NsApiException
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws NsApiException, IOException, SQLException {
		final long timeInterval = 2500;
		NS ns = new NS();
		Starbucks sb = new Starbucks();
		Tweet tweet = new Tweet();
		DataPersistenceLayer db = new DataPersistenceLayer();

		Runnable runnable = new Runnable() {

			public void run() {
				while (true) {
					try {
						ArrayList<Storing> storingen = ns.cron(ns.getApi());
						if (storingen != null) {
							for (Storing stor : storingen) {
								String[] location = stor.getTraject().split("-");
								Starbuck starbuck = sb.getStarbucks(ns.getStation(location[0]).getLat(),
										ns.getStation(location[0]).getLon());
								if (!db.doesIdExist(stor.getId())) {
									String tweetstring = stor.getTraject() + ". Dichtstbijzijnde #starbucks bij "
											+ starbuck.getNaam() + " " + starbuck.getGoogleMapsLink();
									System.out.println(tweetstring);
									tweet.postTweet(tweetstring);
									db.saveNewStoring(stor.getId(), tweetstring);
								} else {
									System.out.println("Already posted tweet");
								}
							}
						} else {
							System.out.println("no new storingen");
						}
						Thread.sleep(timeInterval);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}
}
