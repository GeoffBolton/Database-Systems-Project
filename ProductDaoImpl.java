package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

import javax.sql.DataSource;

public class ProductDaoImpl implements ProductDAO
{
	
	private static final String insertSQL = "INSERT INTO PRODUCT (id, prodName, prodDescription, prodCategory, prodUPC) VALUES (NULL, ?, ?, ?, ?);";
	private static final String selectQueryByProductID = "SELECT id, prodName, prodDescription, prodCategory, prodUPC FROM Product where id = ?;";
	private static final String updateSQL = "UPDATE PRODUCT SET prodName = ?, prodDescription = ?, prodCategory = ?, prodUPC = ? WHERE id = ?;";
	private static final String deleteSQL = "DELETE FROM PRODUCT WHERE id = ?;";
	private static final String selectQueryByCategory = "SELECT id, prodName, prodDescription, prodCategory, prodUPC FROM Product where prodCategory = ?;";
	private static final String selectQueryByUPC = "SELECT id, prodName, prodDescription, prodCategory, prodUPC FROM Product where prodUPC = ?;";
	
	public ProductDaoImpl()
	{
	}
	
	public Product create(Connection conn, Product p_product) throws SQLException, DAOException
	{
		if(p_product.getId() == null)
		{
			throw new DAOException("Product ID is currently null.");
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p_product.getProdName());
			ps.setString(2, p_product.getProdDescription());
			ps.setInt(3, p_product.getProdCategory());
			ps.setString(4, p_product.getProdUPC());
			
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			
			int lastKey = keyRS.getInt(1);
			p_product.setId(Long.valueOf(lastKey));
			
			return p_product;
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
	
	public Product retrieve(Connection conn, Long productID) throws SQLException, DAOException
	{
		if(productID == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(selectQueryByProductID);
			ps.setLong(1, productID);
			ResultSet rs = ps.executeQuery();
			Product prod = new Product();
		
			while(rs.next()) {
				prod.setId(rs.getLong("id"));
				prod.setProdName(rs.getString("prodName"));
				prod.setProdDescription(rs.getString("prodDescription"));
				prod.setProdCategory(rs.getInt("prodCategory"));
				prod.setProdUPC(rs.getString("prodUPC"));
			}
			
			if(prod.getId() == null)
			{
				return null;
			}
			
			return prod;
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
	
	public int update(Connection conn, Product product) throws SQLException, DAOException
	{
		if (product.getId() == null)
		{
			throw new DAOException("Product is null.");
		}
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(updateSQL);
			
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			
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
	
	public int delete(Connection conn, Long productID) throws SQLException, DAOException
	{
		if(productID == null)
		{
			throw new DAOException("ProductID is null.");
		}
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(deleteSQL);
			
			ps.setLong(1, productID);
			
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
	
	public List<Product> retrieveByCategory(Connection conn, int category) throws SQLException, DAOException
	{
		PreparedStatement ps = null;
		List<Product> productResultList = new ArrayList<Product>();
		try {
			ps = conn.prepareStatement(selectQueryByCategory);
			
			ps.setInt(1, category);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Product prod = new Product();
				prod.setId(rs.getLong("id"));
				prod.setProdName(rs.getString("prodName"));
				prod.setProdDescription(rs.getString("prodDescription"));
				prod.setProdCategory(rs.getInt("prodCategory"));
				prod.setProdUPC(rs.getString("prodUPC"));
				productResultList.add(prod);
			}
			
			return productResultList;
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
	
	public Product retrieveByUPC(Connection conn, String upc) throws SQLException, DAOException
	{
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(selectQueryByUPC);
			
			ps.setString(1, upc);
			ResultSet rs = ps.executeQuery();
			Product prod = new Product();
			
			while(rs.next()) {
				prod.setId(rs.getLong("id"));
				prod.setProdName(rs.getString("prodName"));
				prod.setProdDescription(rs.getString("prodDescription"));
				prod.setProdCategory(rs.getInt("prodCategory"));
				prod.setProdUPC(rs.getString("prodUPC"));
			}
			if(prod.getId() == null)
			{
				return null;
			}
			
			return prod;
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
