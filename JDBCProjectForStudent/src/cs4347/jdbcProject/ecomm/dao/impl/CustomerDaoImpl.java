package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoImpl implements CustomerDAO
{
	private static final String insertSQL = "INSERT INTO CUSTOMER (id, firstName, lastName, gender, dob, email) VALUES (NULL, ?, ?, ?, ?, ?);";
	private static final String selectQueryByCustomerID = "SELECT id, firstName, lastName, gender, dob, email FROM Customer where id = ?;";
	private static final String updateSQL = "UPDATE PRODUCT SET firstName = ?, lastName = ?, gender = ?, dob = ?, email = ? WHERE id = ?;";
	private static final String deleteSQL = "DELETE FROM Customer WHERE id = ?;";
	private static final String selectQueryByZipcode = "SELECT id, firstName, lastName, gender, dob, email FROM Customer, Address where id = customerID AND zipcode = ?;";
	private static final String selectQueryByDOB = "SELECT id, firstName, lastName, gender, dob, email FROM Customer where dob >= ?  AND dob <= ?;";
	
	public CustomerDaoImpl(){
		
	}

	@Override
	public Customer create(Connection conn, Customer customer) throws SQLException, DAOException {

		if(customer.getId() == null)
		{
			throw new DAOException("This customer ID is null!");
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, String.valueOf(customer.getGender()));
			ps.setDate(4, customer.getDob());
			
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			
			int lastKey = keyRS.getInt(1);
			customer.setId(Long.valueOf(lastKey));
			
			return customer;
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
	public Customer retrieve(Connection conn, Long id) throws SQLException, DAOException {
		if(id == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(selectQueryByCustomerID);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			Customer customer = new Customer();
		
			while(rs.next()) {
				customer.setId(rs.getLong("id"));
				customer.setFirstName(rs.getString("firstName"));
				customer.setLastName(rs.getString("lastName"));
				customer.setGender(rs.getString("gender").charAt(0));
				customer.setDob(rs.getDate("dob"));
				customer.setEmail(rs.getString("email"));
			}
			
			if(customer.getId() == null)
			{
				return null;
			}
			
			return customer;
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
	public int update(Connection conn, Customer customer) throws SQLException, DAOException {

		if (customer.getId() == null)
		{
			throw new DAOException("This customer ID is null!");
		}
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(updateSQL);
			
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, String.valueOf(customer.getGender()));
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			
			return ps.executeUpdate();
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
	public int delete(Connection conn, Long id) throws SQLException, DAOException {

		if(id == null)
		{
			throw new DAOException("This customer ID is null!");
		}
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(deleteSQL);
			
			ps.setLong(1, id);
			
			return ps.executeUpdate();
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
	public List<Customer> retrieveByZipCode(Connection conn, String zipCode) throws SQLException, DAOException {

		PreparedStatement ps = null;
		List<Customer> customerResultList = new ArrayList<Customer>();
		try {
			ps = conn.prepareStatement(selectQueryByZipcode);
			
			ps.setString(1, zipCode);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong("id"));
				customer.setFirstName(rs.getString("firstname"));
				customer.setLastName(rs.getString("lastname"));
				customer.setGender(rs.getString("gender").charAt(0));
				customer.setDob(rs.getDate("dob"));
				customer.setEmail(rs.getString("email"));
				customerResultList.add(customer);
			}
			
			return customerResultList;
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
	public List<Customer> retrieveByDOB(Connection conn, Date startDate, Date endDate) throws SQLException, DAOException {

		PreparedStatement ps = null;
		List<Customer> customerResultList = new ArrayList<Customer>();
		try {
			ps = conn.prepareStatement(selectQueryByDOB);
			
			ps.setDate(1, startDate);
			ps.setDate(1, endDate);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong("id"));
				customer.setFirstName(rs.getString("firstname"));
				customer.setLastName(rs.getString("lastname"));
				customer.setGender(rs.getString("gener").charAt(0));
				customer.setDob(rs.getDate("dob"));
				customer.setEmail(rs.getString("email"));
				customerResultList.add(customer);
			}
			
			return customerResultList;
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
