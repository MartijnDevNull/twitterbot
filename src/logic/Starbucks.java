package logic;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import domain.Starbuck;

public class Starbucks {

	public Starbuck getStarbucks(Double lat, Double lon)
			throws SAXException, IOException, ParserConfigurationException {
		return getContent("https://openapi.starbucks.com/location/v1/stores?&radius=10&limit=1&brandCode=SBUX&latLng="
				+ lat + "%2C" + lon + "&apikey=7b35m595vccu6spuuzu2rjh4");
	}

	/**
	 * Get Starbucks location from their super documented API
	 * 
	 * @param _url
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private Starbuck getContent(String _url) throws SAXException, IOException, ParserConfigurationException {
		URL url = new URL(_url);
		URLConnection conn = url.openConnection();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(conn.getInputStream());

		NodeList nList = doc.getElementsByTagName("store");

		Node nNode = nList.item(0);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;
			return new Starbuck(eElement.getElementsByTagName("name").item(0).getTextContent(),
					eElement.getElementsByTagName("streetAddressLine1").item(0).getTextContent(),
					eElement.getElementsByTagName("city").item(0).getTextContent(),
					eElement.getElementsByTagName("latitude").item(0).getTextContent(),
					eElement.getElementsByTagName("longitude").item(0).getTextContent());

		}

		return null;
	}
}
