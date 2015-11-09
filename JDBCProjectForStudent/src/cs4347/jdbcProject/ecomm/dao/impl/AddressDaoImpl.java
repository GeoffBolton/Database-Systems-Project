package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class AddressDaoImpl implements AddressDAO
{
	private static final String insertSQL = "INSERT INTO ADDRESS (address1, address2, city, state, zipcode, customerID) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String selectQueryByCustomerID = "SELECT address1, address2, city, state, zipcode, customerID FROM Address where customerID = ?;";
	private static final String deleteSQL = "DELETE FROM Address WHERE customerID = ?;";
	
	public AddressDaoImpl(){
		
	}

	@Override
	public Address create(Connection conn, Address address, Long customerID) throws SQLException, DAOException {

		if(customerID == null)
		{
			throw new DAOException("This customer ID is null!");
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(insertSQL);
			ps.setString(1, address.getAddress1());
			ps.setString(2, address.getAddress2());
			ps.setString(3, address.getCity());
			ps.setString(4, address.getState());
			ps.setString(5, address.getZipcode());
			ps.setLong(6, customerID);
			
			
			return address;
		}
		finally
		{
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	
	}

	@Override
	public Address retrieveForCustomerID(Connection conn, Long customerID) throws SQLException, DAOException {


		if(customerID == null)
		{
			throw new DAOException("This customer ID is null!");
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(selectQueryByCustomerID);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			Address address = new Address();
		
			while(rs.next()) {
				address.setAddress1(rs.getString("address1"));
				address.setAddress2(rs.getString("address2"));
				address.setCity(rs.getString("city"));
				address.setState(rs.getString("state"));
				address.setZipcode(rs.getString("zipcode"));
			}
			return address;
		}
		
		finally
		{
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	
	
	}

	@Override
	public void deleteForCustomerID(Connection conn, Long customerID) throws SQLException, DAOException {

		if(customerID == null)
		{
			throw new DAOException("This customerID is null!");
		}
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(deleteSQL);
			
			ps.setLong(1, customerID);
			
			ps.executeUpdate();
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
	
	}

}
