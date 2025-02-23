package com.bms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public class EmployeeDAO {
	
	 /**
		 * 
		 * This method fetches all the details of an employee by taking
		 * EMP ID. 
		 */

    
    public Employee getEmployees() throws SQLException {
    	String employeeId1 = EmployeeLoginServlet.EmployeeId;
        
        Employee employee = null;
        String query = "SELECT * FROM Employee where employeeId=?";

        try (Connection conn = DBConnection.getConnection()){
        	
        	try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        		pstmt.setString(1, employeeId1);
        		ResultSet resultSet = pstmt.executeQuery();

                while (resultSet.next()) {
                    int employeeId = resultSet.getInt("employeeId");
                    String employeeName = resultSet.getString("employeeName");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String address = resultSet.getString("address");
                    String contactNumber = resultSet.getString("contactNumber");

                    // Creating and adding Employee object to the list
                    employee = new Employee(employeeId, employeeName, email, password, address, contactNumber);
                }
        	}
        	
             
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception
        }

        return employee;
    }

    /**
	 * 
	 * This method inserts the employee in the database on submitting 
	 * the registration form for employee registration.
  */

    // Insert a new employee
    public static void insertEmployee(Connection connection, Employee employee) throws SQLException {
        String insertSQL = "INSERT INTO Employee (employeeId, employeeName, email, password, address, contactNumber) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, employee.getEmployeeId());
            pstmt.setString(2, employee.getEmployeeName());
            pstmt.setString(3, employee.getEmail());
            pstmt.setString(4, employee.getPassword());
            pstmt.setString(5, employee.getAddress());
            pstmt.setString(6, employee.getContactNumber());

            pstmt.executeUpdate();
            
            System.out.println("Employee inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception
        }
    }
    
    /**
	 * 
	 * Checks whether the provided email already exists in the
	 * system or not during the registration.
    */


    // Check if an email already exists
    public static boolean emailExists(Connection connection, String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM Employee WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exception
            return false;
        }
    }
}