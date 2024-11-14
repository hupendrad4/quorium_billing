//package billing;
//
//import com.itextpdf.kernel.colors.ColorConstants;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.borders.Border;
//import com.itextpdf.layout.borders.SolidBorder;
//import com.itextpdf.layout.element.*;
//import com.itextpdf.layout.property.TextAlignment;
//import com.itextpdf.layout.property.UnitValue;
//import com.itextpdf.io.image.ImageData;
//import com.itextpdf.io.image.ImageDataFactory;
//
//import java.io.FileOutputStream;
//import java.util.List;
//
//public class InvoiceGenerator {
//
//    private static final double GST_RATE = 0.05;
//    private static final double CGST_RATE = 0.025;
//    private static final double SGST_RATE = 0.025;
//
////    public static void generateInvoice(String customerName, String customerAddress, List<Product> purchasedProducts, double baseAmount,
////                                       String companyName, String companyEmail, String companyPhone, String companyWebsite, String logoPath, boolean includeGST) {
////        String pdfPath = System.getProperty("user.home") + "/Downloads/Invoice_" + customerName + ".pdf";
////
////        try {
////            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
////            PdfDocument pdfDoc = new PdfDocument(writer);
////            Document document = new Document(pdfDoc);
////
////            addHeader(document, companyName, companyEmail, companyPhone, companyWebsite, logoPath);
////            addCustomerInfo(document, customerName, customerAddress);
////            addProductTable(document, purchasedProducts, includeGST);
////            addQrCode(document);
////            addFooter(document, companyName, companyEmail, companyPhone, companyWebsite);
////
////            document.close();
////            System.out.println("Invoice generated successfully: " + pdfPath);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
////    public static void generateInvoice(String customerName, String customerAddress, List<Product> purchasedProducts, double baseAmount,
////                                       String companyName, String companyEmail, String companyPhone, String companyWebsite, String logoPath, boolean includeGST) {
////        String pdfPath = System.getProperty("user.home") + "/Downloads/Invoice_" + customerName + ".pdf";
////
////        try {
////            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
////            PdfDocument pdfDoc = new PdfDocument(writer);
////            Document document = new Document(pdfDoc);
////
////            // Add Company Header
////            document.add(new Paragraph("Company: " + companyName));
////            document.add(new Paragraph("Address: 704, Millennium ATLAS, Near Vission Mall Road, Tathawade, Pune-411033"));
////            document.add(new Paragraph("GSTIN: 27AAACQ9804F1ZE"));
////            document.add(new Paragraph("Email: " + companyEmail + "   Phone: " + companyPhone));
////            document.add(new Paragraph("\n")); // Add a blank line
////
////            // Add Customer Details
////            document.add(new Paragraph("Customer Details:"));
////            document.add(new Paragraph("Name: " + customerName));
////            document.add(new Paragraph("Address: " + customerAddress));
////            document.add(new Paragraph("GSTIN: Customer GSTIN Here"));
////            document.add(new Paragraph("\n")); // Add a blank line
////
////            // Invoice and Dispatch Details
////            document.add(new Paragraph("Invoice #: GeneratedInvoiceNumber   Date: " + java.time.LocalDate.now()));
////            document.add(new Paragraph("Place of Supply: PlaceHere"));
////            document.add(new Paragraph("Vehicle Number: VehicleNumberHere"));
////            document.add(new Paragraph("\n")); // Add a blank line
////
////            // Add Itemized Table
////            Table table = new Table(6); // Create a table with 6 columns
////            table.addHeaderCell("Item Name");
////            table.addHeaderCell("HSN/SAC");
////            table.addHeaderCell("Rate/Item");
////            table.addHeaderCell("Qty");
////            table.addHeaderCell("Taxable Value");
////            table.addHeaderCell("Total");
////
////            double totalAmount = 0.0;
////
////            for (Product product : purchasedProducts) {
////                double lineAmount = product.getPrice() * product.getQuantity();
////                totalAmount += lineAmount; // Calculate total amount for tax summary
////                table.addCell(product.getName());
////                table.addCell("HSNCodeHere");
////                table.addCell(String.format("%.2f", product.getPrice()));
////                table.addCell(String.valueOf(product.getQuantity()));
////                table.addCell(String.format("%.2f", lineAmount));
////                table.addCell(String.format("%.2f", lineAmount * 1.18)); // Assuming 18% GST included in total
////            }
////
////            document.add(table);
////
////            // Tax Summary and Total
////            double gstAmount = totalAmount * 0.18; // Assuming 18% GST
////            double grandTotal = totalAmount + gstAmount;
////
////            document.add(new Paragraph("\nTaxable Amount: " + totalAmount));
////            document.add(new Paragraph("IGST: " + gstAmount));
////            document.add(new Paragraph("Grand Total: " + grandTotal));
////            document.add(new Paragraph("\n")); // Add a blank line
////
////            // Bank Details
////            document.add(new Paragraph("Bank: BankNameHere   Account #: AccountNumberHere"));
////            document.add(new Paragraph("IFSC: IFSCCodeHere   Branch: BranchNameHere"));
////            document.add(new Paragraph("\n")); // Add a blank line
////
////            // QR Code Placeholder for UPI Payment
////            document.add(new Paragraph("[QR Code for Payment]"));
////            document.add(new Paragraph("\n")); // Add a blank line
////
////            // Footer with Terms and Conditions
////            document.add(new Paragraph("Notes:"));
////            document.add(new Paragraph("1. Goods sold cannot be taken back or exchanged."));
////            document.add(new Paragraph("2. We are not the manufacturers; warranty is as per terms."));
////            document.add(new Paragraph("3. Interest @24% p.a. for unpaid bills beyond 15 days."));
////            document.add(new Paragraph("4. Subject to jurisdiction."));
////            document.add(new Paragraph("\n")); // Add a blank line
////
////            document.add(new Paragraph("Authorized Signatory: [Signature Here]"));
////
////            document.close();
////            System.out.println("Invoice generated successfully: " + pdfPath);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//
//    public static void generateInvoice(String customerName, String customerAddress, List<Product> purchasedProducts, double baseAmount,
//                                       String companyName, String companyEmail, String companyPhone, String companyWebsite, String logoPath, boolean includeGST) {
//        String pdfPath = System.getProperty("user.home") + "/Downloads/Invoice_" + customerName + ".pdf";
//
//        try {
//            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
//            PdfDocument pdfDoc = new PdfDocument(writer);
//            Document document = new Document(pdfDoc);
//
//            // Existing header method
//            addHeader(document, companyName, companyEmail, companyPhone, companyWebsite, logoPath);
//
//            // New methods to add customer info and invoice details
//            addCustomerInfo(document, customerName, customerAddress);
//            addInvoiceDetails(document, purchasedProducts, includeGST); // New method to add product table and totals
//
//            // Existing QR Code and Footer methods
//            addQrCode(document);
//            addFooter(document, companyName, companyEmail, companyPhone, companyWebsite);
//
//            document.close();
//            System.out.println("Invoice generated successfully: " + pdfPath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void addInvoiceDetails(Document document, List<Product> purchasedProducts, boolean includeGST) {
//        // Add Invoice and Dispatch Details
//        document.add(new Paragraph("Invoice #: GeneratedInvoiceNumber   Date: " + java.time.LocalDate.now()));
//        document.add(new Paragraph("Place of Supply: PlaceHere")); // Placeholder for Place of Supply
//        document.add(new Paragraph("Vehicle Number: VehicleNumberHere")); // Placeholder for Vehicle Number
//        document.add(new Paragraph("\n")); // Add a blank line
//
//        // Add Itemized Table
//        Table table = new Table(6); // Create a table with 6 columns
//        table.addHeaderCell("Item Name");
//        table.addHeaderCell("HSN/SAC");
//        table.addHeaderCell("Rate/Item");
//        table.addHeaderCell("Qty");
//        table.addHeaderCell("Taxable Value");
//        table.addHeaderCell("Total");
//
//        double totalAmount = 0.0;
//
//        for (Product product : purchasedProducts) {
//            double lineAmount = product.getPrice() * product.getQuantity();
//            totalAmount += lineAmount; // Calculate total amount for tax summary
//            table.addCell(product.getName());
//            table.addCell("HSNCodeHere"); // Placeholder for HSN Code
//            table.addCell(String.format("%.2f", product.getPrice()));
//            table.addCell(String.valueOf(product.getQuantity()));
//            table.addCell(String.format("%.2f", lineAmount));
//            table.addCell(String.format("%.2f", lineAmount * (includeGST ? 1.18 : 1.0))); // Include GST if applicable
//        }
//
//        document.add(table);
//
//        // Tax Summary and Total
//        double gstAmount = totalAmount * 0.18; // Assuming 18% GST
//        double grandTotal = totalAmount + gstAmount;
//
//        document.add(new Paragraph("\nTaxable Amount: " + totalAmount));
//        document.add(new Paragraph("IGST: " + gstAmount));
//        document.add(new Paragraph("Grand Total: " + grandTotal));
//        document.add(new Paragraph("\n")); // Add a blank line
//
//        // Bank Details
//        document.add(new Paragraph("Bank: BankNameHere   Account #: AccountNumberHere")); // Placeholder for bank details
//        document.add(new Paragraph("IFSC: IFSCCodeHere   Branch: BranchNameHere")); // Placeholder for IFSC and Branch
//        document.add(new Paragraph("\n")); // Add a blank line
//
//        // QR Code Placeholder for UPI Payment
//        document.add(new Paragraph("[QR Code for Payment]"));
//        document.add(new Paragraph("\n")); // Add a blank line
//
//        // Footer with Terms and Conditions
//        document.add(new Paragraph("Notes:"));
//        document.add(new Paragraph("1. Goods sold cannot be taken back or exchanged."));
//        document.add(new Paragraph("2. We are not the manufacturers; warranty is as per terms."));
//        document.add(new Paragraph("3. Interest @24% p.a. for unpaid bills beyond 15 days."));
//        document.add(new Paragraph("4. Subject to jurisdiction."));
//        document.add(new Paragraph("\n")); // Add a blank line
//
//        document.add(new Paragraph("Authorized Signatory: [Signature Here]")); // Placeholder for signature
//    }
//
//
//    private static void addHeader(Document document, String companyName, String companyEmail, String companyPhone, String companyWebsite, String logoPath) {
//        Table headerTable = new Table(2);
//        headerTable.setWidth(UnitValue.createPercentValue(100));
//        headerTable.setMarginBottom(10f);
//
//        try {
//            ImageData logoData = ImageDataFactory.create(logoPath);
//            Image logo = new Image(logoData).setWidth(100);
//            headerTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
//        } catch (Exception e) {
//            System.out.println("Logo not found or failed to load: " + e.getMessage());
//        }
//
//        Cell companyInfoCell = new Cell()
//                .add(new Paragraph(companyName).setBold())
//                .add(new Paragraph("Email: " + companyEmail))
//                .add(new Paragraph("Phone: " + companyPhone))
//                .add(new Paragraph("Website: " + companyWebsite))
//                .setBorder(Border.NO_BORDER)
//                .setTextAlignment(TextAlignment.RIGHT);
//
//        headerTable.addCell(companyInfoCell);
//        document.add(headerTable);
//    }
//
//    private static void addCustomerInfo(Document document, String customerName, String customerAddress) {
//        document.add(new Paragraph("Invoice").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
//        document.add(new Paragraph("Customer Name: " + customerName));
//        document.add(new Paragraph("Customer Address: " + customerAddress));
//        document.add(new Paragraph(" "));
//    }
//
//    private static void addProductTable(Document document, List<Product> purchasedProducts, boolean includeGST) {
//        Table productTable = new Table(new float[]{4, 2, 2, 2});
//        productTable.setWidth(UnitValue.createPercentValue(100));
//        productTable.addHeaderCell(new Cell().add(new Paragraph("Product Name")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
//        productTable.addHeaderCell(new Cell().add(new Paragraph("Price")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
//        productTable.addHeaderCell(new Cell().add(new Paragraph("Quantity")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
//        productTable.addHeaderCell(new Cell().add(new Paragraph("Total")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
//
//        double totalProductAmount = 0;
//
//        for (Product product : purchasedProducts) {
//            double productTotal = product.getPrice() * product.getQuantity();
//            productTable.addCell(new Cell().add(new Paragraph(product.getName())));
//            productTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", product.getPrice()))));
//            productTable.addCell(new Cell().add(new Paragraph(String.valueOf(product.getQuantity()))));
//            productTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", productTotal))));
//            totalProductAmount += productTotal;
//        }
//
//        double cgstAmount = includeGST ? totalProductAmount * CGST_RATE : 0;
//        double sgstAmount = includeGST ? totalProductAmount * SGST_RATE : 0;
//        double totalAmount = totalProductAmount + cgstAmount + sgstAmount;
//
//        productTable.addCell(new Cell(1, 3).add(new Paragraph("Amount (Before GST):")).setTextAlignment(TextAlignment.RIGHT));
//        productTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", totalProductAmount))));
//
//        if (includeGST) {
//            productTable.addCell(new Cell(1, 3).add(new Paragraph("CGST (2.5%):")).setTextAlignment(TextAlignment.RIGHT));
//            productTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", cgstAmount))));
//            productTable.addCell(new Cell(1, 3).add(new Paragraph("SGST (2.5%):")).setTextAlignment(TextAlignment.RIGHT));
//            productTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", sgstAmount))));
//            productTable.addCell(new Cell(1, 3).add(new Paragraph("Total Amount (With GST):")).setTextAlignment(TextAlignment.RIGHT));
//        } else {
//            productTable.addCell(new Cell(1, 3).add(new Paragraph("Total Amount:")).setTextAlignment(TextAlignment.RIGHT));
//        }
//        productTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", totalAmount))));
//
//        document.add(productTable);
//    }
//
//    private static void addQrCode(Document document) {
//        document.add(new Paragraph(" "));
//        try {
//            String qrPath = "/home/aumni/IdeaProjects/untitled/src/main/resources/QR_Code.png";
//            ImageData qrImage = ImageDataFactory.create(qrPath);
//            Image qrCode = new Image(qrImage).setWidth(100);
//            document.add(new Paragraph("Scan QR Code for Payment:"));
//            document.add(qrCode);
//        } catch (Exception e) {
//            System.out.println("QR code not found or failed to load: " + e.getMessage());
//        }
//    }
//
//    private static void addFooter(Document document, String companyName, String companyEmail, String companyPhone, String companyWebsite) {
//        document.add(new Paragraph(" "));
//        Table footerTable = new Table(1);
//        footerTable.setWidth(UnitValue.createPercentValue(100));
//        footerTable.addCell(new Cell()
//                .add(new Paragraph(companyName).setBold())
//                .add(new Paragraph("Email: " + companyEmail))
//                .add(new Paragraph("Phone: " + companyPhone))
//                .add(new Paragraph("Website: " + companyWebsite))
//                .setTextAlignment(TextAlignment.CENTER)
//                .setBorderTop(new SolidBorder(ColorConstants.GRAY, 1)));
//
//        document.add(footerTable);
//    }
//}
