package me.tss1410.RegionerPlus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
	private String hostname = "";
	private String portnmbr = "";
	private String username = "";
	private String password = "";
	private String database = "";
	protected Connection connection = null;

	public MySQL(final String hostname, final String portnmbr,
			final String database, final String username, final String password) {
		this.hostname = hostname;
		this.portnmbr = portnmbr;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	/**
	 * checks if the connection is still active
	 * 
	 * @return true if still active
	 * @throws SQLException
	 * */
	public boolean checkConnection() throws SQLException {
		if (connection.isValid(5)) {
			return true;
		}
		return false;
	}

	/**
	 * close database connection
	 * */
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * returns the active connection
	 * 
	 * @return Connection
	 * 
	 * */

	public Connection getConnection() {
		return this.connection;
	}


	/**
	 * open database connection
	 * 
	 * */
	public Connection open() {
		String url = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://" + this.hostname + ":" + this.portnmbr + "/"
					+ this.database + "?autoReconnect=true";
			this.connection = DriverManager.getConnection(url, this.username,
					this.password);
			return this.connection;
		} catch (final SQLException e) {
			System.out.print("Could not connect to MySQL server!");
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			System.out.print("JDBC Driver not found!");
			e.printStackTrace();
		}
		return null;
	}
}
