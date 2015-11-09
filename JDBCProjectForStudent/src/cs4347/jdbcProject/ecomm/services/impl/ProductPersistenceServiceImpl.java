package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.services.ProductPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	private DataSource dataSource;
	
	public ProductPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;		
	}
	
	
	public Product create(Product product) throws SQLException, DAOException
	{	
		ProductDAO productDAO = new ProductDaoImpl();
		
		//If productID null throw DAOException
		if(product.getId() != null)
		{
			throw new DAOException("Given product has a non-null ID.");
		}
		Connection connection = dataSource.getConnection();
		Product prod = null;
		
		//Call create from DAO Implementation
		try {
			connection.setAutoCommit(false);
			prod = productDAO.create(connection, product);
			
			connection.commit();
		}
		catch (Exception ex) {
			connection.rollback();
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		return prod;
	}

	public Product retrieve(Long id) throws SQLException, DAOException
	{
		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		Product prod = null;
		
		//Call retrieve from DAO Implementation
		try {
			connection.setAutoCommit(false);
			prod = productDAO.retrieve(connection, id);
			
			connection.commit();
		}
		catch (Exception ex) {
			connection.rollback();
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}	
		return prod;
	}
	
	public int update(Product product) throws SQLException, DAOException
	{
		if(product.getId() == null)
		{
			throw new DAOException("ProductID is null.");
		}
		
		int row = 0;
		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		
		//Call update from DAO Implementation
		try {
			connection.setAutoCommit(false);
			row = productDAO.update(connection, product);
			
			connection.commit();
		}
		catch (Exception ex) {
			connection.rollback();
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}	
		return row;
	}
	
	public int delete(Long id) throws SQLException, DAOException
	{
		if(id == null)
		{
			throw new DAOException("ProductID is null.");
		}
		
		int row = 0;
		ProductDAO productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		//Call delete from DAO Implementation
		try {
			connection.setAutoCommit(false);
			row = productDAO.delete(connection, id);
			
			connection.commit();
		}
		catch (Exception ex) {
			connection.rollback();
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}	
		return row;
	}
	
	public Product retrieveByUPC(String upc) throws SQLException, DAOException
	{
		ProductDAO productDAO = new ProductDaoImpl();
		Product prod = null;
		Connection connection = dataSource.getConnection();
		
		//Call retrieveByUPC from DAO Implementation
		try {
			connection.setAutoCommit(false);
			prod = productDAO.retrieveByUPC(connection, upc);
			connection.commit();
		}
		catch (Exception ex) {
			connection.rollback();
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}	
		return prod;
	}
	
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException
	{
		List<Product> resultList = new ArrayList<Product>();
		ProductDAO productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		//Call retrieveByCategory from DAO Implementation
		try {
			connection.setAutoCommit(false);
			resultList = productDAO.retrieveByCategory(connection, category);
			connection.commit();
		}
		catch (Exception ex) {
			connection.rollback();
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		return resultList;
	}
}
