package billing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductManager {
    private static final String URL = "jdbc:sqlite:billing.db";

    public static void initializeProducts() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            String[] products = {"Shev", "Chakli", "Chivda", "Ladoo", "Sweets", "Jalebi", "Khaman", "Dhokla", "Shankarpade"};
            double[] prices = {10.0, 20.0, 15.0, 30.0, 25.0, 12.0, 18.0, 22.0, 10.0};
            int initialQuantity = 100;

            for (int i = 0; i < products.length; i++) {
                pstmt.setString(1, products[i]);
                pstmt.setDouble(2, prices[i]);
                pstmt.setInt(3, initialQuantity);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Products initialized in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
