package billing;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator extends Component {
    private static final String URL = "jdbc:sqlite:billing.db";

    public static void generateSalesReport(String startDate, String endDate) {
        String sql = "SELECT date, product_name, quantity, total FROM sales WHERE date BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("Sales Report from " + startDate + " to " + endDate);
            System.out.println("------------------------------------------------");

            while (rs.next()) {
                String date = rs.getString("date");
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");

                System.out.println("Date: " + date + ", Product: " + productName + ", Quantity: " + quantity + ", Total: " + total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateCustomerSalesReport(String customerId, String startDate, String endDate) {
        String sql = "SELECT s.date, s.product_name, s.quantity, s.total FROM sales s " +
                "INNER JOIN customers c ON s.customer_id = c.id " +
                "WHERE c.id = ? AND s.date BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("Sales Report for Customer " + customerId + " from " + startDate + " to " + endDate);
            System.out.println("------------------------------------------------");

            while (rs.next()) {
                String date = rs.getString("date");
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");

                System.out.println("Date: " + date + ", Product: " + productName + ", Quantity: " + quantity + ", Total: " + total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewSalesReport(String period) {
        List<Sale> sales = DatabaseManager.getSalesReport(period);
        StringBuilder salesReport = new StringBuilder("Sales Report for " + period + ":\n");

        for (Sale sale : sales) {
            salesReport.append("Product: ").append(sale.getProductName())
                    .append(", Quantity: ").append(sale.getQuantity())
                    .append(", Total: ₹").append(sale.getTotal()).append("\n");
        }

        JOptionPane.showMessageDialog(this, salesReport.toString(), "Sales Report", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void generateFinancialYearReport(int year) {
        String startDate = year + "-04-01";
        String endDate = (year + 1) + "-03-31";
        generateSalesReport(startDate, endDate);
    }
}
