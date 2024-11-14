package billing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BillingApp {
    private JFrame frame;
    private JComboBox<Product> productComboBox;
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JTextField customerNameField;
    private JTextField customerMobileField;
    private JTextField gstNumberField;
    private JTextField quantityField;
    private JTextField customerAddressField;
    private JLabel totalAmountLabel; // Label to display Total Amount (Before GST)
    private List<Product> cart;
    private JRadioButton gstIncluded; // Radio button for including GST
    private JRadioButton gstExcluded; // Radio button for excluding GST

    // Branding Information
    private final String companyName = "Quorium Technologies Private Limited";
    private final String companyEmail = "info@quorium.com";
    private final String companyPhone = "+91-797202990";
    private final String companyWebsite = "www.quoriumtechnologies.com";
    private final String logoPath = "src/main/resources/quorium logo.png";

    public BillingApp() {
        DatabaseManager.initializeDatabase();
        ProductManager.initializeProducts();

        frame = new JFrame("Billing Application");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(240, 248, 255));

        productComboBox = new JComboBox<>();
        cartTableModel = new DefaultTableModel(new String[]{"Product", "Quantity"}, 0);
        cartTable = new JTable(cartTableModel);
        cart = new ArrayList<>();
        quantityField = new JTextField(5);
        customerNameField = new JTextField(15);
        customerMobileField = new JTextField(15);
        gstNumberField = new JTextField(15);
        customerAddressField = new JTextField(15);
        totalAmountLabel = new JLabel("Amount (Before GST): ₹0.00"); // Initial value for total amount label

        loadProducts();

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBackground(new Color(240, 248, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel

        inputPanel.add(new JLabel("Customer Name:", JLabel.RIGHT));
        inputPanel.add(customerNameField);
        inputPanel.add(new JLabel("Mobile:", JLabel.RIGHT));
        inputPanel.add(customerMobileField);
        inputPanel.add(new JLabel("GST Number:", JLabel.RIGHT));
        inputPanel.add(gstNumberField);
        inputPanel.add(new JLabel("Address:", JLabel.RIGHT));
        inputPanel.add(customerAddressField);
        inputPanel.add(new JLabel("Product:", JLabel.RIGHT));
        inputPanel.add(productComboBox);
        inputPanel.add(new JLabel("Quantity:", JLabel.RIGHT));
        inputPanel.add(quantityField);

        // Panel for GST selection
        JPanel gstPanel = new JPanel();
        gstPanel.setBackground(new Color(240, 248, 255));
        gstIncluded = new JRadioButton("Include GST");
        gstExcluded = new JRadioButton("Exclude GST", true); // Default selection
        ButtonGroup gstGroup = new ButtonGroup();
        gstGroup.add(gstIncluded);
        gstGroup.add(gstExcluded);
        gstPanel.add(gstIncluded);
        gstPanel.add(gstExcluded);
        inputPanel.add(gstPanel); // Add GST selection to input panel

        // Cart Panel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBackground(new Color(240, 248, 255));
        cartPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Shopping Cart"));

        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setPreferredSize(new Dimension(0, 150));
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        // Display the total amount (before GST) below the cart
        JPanel totalAmountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalAmountPanel.setBackground(new Color(240, 248, 255));
        totalAmountPanel.add(totalAmountLabel);
        cartPanel.add(totalAmountPanel, BorderLayout.SOUTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(createStyledButton("Add to Cart", e -> addToCart()));
        buttonPanel.add(createStyledButton("Generate Invoice", e -> generateInvoice()));
        buttonPanel.add(createStyledButton("Download Invoice", e -> downloadInvoice()));
        cartPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(cartPanel, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBackground(new Color(70, 130, 180));
        footerPanel.setPreferredSize(new Dimension(600, 150));

        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon logoIcon = new ImageIcon(logoPath);
            Image logoImage = logoIcon.getImage().getScaledInstance(180, 80, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(logoImage));
        } catch (Exception e) {
            logoLabel.setText("Logo not found");
        }
        footerPanel.add(logoLabel);

        footerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.setBackground(new Color(70, 130, 180));
        infoPanel.add(new JLabel(companyName, JLabel.CENTER));
        infoPanel.add(new JLabel("Email: " + companyEmail, JLabel.CENTER));
        infoPanel.add(new JLabel("Phone: " + companyPhone, JLabel.CENTER));
        infoPanel.add(new JLabel("Website: " + companyWebsite, JLabel.CENTER));
        footerPanel.add(infoPanel);

        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void loadProducts() {
        List<Product> products = DatabaseManager.getProducts();
        for (Product product : products) {
            productComboBox.addItem(product);
        }
    }

    private JButton createStyledButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 165, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.addActionListener(action);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40)); // Button size
        return button;
    }

//    private void addToCart() {
//        Product selectedProduct = (Product) productComboBox.getSelectedItem();
//        if (selectedProduct != null) {
//            int quantity = Integer.parseInt(quantityField.getText());
//            selectedProduct.setQuantity(quantity);
//            cart.add(selectedProduct);
//            cartTableModel.addRow(new Object[]{selectedProduct.getName(), quantity});
//            quantityField.setText("");
//            updateTotalAmountLabel(); // Update total amount label when adding a product to the cart
//        }
//    }

    private void addToCart() {
        try {
            Product selectedProduct = (Product) productComboBox.getSelectedItem();
            if (selectedProduct != null) {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity <= 0) {
                    throw new NumberFormatException();
                }
                selectedProduct.setQuantity(quantity);
                cart.add(selectedProduct);
                cartTableModel.addRow(new Object[]{selectedProduct.getName(), quantity});
                quantityField.setText("");
                updateTotalAmountLabel();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void removeFromCart(int rowIndex) {
        if (rowIndex >= 0) {
            Product removedProduct = cart.get(rowIndex);
            cart.remove(rowIndex);
            cartTableModel.removeRow(rowIndex);
            updateTotalAmountLabel();
        }
    }


    private void generateInvoice() {
        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        double totalAmount = calculateTotalAmount(); // Calculate total amount only once here
        boolean includeGST = gstIncluded.isSelected(); // Check if GST is included

        // Calculate total with GST if needed
        double payableAmount = includeGST ? calculateTotalWithGST(totalAmount) : totalAmount;
        showInvoice(customerName, customerAddress, payableAmount, includeGST);
    }

    private void downloadInvoice() {
        String customerName = customerNameField.getText();
        String customerAddress = customerAddressField.getText();
        boolean includeGST = gstIncluded.isSelected(); // Check if GST is included
        double baseAmount = calculateTotalAmount();

        // Calculate total with GST if needed
        double payableAmount = includeGST ? calculateTotalWithGST(baseAmount) : baseAmount;

        InvoiceGenerator.generateInvoice(customerName, customerAddress, cart, payableAmount,
                companyName, companyEmail, companyPhone, companyWebsite, logoPath, includeGST);
    }

    private double calculateTotalAmount() {
        double totalAmount = 0;
        for (Product product : cart) {
            totalAmount += product.getPrice() * product.getQuantity();
        }
        return totalAmount;
    }

    private double calculateTotalWithGST(double baseAmount) {
        return baseAmount * 1.18; // Assuming 18% GST
    }

    private void updateTotalAmountLabel() {
        double totalAmount = calculateTotalAmount();
        totalAmountLabel.setText("Amount (Before GST): ₹" + String.format("%.2f", totalAmount));
    }

//    private void showInvoice(String customerName, String customerAddress, double payableAmount, boolean includeGST) {
//        String message = String.format("Invoice Details:\nCustomer Name: %s\nCustomer Address: %s\nTotal Amount: ₹%.2f\n(GST %s)\n",
//                customerName, customerAddress, payableAmount, includeGST ? "Included" : "Excluded");
//        JOptionPane.showMessageDialog(frame, message, "Invoice", JOptionPane.INFORMATION_MESSAGE);
//    }

    private void showInvoice(String customerName, String customerAddress, double totalAmount, boolean includeGST) {
        StringBuilder invoiceBuilder = new StringBuilder();

        // Company Header
        invoiceBuilder.append("Company: ").append(companyName).append("\n");
        invoiceBuilder.append("Address: 704, Millennium ATLAS , Near Vission Mall Road,\n" +
                "\n" +
                "Tathawade , Pune-411033 \n");
        invoiceBuilder.append("GSTIN: 27AAACQ9804F1ZE\n");
        invoiceBuilder.append("Email: ").append(companyEmail).append("   Phone: ").append(companyPhone).append("\n");
        invoiceBuilder.append("\n");

        // Customer Details
        invoiceBuilder.append("Customer Details:\n");
        invoiceBuilder.append("Name: ").append(customerName).append("\n");
        invoiceBuilder.append("Address: ").append(customerAddress).append("\n");
        invoiceBuilder.append("GSTIN: Customer GSTIN Here\n");
        invoiceBuilder.append("\n");

        // Invoice and Dispatch Details
        invoiceBuilder.append("Invoice #: ").append("GeneratedInvoiceNumber").append("   Date: ").append(java.time.LocalDate.now()).append("\n");
        invoiceBuilder.append("Place of Supply: PlaceHere\n");
        invoiceBuilder.append("Vehicle Number: VehicleNumberHere\n");
        invoiceBuilder.append("\n");

        // Itemized Table
        invoiceBuilder.append("Item Name     HSN/SAC    Rate/Item     Qty     Taxable Value    IGST     Total\n");
        for (Product product : cart) {
            double lineAmount = product.getPrice() * product.getQuantity();
            invoiceBuilder.append(String.format("%s    %s     %.2f       %d       %.2f     %.2f     %.2f\n",
                    product.getName(), "HSNCodeHere", product.getPrice(), product.getQuantity(), lineAmount, lineAmount * 0.18, lineAmount * 1.18));
        }

        // Tax Summary and Total
        double gstAmount = totalAmount * 0.18;
        double grandTotal = totalAmount + gstAmount;
        invoiceBuilder.append("\nTaxable Amount: ").append(totalAmount).append("\n");
        invoiceBuilder.append("IGST: ").append(gstAmount).append("\n");
        invoiceBuilder.append("Grand Total: ").append(grandTotal).append("\n\n");

        // Bank Details
        invoiceBuilder.append("Bank: BankNameHere   Account #: AccountNumberHere\n");
        invoiceBuilder.append("IFSC: IFSCCodeHere   Branch: BranchNameHere\n");
        invoiceBuilder.append("\n");

        // QR Code Placeholder for UPI Payment
        invoiceBuilder.append("[QR Code for Payment]\n\n");

        // Footer with Terms and Conditions
        invoiceBuilder.append("Notes:\n");
        invoiceBuilder.append("1. Goods sold cannot be taken back or exchanged.\n");
        invoiceBuilder.append("2. We are not the manufacturers; warranty is as per terms.\n");
        invoiceBuilder.append("3. Interest @24% p.a. for unpaid bills beyond 15 days.\n");
        invoiceBuilder.append("4. Subject to jurisdiction.\n\n");

        invoiceBuilder.append("Authorized Signatory: [Signature Here]\n");

        JOptionPane.showMessageDialog(frame, invoiceBuilder.toString(), "Invoice", JOptionPane.INFORMATION_MESSAGE);
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(BillingApp::new);
    }
}
