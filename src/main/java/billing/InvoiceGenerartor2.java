package billing;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.FileOutputStream;
import java.util.List;

class InvoiceGenerator {

    private static final double GST_RATE = 0.05;
    private static final double CGST_RATE = 0.025;
    private static final double SGST_RATE = 0.025;

    public static void generateInvoice(String customerName, String customerAddress, List<Product> purchasedProducts, double baseAmount,
                                       String companyName, String companyEmail, String companyPhone, String companyWebsite, String logoPath, boolean includeGST) {
        String pdfPath = System.getProperty("user.home") + "/Downloads/Invoice_" + customerName + ".pdf";

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Add Company Header
            addHeader(document, companyName, companyEmail, companyPhone, companyWebsite, logoPath);

            // Add Customer Info
            addCustomerInfo(document, customerName, customerAddress);

            // Add Invoice Details
            addInvoiceDetails(document, purchasedProducts, includeGST);

            // Add QR Code and Footer
            addQrCode(document);
            addFooter(document, companyName, companyEmail, companyPhone, companyWebsite);

            document.close();
            System.out.println("Invoice generated successfully: " + pdfPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addHeader(Document document, String companyName, String companyEmail, String companyPhone, String companyWebsite, String logoPath) {
        // Logo and company details
        Table headerTable = new Table(1);
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.setMarginBottom(10f);

        try {
            ImageData logoData = ImageDataFactory.create(logoPath);
            Image logo = new Image(logoData).setWidth(100);
            headerTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        } catch (Exception e) {
            System.out.println("Logo not found or failed to load: " + e.getMessage());
        }

        // Company Information
        Cell companyInfoCell = new Cell()
                .add(new Paragraph(companyName).setBold())
                .add(new Paragraph("Email: " + companyEmail))
                .add(new Paragraph("Phone: " + companyPhone))
                .add(new Paragraph("Website: " + companyWebsite))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER);

        headerTable.addCell(companyInfoCell);
        headerTable.addCell(new Cell().setBorder(new SolidBorder(ColorConstants.GRAY, 1))); // Separator line
        document.add(headerTable);
        document.add(new Paragraph("\n")); // Add space after header
    }

    private static void addCustomerInfo(Document document, String customerName, String customerAddress) {
        document.add(new Paragraph("Invoice").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Customer Name: " + customerName));
        document.add(new Paragraph("Customer Address: " + customerAddress));
        document.add(new Paragraph("\n")); // Add space after customer info
    }

    private static void addInvoiceDetails(Document document, List<Product> purchasedProducts, boolean includeGST) {
        // Add Invoice and Dispatch Details
        document.add(new Paragraph("Invoice #: GeneratedInvoiceNumber   Date: " + java.time.LocalDate.now()));
        document.add(new Paragraph("Place of Supply: PlaceHere")); // Placeholder for Place of Supply
        document.add(new Paragraph("Vehicle Number: VehicleNumberHere")); // Placeholder for Vehicle Number
        document.add(new Paragraph("\n")); // Add a blank line

        // Add Itemized Table
        Table table = new Table(6); // Create a table with 6 columns
        table.addHeaderCell("S.No");
        table.addHeaderCell("Item Name");
        table.addHeaderCell("HSN/SAC");
        table.addHeaderCell("Rate/Item");
        table.addHeaderCell("Qty");
        table.addHeaderCell("Total");

        double totalAmount = 0.0;
        int serialNumber = 1;

        for (Product product : purchasedProducts) {
            double lineAmount = product.getPrice() * product.getQuantity();
            totalAmount += lineAmount; // Calculate total amount for tax summary
            table.addCell(String.valueOf(serialNumber++));
            table.addCell(product.getName());
            table.addCell("HSNCodeHere"); // Placeholder for HSN Code
            table.addCell(String.format("%.2f", product.getPrice()));
            table.addCell(String.valueOf(product.getQuantity()));
            table.addCell(String.format("%.2f", lineAmount)); // Total for this product
        }

        document.add(table);

        // Tax Summary and Total
        double cgstAmount = includeGST ? totalAmount * CGST_RATE : 0;
        double sgstAmount = includeGST ? totalAmount * SGST_RATE : 0;
        double totalWithGST = totalAmount + cgstAmount + sgstAmount;

        // Add Tax Summary in the same format
        Table taxSummaryTable = new Table(2);
        taxSummaryTable.addCell("Taxable Amount:");
        taxSummaryTable.addCell(String.format("%.2f", totalAmount));
        if (includeGST) {
            taxSummaryTable.addCell("CGST (2.5%):");
            taxSummaryTable.addCell(String.format("%.2f", cgstAmount));
            taxSummaryTable.addCell("SGST (2.5%):");
            taxSummaryTable.addCell(String.format("%.2f", sgstAmount));
            taxSummaryTable.addCell("Grand Total (With GST):");
            taxSummaryTable.addCell(String.format("%.2f", totalWithGST));
        } else {
            taxSummaryTable.addCell("Total Amount:");
            taxSummaryTable.addCell(String.format("%.2f", totalAmount));
        }

        document.add(taxSummaryTable);
        document.add(new Paragraph("\n")); // Add space after totals

        // Bank Details
        document.add(new Paragraph("Bank: BankNameHere   Account #: AccountNumberHere")); // Placeholder for bank details
        document.add(new Paragraph("IFSC: IFSCCodeHere   Branch: BranchNameHere")); // Placeholder for IFSC and Branch
        document.add(new Paragraph("\n")); // Add a blank line
    }

    private static void addQrCode(Document document) {
        document.add(new Paragraph(" "));
        try {
            String qrPath = "/home/aumni/IdeaProjects/untitled/src/main/resources/QR_Code.png";
            ImageData qrImage = ImageDataFactory.create(qrPath);
            Image qrCode = new Image(qrImage).setWidth(100);
            document.add(new Paragraph("Scan QR Code for Payment:"));
            document.add(qrCode);
        } catch (Exception e) {
            System.out.println("QR code not found or failed to load: " + e.getMessage());
        }
    }

    private static void addFooter(Document document, String companyName, String companyEmail, String companyPhone, String companyWebsite) {
        document.add(new Paragraph(" "));
        Table footerTable = new Table(1);
        footerTable.setWidth(UnitValue.createPercentValue(100));
        footerTable.addCell(new Cell()
                .add(new Paragraph(companyName).setBold())
                .add(new Paragraph("Email: " + companyEmail))
                .add(new Paragraph("Phone: " + companyPhone))
                .add(new Paragraph("Website: " + companyWebsite))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorderTop(new SolidBorder(ColorConstants.GRAY, 1)));

        document.add(footerTable);
    }
}
