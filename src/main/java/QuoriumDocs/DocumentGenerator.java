package QuoriumDocs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class DocumentGenerator extends JFrame {

    // UI Components
    private JTextField nameField, dobField, designationField, joiningDateField, lastWorkingDateField;
    private JButton generateButton;
    private JComboBox<String> docTypeCombo;

    // Color scheme
    private static final BaseColor PRIMARY_COLOR = new BaseColor(0, 0, 77);     // #00004d
    private static final BaseColor ACCENT_COLOR = new BaseColor(219, 148, 33);  // #DB9421
    private static final BaseColor BLACK_COLOR = BaseColor.BLACK;
    private static final BaseColor LIGHT_GRAY = new BaseColor(230, 230, 230);   // Light gray for background

    public DocumentGenerator() {
        setTitle("Quorium Technologies Document Generator");
        setLayout(new GridLayout(7, 2, 10, 10));

        // Input Fields
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Date of Birth (dd-MM-yyyy):"));
        dobField = new JTextField();
        add(dobField);

        add(new JLabel("Designation:"));
        designationField = new JTextField();
        add(designationField);

        add(new JLabel("Joining Date (dd-MM-yyyy):"));
        joiningDateField = new JTextField();
        add(joiningDateField);

        add(new JLabel("Last Working Date (dd-MM-yyyy):"));
        lastWorkingDateField = new JTextField();
        add(lastWorkingDateField);

        // Document Type Selection
        add(new JLabel("Document Type:"));
        String[] docTypes = {"Offer Letter", "Appointment Letter", "Internship Offer Letter", "Internship Completion Letter", "Salary Slip", "Experience & Relieving Letter"};
        docTypeCombo = new JComboBox<>(docTypes);
        add(docTypeCombo);

        // Generate Button
        generateButton = new JButton("Generate Document");
        add(generateButton);

        generateButton.addActionListener(new GenerateDocumentAction());

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class GenerateDocumentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String dob = dobField.getText();
            String designation = designationField.getText();
            String joiningDate = joiningDateField.getText();
            String lastWorkingDate = lastWorkingDateField.getText();
            String docType = (String) docTypeCombo.getSelectedItem();

            String companyName = "Quorium Technologies Private Limited";
            String companyAddress = "123 Tech Park, Pune, Maharashtra, India";
            String companyLogoPath = "/home/aumni/IdeaProjects/untitled/src/main/resources/quorium logo.png";
            String companyContact = "info@quorium.com";

            String fileName = name + "_" + docType.replace(" ", "_") + ".pdf";

            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileName));
                document.open();

                // Add Header with Logo
                PdfPTable headerTable = new PdfPTable(1);
                headerTable.setWidthPercentage(100);
                headerTable.getDefaultCell().setBackgroundColor(ACCENT_COLOR);
                headerTable.getDefaultCell().setPadding(10);

                Image logo = Image.getInstance(companyLogoPath);
                logo.scaleToFit(80, 80);
                logo.setAlignment(Element.ALIGN_CENTER);

                PdfPCell logoCell = new PdfPCell(logo);
                logoCell.setBorder(Rectangle.NO_BORDER);
                logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerTable.addCell(logoCell);

                PdfPCell companyNameCell = new PdfPCell(new Phrase(companyName, new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.WHITE)));
                companyNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                companyNameCell.setBorder(Rectangle.NO_BORDER);
                headerTable.addCell(companyNameCell);

                document.add(headerTable);

                // Add Date
                document.add(new Paragraph("\nDate: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "\n\n", new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BLACK_COLOR)));

                // Add a line separator
                LineSeparator separator = new LineSeparator();
                separator.setLineColor(PRIMARY_COLOR);
                document.add(new Chunk(separator));

                // Document Body Based on Type with light gray background
                PdfPTable bodyTable = new PdfPTable(1);
                bodyTable.setWidthPercentage(100);
                bodyTable.getDefaultCell().setBackgroundColor(LIGHT_GRAY);
                bodyTable.getDefaultCell().setPadding(10);

                String bodyContent = generateBodyContent(docType, name, designation, joiningDate, lastWorkingDate, companyName);
                PdfPCell bodyCell = new PdfPCell(new Phrase(bodyContent, new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BLACK_COLOR)));
                bodyCell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(bodyCell);

                document.add(bodyTable);

                // Add Footer
                document.add(new Chunk(separator));

                Paragraph footer = new Paragraph(companyAddress + "\n" + "Contact: " + companyContact,
                        new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, PRIMARY_COLOR));
                footer.setAlignment(Element.ALIGN_CENTER);
                document.add(footer);

                document.close();

                JOptionPane.showMessageDialog(null, docType + " generated and saved as " + fileName);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error generating document: " + ex.getMessage());
            }
        }

        private String generateBodyContent(String docType, String name, String designation, String joiningDate, String lastWorkingDate, String companyName) {
            switch (docType) {
                case "Offer Letter":
                    return "Dear " + name + ",\n\nWe are pleased to offer you the position of " + designation + " at " + companyName + ".\nYour joining date will be " + joiningDate + ".\n\nBest Regards,\n" + companyName;
                case "Appointment Letter":
                    return "Dear " + name + ",\n\nWe are delighted to confirm your appointment as " + designation + " effective from " + joiningDate + ".\n\nSincerely,\n" + companyName;
                case "Internship Offer Letter":
                    return "Dear " + name + ",\n\nWe are happy to offer you an internship as " + designation + " at " + companyName + ".\nYour start date will be " + joiningDate + ".\n\nRegards,\n" + companyName;
                case "Internship Completion Letter":
                    return "Dear " + name + ",\n\nCongratulations on successfully completing your internship with us.\nYour last working day was " + lastWorkingDate + ".\n\nBest of luck,\n" + companyName;
                case "Salary Slip":
                    return "Salary Slip for " + name + "\nDesignation: " + designation + "\nJoining Date: " + joiningDate + "\n\nSincerely,\n" + companyName;
                case "Experience & Relieving Letter":
                    return "Dear " + name + ",\n\nThis is to certify that " + name + " worked with " + companyName + " as " + designation + " from " + joiningDate + " to " + lastWorkingDate + ".\n\nWe wish " + name + " all the best in their future endeavors.\n\nSincerely,\n" + companyName;
                default:
                    return "";
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DocumentGenerator::new);
    }
}
