package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.util.DAOException;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.CreditCard;


public class CreditCardDaoImpl implements CreditCardDAO
{
	private static final String insertSQL = "INSERT INTO CREDITCARD (name, ccNumber, expDate, securityCode, customerID) VALUES (?, ?, ?, ?, ?);";
	private static final String selectQueryByCustomerID = "SELECT name, ccNumber, expDate, securityCode, customerID FROM CreditCard where customerID = ?;";
	private static final String deleteSQL = "DELETE FROM CreditCard WHERE customerID = ?;";
	

	public CreditCardDaoImpl(){
		
	}
	
	@Override
	public CreditCard create(Connection conn, CreditCard creditCard, Long customerID) throws SQLException, DAOException {

		if(customerID == null)
		{
			throw new DAOException("Customer ID is null!");
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(insertSQL);
			ps.setString(1, creditCard.getName());
			ps.setString(2, creditCard.getCcNumber());
			ps.setString(3, creditCard.getExpDate());
			ps.setString(4, creditCard.getSecurityCode());
			ps.setLong(5, customerID);
			
			
			return creditCard;
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
	public CreditCard retrieveForCustomerID(Connection conn, Long customerID) throws SQLException, DAOException {

		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(selectQueryByCustomerID);
			
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			CreditCard cc = new CreditCard();
			
			while(rs.next()) {
				cc.setName(rs.getString("id"));
				cc.setCcNumber(rs.getString("prodName"));
				cc.setExpDate(rs.getString("prodDescription"));
				cc.setSecurityCode(rs.getString("prodCategory"));
			}
			
			return cc;
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
