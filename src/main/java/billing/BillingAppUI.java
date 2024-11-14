package billing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; // Ensure this import is here

public class BillingAppUI extends JFrame {
    private JTextField customerNameField;
    private JTextField mobileField;
    private JTextField gstField;
    private JTextField addressField;
    private JComboBox<Product> productComboBox;
    private JTextField quantityField;
    private JTextArea invoiceArea;
    private JLabel totalLabel;
    private JLabel pendingAmountLabel;

    public BillingAppUI() {
        setTitle("Billing Application");
        setLayout(new FlowLayout());

        // Initialize UI components
        customerNameField = new JTextField(20);
        mobileField = new JTextField(10);
        gstField = new JTextField(15);
        addressField = new JTextField(25);
        productComboBox = new JComboBox<>(getProducts().toArray(new Product[0]));
        quantityField = new JTextField(5);
        invoiceArea = new JTextArea(10, 30);
        totalLabel = new JLabel("Total: ");
        pendingAmountLabel = new JLabel("Pending Amount: ");

        JButton generateInvoiceButton = new JButton("Generate Invoice");
        generateInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });

        JButton viewSalesButton = new JButton("View Monthly Sales");
        viewSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSalesReport("monthly");
            }
        });

        // Add components to frame
        add(new JLabel("Customer Name:"));
        add(customerNameField);
        add(new JLabel("Mobile Number:"));
        add(mobileField);
        add(new JLabel("GST Number:"));
        add(gstField);
        add(new JLabel("Address:"));
        add(addressField);
        add(new JLabel("Product:"));
        add(productComboBox);
        add(new JLabel("Quantity:"));
        add(quantityField);
        add(generateInvoiceButton);
        add(viewSalesButton);
        add(totalLabel);
        add(pendingAmountLabel);
        add(invoiceArea);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void generateInvoice() {
        String customerName = customerNameField.getText();
        String mobile = mobileField.getText();
        String gst = gstField.getText();
        String address = addressField.getText();
        Product selectedProduct = (Product) productComboBox.getSelectedItem();

        // Validate quantity input
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
            return;
        }

        double total = selectedProduct.getPrice() * quantity;

        // Record sale and update the quantity
        DatabaseManager.recordSale("now", selectedProduct.getName(), selectedProduct.getPrice(), quantity, total, total); // Assuming full payment for now

        // Generate invoice details
        String invoiceDetails = "Customer: " + customerName + "\n" +
                "Mobile: " + mobile + "\n" +
                "GST: " + gst + "\n" +
                "Address: " + address + "\n" +
                "Product: " + selectedProduct.getName() + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total: " + total + "\n";
        invoiceArea.setText(invoiceDetails);
        totalLabel.setText("Total: " + total);
        pendingAmountLabel.setText("Pending Amount: 0.00");
    }

    private void viewSalesReport(String period) {
        List<Sale> sales = DatabaseManager.getSalesReport(period);
        // Display sales to the user in a suitable format
    }

    public static void main(String[] args) {
        new BillingAppUI();
    }

    private List<Product> getProducts() {
        return DatabaseManager.getProducts();
    }
}
