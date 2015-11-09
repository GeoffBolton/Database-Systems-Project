package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchasePersistenceService;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchasePersistenceServiceImpl implements PurchasePersistenceService
{
	private DataSource dataSource;

	public PurchasePersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public Purchase create(Purchase purchase) throws SQLException, DAOException {
		PurchaseDAO purchaseDAO = new PurchaseDaoImpl();
		
		//If productID null throw DAOException
		if(purchase.getId() != null)
		{
			throw new DAOException("This purchase has a non-null ID.");
		}
		Connection conn = dataSource.getConnection();
		Purchase purchs = null;
		
		//Call create from DAO Implementation
		try {
			conn.setAutoCommit(false);
			purchs = purchaseDAO.create(conn, purchase);
			
			conn.commit();
		}
		catch (Exception ex) {
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		return purchs;
	}

	@Override
	public Purchase retrieve(Long id) throws SQLException, DAOException {
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		
		Connection conn = dataSource.getConnection();
		Purchase purchs = null;
		
		//Call retrieve from DAO Implementation
		try {
			conn.setAutoCommit(false);
			purchs = purchaseDao.retrieve(conn, id);
			
			conn.commit();
		}
		catch (Exception ex) {
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
		return purchs;
	}
	

	@Override
	public int update(Purchase purchase) throws SQLException, DAOException {
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		
		Connection conn = dataSource.getConnection();
		int row = 0;
		//Call retrieve from DAO Implementation
		try {
			conn.setAutoCommit(false);
			row = purchaseDao.update(conn, purchase);
			
			conn.commit();
		}
		catch (Exception ex) {
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
		return row;
	}

	@Override
	public int delete(Long id) throws SQLException, DAOException {
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		
		Connection conn = dataSource.getConnection();
		int row = 0;
		//Call retrieve from DAO Implementation
		try {
			conn.setAutoCommit(false);
			row = purchaseDao.delete(conn, id);
			
			conn.commit();
		}
		catch (Exception ex) {
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
		return row;
	}

	@Override
	public List<Purchase> retrieveForCustomerID(Long customerID) throws SQLException, DAOException {
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		
		Connection conn = dataSource.getConnection();
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		//Call retrieve from DAO Implementation
		try {
			conn.setAutoCommit(false);
			purchaseList = purchaseDao.retrieveForCustomerID(conn, customerID);
			
			conn.commit();
		}
		catch (Exception ex) {
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
		return purchaseList;
	}

	@Override
	public PurchaseSummary retrievePurchaseSummary(Long customerID) throws SQLException, DAOException {
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		
		Connection conn = dataSource.getConnection();
		PurchaseSummary purchaseSum = new PurchaseSummary();
		//Call retrieve from DAO Implementation
		try {
			conn.setAutoCommit(false);
			purchaseSum = purchaseDao.retrievePurchaseSummary(conn, customerID);
			
			conn.commit();
		}
		catch (Exception ex) {
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
		return purchaseSum;
	}
	

	@Override
	public List<Purchase> retrieveForProductID(Long productID) throws SQLException, DAOException {
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		
		Connection conn = dataSource.getConnection();
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		//Call retrieve from DAO Implementation
		try {
			conn.setAutoCommit(false);
			purchaseList = purchaseDao.retrieveForProductID(conn, productID);
			
			conn.commit();
		}
		catch (Exception ex) {
			conn.rollback();
		}
		finally {
			if (conn != null) {
				conn.setAutoCommit(true);
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}	
		return purchaseList;
	}
	
}
	


