package domain;

public class Starbuck {
	private String naam;
	private String adres;
	private String city;
	private double latitude;
	private double longitude;

	public Starbuck(String naam, String adres, String city, String latitude, String longitude) {
		super();
		this.naam = naam;
		this.adres = adres;
		this.city = city;
		this.latitude = Double.parseDouble(latitude);
		this.longitude = Double.parseDouble(longitude);
	}
	
	public String getGoogleMapsLink() {
		return "http://maps.google.com/maps?q=" + latitude + "," + longitude + "+(My+Point)&z=14&ll=" + latitude + "," + longitude + "";
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
	
	public String getCity() {
		return city;
	}

	public void City(String city) {
		this.city = city;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}
}
