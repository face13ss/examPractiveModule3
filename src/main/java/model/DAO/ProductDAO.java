package model.DAO;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/exammodule3?useSSL=false";
    private String jdbcUsername = "admin";
    private String jdbcPassword = "Admin@123";

    private static final String SELECT_ALL_PRODUCT =  "SELECT products.id, products.productName, products.price, products.quantity, products.color, products.descripts, category.categoryDevice,products.categoryId"
                                + " FROM products"
                                + " INNER JOIN category ON products.categoryId=category.id;";

    private static final String INSERT_PRODUCT = "INSERT INTO products(productName, price, quantity, color, descripts, categoryId) VALUES"
                                                + " (?, ?, ?,'?',?, ?);";

    public ProductDAO(){}

    protected Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }

    public List<Product> getProductList(){
        List<Product> productList = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT)){
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("products.id");
                String productName = rs.getString("products.productName");
                double price = rs.getDouble("products.price");
                int quantity = rs.getInt("products.quantity");
                String color = rs.getString("products.color");
                String descripts = rs.getString("products.descripts");
                String category = rs.getString("category.categoryDevice");
                int cateforyId = rs.getInt("products.categoryId");
                productList.add(new Product(id,productName,price,quantity,color,descripts,category,cateforyId));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return productList;
    }

}