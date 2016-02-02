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
package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.pvanassen.ns.*;
import nl.pvanassen.ns.error.NsApiException;
import nl.pvanassen.ns.model.stations.Namen;
import nl.pvanassen.ns.model.stations.Station;
import nl.pvanassen.ns.model.stations.StationsHandle;
import nl.pvanassen.ns.model.storingen.Storing;
import nl.pvanassen.ns.model.storingen.Storingen;

/**
 * @author martijn
 *
 */
public class NS {
	private final String USERNAME = "m.balsmeer@gmail.com";
	private final String PASSWORD = "0Dpk9jgBj_F7ZUOYzeE8qGNNGeIlCVJfkUrtcAj7GD-JpKEvkt0DsQ";
	private NsApi api;

	public NS() {
		api = new NsApi(USERNAME, PASSWORD);
	}

	private Storingen getStoringen(NsApi api) throws NsApiException, IOException {
		ApiRequest<Storingen> request = RequestBuilder.getActueleStoringen();
		return api.getApiResponse(request);
	}

	public ArrayList<Storing> cron(NsApi api) throws NsApiException, IOException {
		Storingen storingen = getStoringen(api);
		List<Storing> ongStoringen = storingen.getOngeplandeStoringen();
		if (!ongStoringen.isEmpty()) {
			ArrayList<Storing> tmp = new ArrayList<>();
			for (Storing storing : ongStoringen) {
				tmp.add(storing);
			}
			return tmp;
		} else {
			return null;
		}
	}

	public Station getStation(String naam) throws NsApiException, IOException {
		ApiRequest<List<Station>> request = RequestBuilder.getStations();
		List<Station> stations = api.getApiResponse(request);
		for (Station stat : stations) {
			if (stat.getNamen().getKort().equals(naam) || stat.getNamen().getMiddel().equals(naam)
					|| stat.getNamen().getLang().equals(naam)) {
				return stat;

			}
		}
		return null;

	}

	public NsApi getApi() {
		return api;
	}
}
