package logic;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataPersistenceLayer {
	Connection connection = null;

	public DataPersistenceLayer() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.dir")+"/tweets.db");
	}

	public void saveNewStoring(String id, String message) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("INSERT INTO tweets VALUES(null, '" + message + "','" + id + "')");
		statement.close();
	}

	public boolean doesIdExist(String id) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM tweets WHERE NSID='" + id + "'");
		while (rs.next()) {
			if (rs.getString(1) != null) {
				rs.close();
				return true;
			}
		}
		statement.close();
		return false;
	}

	public void connect() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:tweets.db");
	}
}
