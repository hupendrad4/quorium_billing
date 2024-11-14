//package billing;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DatabaseManager {
//    private static final String URL = "jdbc:sqlite:billing.db";
//
//    public static void initializeDatabase() {
//        try (Connection conn = DriverManager.getConnection(URL);
//             Statement stmt = conn.createStatement()) {
//            // Drop the existing products table if it exists
//            String dropProductsTable = "DROP TABLE IF EXISTS products";
//            stmt.execute(dropProductsTable);
//
//            // Create a new products table with the updated schema
//            String productsTable = "CREATE TABLE IF NOT EXISTS products (" +
//                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "name TEXT, " +
//                    "price DOUBLE, " +
//                    "quantity INTEGER)";
//            stmt.execute(productsTable);
//
//            // Create sales table
//            String salesTable = "CREATE TABLE IF NOT EXISTS sales (" +
//                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "date TEXT, " +
//                    "product_name TEXT, " +
//                    "price DOUBLE, " +
//                    "quantity INTEGER, " +
//                    "total DOUBLE)";
//            stmt.execute(salesTable);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void addProduct(String name, double price, int quantity) {
//        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
//        try (Connection conn = DriverManager.getConnection(URL);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, name);
//            pstmt.setDouble(2, price);
//            pstmt.setInt(3, quantity);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void updateProductQuantity(String productName, int newQuantity) {
//        String sql = "UPDATE products SET quantity = ? WHERE name = ?";
//        try (Connection conn = DriverManager.getConnection(URL);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, newQuantity);
//            pstmt.setString(2, productName);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static List<Product> getProducts() {
//        List<Product> products = new ArrayList<>();
//        String sql = "SELECT * FROM products";
//        try (Connection conn = DriverManager.getConnection(URL);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                products.add(new Product(
//                        rs.getInt("id"),
//                        rs.getString("name"),
//                        rs.getDouble("price"),
//                        rs.getInt("quantity")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return products;
//    }
//
////    public static void recordSale(String date, String productName, double price, int quantity, double total) {
////        String sql = "INSERT INTO sales (date, product_name, price, quantity, total) VALUES (?, ?, ?, ?, ?)";
////        try (Connection conn = DriverManager.getConnection(URL);
////             PreparedStatement pstmt = conn.prepareStatement(sql)) {
////            pstmt.setString(1, date);
////            pstmt.setString(2, productName);
////            pstmt.setDouble(3, price);
////            pstmt.setInt(4, quantity);
////            pstmt.setDouble(5, total);
////            pstmt.executeUpdate();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
//
//    public static void recordSale(String date, String productName, double price, int quantity, double total) {
//        String sql = "INSERT INTO sales (date, product_name, price, quantity, total) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = DriverManager.getConnection(URL);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, date);
//            pstmt.setString(2, productName);
//            pstmt.setDouble(3, price);
//            pstmt.setInt(4, quantity);
//            pstmt.setDouble(5, total);
//            pstmt.executeUpdate();
//
//            // Update product quantity after sale
//            updateProductQuantity(productName, getProductQuantity(productName) - quantity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static int getProductQuantity(String productName) {
//        String sql = "SELECT quantity FROM products WHERE name = ?";
//        try (Connection conn = DriverManager.getConnection(URL);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, productName);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next())
//            {
//                return rs.getInt("quantity");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    public static void recordSale(String date, String productName, double price, int quantity, double total, double paidAmount) {
//        String sql = "INSERT INTO sales (date, product_name, price, quantity, total, paid_amount) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection conn = DriverManager.getConnection(URL);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, date);
//            pstmt.setString(2, productName);
//            pstmt.setDouble(3, price);
//            pstmt.setInt(4, quantity);
//            pstmt.setDouble(5, total);
//            pstmt.setDouble(6, paidAmount);
//            pstmt.executeUpdate();
//
//            // Update product quantity after sale
//            updateProductQuantity(productName, getProductQuantity(productName) - quantity);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}




package billing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:billing.db";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            // Drop existing tables
            stmt.execute("DROP TABLE IF EXISTS products");
            stmt.execute("DROP TABLE IF EXISTS sales");
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("DROP TABLE IF EXISTS pending_payments");

            // Create products table
            String productsTable = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "price DOUBLE, " +
                    "quantity INTEGER)";
            stmt.execute(productsTable);

            // Create sales table with paid and pending amount columns
            String salesTable = "CREATE TABLE IF NOT EXISTS sales (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "date TEXT, " +
                    "product_name TEXT, " +
                    "price DOUBLE, " +
                    "quantity INTEGER, " +
                    "total DOUBLE, " +
                    "paid_amount DOUBLE, " +
                    "pending_amount DOUBLE)";
            stmt.execute(salesTable);

            // Create users table for authorized access
            String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE, " +
                    "password TEXT)";
            stmt.execute(usersTable);

            // Create pending_payments table for tracking unpaid balances
            String pendingPaymentsTable = "CREATE TABLE IF NOT EXISTS pending_payments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "customer_name TEXT, " +
                    "amount_due DOUBLE)";
            stmt.execute(pendingPaymentsTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addProduct(String name, double price, int quantity) {
        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProductQuantity(String productName, int newQuantity) {
        String sql = "UPDATE products SET quantity = ? WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setString(2, productName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void recordSale(String date, String productName, double price, int quantity, double total, double paidAmount) {
        double pendingAmount = total - paidAmount;
        String sql = "INSERT INTO sales (date, product_name, price, quantity, total, paid_amount, pending_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, productName);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setDouble(5, total);
            pstmt.setDouble(6, paidAmount);
            pstmt.setDouble(7, pendingAmount);
            pstmt.executeUpdate();

            // Update product quantity after sale
            updateProductQuantity(productName, getProductQuantity(productName) - quantity);

            // Record pending amount if any
            if (pendingAmount > 0) {
                savePendingAmount(productName, pendingAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getProductQuantity(String productName) {
        String sql = "SELECT quantity FROM products WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void savePendingAmount(String customerName, double pendingAmount) {
        String sql = "INSERT INTO pending_payments (customer_name, amount_due) VALUES (?, ?) " +
                "ON CONFLICT(customer_name) DO UPDATE SET amount_due = amount_due + ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            pstmt.setDouble(2, pendingAmount);
            pstmt.setDouble(3, pendingAmount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getPendingAmount(String customerName) {
        String sql = "SELECT amount_due FROM pending_payments WHERE customer_name = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("amount_due");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static List<Sale> getSalesReport(String period) {
        List<Sale> salesReport = new ArrayList<>(); // Remove .reversed() here
        String sql = period.equalsIgnoreCase("monthly") ?
                "SELECT * FROM sales WHERE strftime('%Y-%m', date) = strftime('%Y-%m', 'now')" :
                "SELECT * FROM sales WHERE strftime('%Y', date) = strftime('%Y', 'now')";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                salesReport.add(new Sale(
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("total"),
                        rs.getDouble("paid_amount"),
                        rs.getDouble("pending_amount"),
                        rs.getString("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesReport;
    }


    public static boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
