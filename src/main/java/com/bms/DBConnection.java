package com.bms;

import java.sql.Connection;
import java.sql.DriverManager;



/**
 * This is the Database connection class.
 * Responsible for establishing database connection with the application
 * using JDBC.
*/
public class DBConnection {  



	  private static String DRIVERNAME = "org.apache.derby.jdbc.EmbeddedDriver";
//	  jdbc:derby:D:\Users\2794241\MyDB;create=true
		private static String URL = "jdbc:derby:D:\\Users\\2794241\\MyDB;create=true";

		private static Connection connection;

		

		public static Connection getConnection() {
			
			connection = null;
	 

		try {

				Class.forName(DRIVERNAME);

				connection = DriverManager.getConnection(URL);

				System.out.println("The DB Connection is Established!");

			}catch(Exception e) {

				System.out.println("An exception occured while connecting to the DB : "+ e);

			}

			return connection;
		}
}