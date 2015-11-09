package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

import javax.sql.DataSource;

public class PurchaseDaoImpl implements PurchaseDAO
{
	private static final String insertSQL = "INSERT INTO PURCHASE (id, productID, customerID, purchaseDate, purchaseAmount) VALUES (NULL, ?, ?, ?, ?);";
	private static final String selectQueryByPurchaseID = "SELECT id, productID, customerID, purchaseDate, purchaseAmount FROM Purchase where id = ?;";
	private static final String updateSQL = "UPDATE PURCHSE SET purchaseID = ?, customerID = ?, purchaseDate = ?, purchaseAmount = ? WHERE id = ?;";
	private static final String deleteSQL = "DELETE FROM PURCHASE WHERE id = ?;";
	private static final String selectQueryByCustomerID = "SELECT id, productID, customerID, purchaseDate, purchaseAmount FROM Purchase where customerID = ?;";
	private static final String selectQueryByProductID = "SELECT id, productID, customerID, purchaseDate, purchaseAmount FROM Purchase where productID = ?;";
	private static final String selectQueryByPurchaseHistory = "SELECT id, productID, customerID, purchaseDate, purchaseAmount FROM Purchase where customerID = ?;";
	
	public PurchaseDaoImpl(){
		
	}
	public Purchase create(Connection conn, Purchase purchase) throws SQLException, DAOException{
		if(purchase.getId() != null){
			throw new DAOException("This purchase ID is non-null");
		}
		
		PreparedStatement prepst = null;
		
		try {
			prepst = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			prepst.setLong(1, purchase.getProductID());
			prepst.setLong(2, purchase.getCustomerID());
			prepst.setDate(3, purchase.getPurchaseDate());
			prepst.setDouble(4, purchase.getPurchaseAmount());
			
			ResultSet keyRS = prepst.getGeneratedKeys();
			keyRS.next();
			
			int nextKey = keyRS.getInt(1);
			purchase.setId(Long.valueOf(nextKey));
			
			return purchase;
		}
		finally
		{
			if (prepst != null && !prepst.isClosed()) {
				prepst.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		
	}
	public Purchase retrieve(Connection conn, Long purchaseID) throws SQLException, DAOException
	{
		if(purchaseID == null) {
			throw new DAOException("Retrieving null ID Purchase!");
		}
		
		PreparedStatement prepst = null;
		try {
			prepst = conn.prepareStatement(selectQueryByPurchaseID);
			prepst.setLong(1, purchaseID);
			ResultSet rs = prepst.executeQuery();
			Purchase purchase = new Purchase();
		
			while(rs.next()) {
				purchase.setId(rs.getLong("id"));
				purchase.setProductID(rs.getLong("productID"));
				purchase.setCustomerID(rs.getLong("customerID"));
				purchase.setPurchaseDate(rs.getDate("purchaseDate"));
				purchase.setPurchaseAmount(rs.getDouble("purchaseAmount"));
			}
			
			if(purchase.getId() == null)
			{
				return null;
			}
			
			return purchase;
		}
		finally {
			if (prepst != null && !prepst.isClosed()) {
				prepst.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}		
	}
	public int update(Connection conn, Purchase purchase) throws SQLException, DAOException
	{
		if (purchase.getId() == null)
		{
			throw new DAOException("Purchase ID is null.");
		}
		
		PreparedStatement prepst = null;
		try {
			prepst = conn.prepareStatement(updateSQL);
			
			prepst.setLong(1, purchase.getProductID());
			prepst.setLong(2, purchase.getCustomerID());
			prepst.setDate(3, purchase.getPurchaseDate());
			prepst.setDouble(4, purchase.getPurchaseAmount());
			
			return prepst.executeUpdate();
		}
		finally {
			if (prepst != null && !prepst.isClosed()) {
				prepst.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
	}
	public int delete(Connection conn, Long purchaseID) throws SQLException, DAOException
	{
		if(purchaseID == null)
		{
			throw new DAOException("Purchase ID is null.");
		}
		
		PreparedStatement prepst = null;
		try {
			prepst = conn.prepareStatement(deleteSQL);
			
			prepst.setLong(1, purchaseID);
			
			return prepst.executeUpdate();
		}
		finally {
			if (prepst != null && !prepst.isClosed()) {
				prepst.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
	}
	public List<Purchase> retrieveForCustomerID(Connection conn, Long customerID) throws SQLException, DAOException {

		PreparedStatement prepst = null;
		List<Purchase> purchaseResultList = new ArrayList<Purchase>();
		try {
			prepst = conn.prepareStatement(selectQueryByCustomerID);
			
			prepst.setLong(1, customerID);
			ResultSet rs = prepst.executeQuery();
			
			while(rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setId(rs.getLong("id"));
				purchase.setProductID(rs.getLong("productID"));
				purchase.setCustomerID(rs.getLong("customerID"));
				purchase.setPurchaseDate(rs.getDate("purchaseDate"));
				purchase.setPurchaseAmount(rs.getDouble("purchaseAmount"));
				purchaseResultList.add(purchase);
			}
			
			return purchaseResultList;
		}
		finally {
			if (prepst != null && !prepst.isClosed()) {
				prepst.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
	
	}
	public List<Purchase> retrieveForProductID(Connection conn, Long productID) throws SQLException, DAOException {

		PreparedStatement prepst = null;
		List<Purchase> purchaseResultList = new ArrayList<Purchase>();
		try {
			prepst = conn.prepareStatement(selectQueryByProductID);
			
			prepst.setLong(1, productID);
			ResultSet rs = prepst.executeQuery();
			
			while(rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setId(rs.getLong("id"));
				purchase.setProductID(rs.getLong("productID"));
				purchase.setCustomerID(rs.getLong("customerID"));
				purchase.setPurchaseDate(rs.getDate("purchaseDate"));
				purchase.setPurchaseAmount(rs.getDouble("purchaseAmount"));
				purchaseResultList.add(purchase);
			}
			
			return purchaseResultList;
		}
		finally {
			if (prepst != null && !prepst.isClosed()) {
				prepst.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	
	}
	public PurchaseSummary retrievePurchaseSummary(Connection conn, Long customerID) throws SQLException, DAOException {

		PreparedStatement prepst = null;
		PurchaseSummary purchaseSum = new PurchaseSummary();
		double PurchaseMin = 0;
		double PurchaseMax = 0;
		double PurchaseAvg = 0;
		int numPurchases = 0;
		try {
			prepst = conn.prepareStatement(selectQueryByPurchaseHistory);
			
			prepst.setLong(1, customerID);
			ResultSet rs = prepst.executeQuery();
			
			while(rs.next()) {
				Purchase purchase = new Purchase();
				purchase.setId(rs.getLong("id"));
				purchase.setProductID(rs.getLong("productID"));
				purchase.setCustomerID(rs.getLong("customerID"));
				purchase.setPurchaseDate(rs.getDate("purchaseDate"));
				purchase.setPurchaseAmount(rs.getDouble("purchaseAmount"));
				PurchaseMin = Math.min(PurchaseMin, purchase.getPurchaseAmount());
				PurchaseMax = Math.max(PurchaseMax, purchase.getPurchaseAmount());
				PurchaseAvg += purchase.getPurchaseAmount();
				numPurchases++;

				
			}
			purchaseSum.minPurchase = (float)PurchaseMin;
			purchaseSum.maxPurchase = (float)PurchaseMax;
			purchaseSum.avgPurchase = (float)(PurchaseAvg/numPurchases);
			return purchaseSum;
		}
		finally {
			if (prepst != null && !prepst.isClosed()) {
				prepst.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	
		
	}
}
