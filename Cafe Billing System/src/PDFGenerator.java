package src;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PDFGenerator {

    // Cafe Theme Colors for PDF
    private static final BaseColor PRIMARY_BROWN = new BaseColor(93, 64, 55); // Espresso
    private static final BaseColor LIGHT_CREAM = new BaseColor(250, 246, 240); // Latte Background
    private static final BaseColor ACCENT_COLOR = new BaseColor(180, 140, 100); // Gold/Light Brown
    private static final BaseColor TEXT_DARK = new BaseColor(62, 39, 35);

    public static String generateReceipt(List<MenuItem> items, BillCalculator calculator, String customerName, String customerPhone) {
        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        String fileName = downloadsPath + File.separator + "CafeReceipt_" + timestamp + ".pdf";

        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD, PRIMARY_BROWN);
            Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.ITALIC, ACCENT_COLOR);
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, TEXT_DARK);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, TEXT_DARK);
            
            Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            Font tableRowFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, TEXT_DARK);
            
            Font totalHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, TEXT_DARK);
            Font grandTotalFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, PRIMARY_BROWN);
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.WHITE);

            // ================= HEADER SECTION =================
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            try {
                headerTable.setWidths(new float[]{1.5f, 4f});
            } catch (Exception e) {}

            // Logo Cell
            PdfPCell logoCell = new PdfPCell();
            logoCell.setBorder(Rectangle.NO_BORDER);
            try {
                Image logo = Image.getInstance("images/logo.png");
                logo.scaleToFit(90, 90);
                logo.setAlignment(Element.ALIGN_CENTER);
                logoCell.addElement(logo);
            } catch (Exception e) {
                logoCell.addElement(new Paragraph("LOGO", titleFont));
            }
            headerTable.addCell(logoCell);

            // Title Cell
            PdfPCell titleCell = new PdfPCell();
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            Paragraph title = new Paragraph("THE RUSTIC BEANS CAFE", titleFont);
            Paragraph subtitle = new Paragraph("Premium Coffee, Fresh Bakes & Good Times", subtitleFont);
            Paragraph address = new Paragraph("123 Coffee Lane, Brew City | +1 234 567 8900", valueFont);
            
            titleCell.addElement(title);
            titleCell.addElement(subtitle);
            titleCell.addElement(address);
            headerTable.addCell(titleCell);

            document.add(headerTable);

            // Divider Line
            PdfPTable line = new PdfPTable(1);
            line.setWidthPercentage(100);
            line.setSpacingBefore(15f);
            line.setSpacingAfter(15f);
            PdfPCell lineCell = new PdfPCell(new Phrase(""));
            lineCell.setBorder(Rectangle.BOTTOM);
            lineCell.setBorderColorBottom(ACCENT_COLOR);
            lineCell.setBorderWidthBottom(2f);
            line.addCell(lineCell);
            document.add(line);

            // ================= CUSTOMER & INVOICE DETAILS =================
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            
            // Left column (Customer)
            PdfPCell customerCell = new PdfPCell();
            customerCell.setBorder(Rectangle.NO_BORDER);
            customerCell.addElement(new Phrase("BILL TO:", labelFont));
            customerCell.addElement(new Phrase(customerName.isEmpty() ? "Walk-in Customer" : customerName.toUpperCase(), valueFont));
            if (!customerPhone.isEmpty()) {
                customerCell.addElement(new Phrase("Ph: " + customerPhone, valueFont));
            }
            infoTable.addCell(customerCell);
            
            // Right column (Invoice Info)
            PdfPCell invoiceCell = new PdfPCell();
            invoiceCell.setBorder(Rectangle.NO_BORDER);
            invoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            SimpleDateFormat displayFormat = new SimpleDateFormat("MMM dd, yyyy  hh:mm a");
            Paragraph receiptNo = new Paragraph("Receipt #: INV-" + timestamp.substring(8), labelFont);
            receiptNo.setAlignment(Element.ALIGN_RIGHT);
            Paragraph dateP = new Paragraph("Date: " + displayFormat.format(new Date()), valueFont);
            dateP.setAlignment(Element.ALIGN_RIGHT);
            
            invoiceCell.addElement(receiptNo);
            invoiceCell.addElement(dateP);
            infoTable.addCell(invoiceCell);

            document.add(infoTable);
            document.add(new Paragraph("\n"));

            // ================= ITEMS TABLE =================
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            try {
                table.setWidths(new float[]{4.5f, 1.5f, 1f, 2f});
            } catch(Exception e) {}

            // Table Headers (Colored)
            addTableHeader(table, "ITEM DESCRIPTION", tableHeaderFont);
            addTableHeader(table, "PRICE", tableHeaderFont);
            addTableHeader(table, "QTY", tableHeaderFont);
            addTableHeader(table, "TOTAL", tableHeaderFont);

            // Table Rows with Alternate Colors
            boolean isCreamRow = true;
            for (MenuItem item : items) {
                if (item.isSelected() && item.getQuantity() > 0) {
                    BaseColor rowColor = isCreamRow ? LIGHT_CREAM : BaseColor.WHITE;
                    
                    addTableRow(table, item.getName(), tableRowFont, rowColor, Element.ALIGN_LEFT);
                    addTableRow(table, "Rs. " + String.format("%.2f", item.getPrice()), tableRowFont, rowColor, Element.ALIGN_CENTER);
                    addTableRow(table, String.valueOf(item.getQuantity()), tableRowFont, rowColor, Element.ALIGN_CENTER);
                    addTableRow(table, "Rs. " + String.format("%.2f", item.getTotalPrice()), tableRowFont, rowColor, Element.ALIGN_RIGHT);
                    
                    isCreamRow = !isCreamRow; // Alternate color
                }
            }
            document.add(table);

            // ================= TOTALS SECTION =================
            PdfPTable totalsTable = new PdfPTable(2);
            totalsTable.setWidthPercentage(40);
            totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalsTable.setSpacingBefore(15f);

            // Subtotal
            addTotalRow(totalsTable, "Subtotal:", "Rs. " + String.format("%.2f", calculator.getSubtotal()), totalHeaderFont, valueFont);
            // Tax
            addTotalRow(totalsTable, "Tax (5%):", "Rs. " + String.format("%.2f", calculator.getTax()), totalHeaderFont, valueFont);
            // Service Charge
            addTotalRow(totalsTable, "Service (10%):", "Rs. " + String.format("%.2f", calculator.getServiceCharge()), totalHeaderFont, valueFont);

            // Grand Total (Highlighted Row)
            PdfPCell grandTotalLabel = new PdfPCell(new Phrase("GRAND TOTAL:", grandTotalFont));
            grandTotalLabel.setBorder(Rectangle.TOP);
            grandTotalLabel.setBorderWidthTop(2f);
            grandTotalLabel.setBorderColorTop(PRIMARY_BROWN);
            grandTotalLabel.setPaddingTop(10f);
            grandTotalLabel.setPaddingBottom(5f);
            
            PdfPCell grandTotalValue = new PdfPCell(new Phrase("Rs. " + String.format("%.2f", calculator.getGrandTotal()), grandTotalFont));
            grandTotalValue.setBorder(Rectangle.TOP);
            grandTotalValue.setBorderWidthTop(2f);
            grandTotalValue.setBorderColorTop(PRIMARY_BROWN);
            grandTotalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
            grandTotalValue.setPaddingTop(10f);
            grandTotalValue.setPaddingBottom(5f);

            totalsTable.addCell(grandTotalLabel);
            totalsTable.addCell(grandTotalValue);

            document.add(totalsTable);

            // ================= FOOTER =================
            document.add(new Paragraph("\n\n\n"));
            
            PdfPTable footerTable = new PdfPTable(1);
            footerTable.setWidthPercentage(100);
            PdfPCell footerCell = new PdfPCell(new Phrase("Thank you for visiting! Have a wonderful day.", footerFont));
            footerCell.setBackgroundColor(PRIMARY_BROWN);
            footerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            footerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            footerCell.setPadding(15f);
            footerCell.setBorder(Rectangle.NO_BORDER);
            
            footerTable.addCell(footerCell);
            document.add(footerTable);

            document.close();
            System.out.println("Receipt generated: " + fileName);
            return fileName;

        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            return null;
        }
    }

    private static void addTableHeader(PdfPTable table, String headerTitle, Font font) {
        PdfPCell header = new PdfPCell();
        header.setPhrase(new Phrase(headerTitle, font));
        header.setBackgroundColor(PRIMARY_BROWN);
        header.setBorder(Rectangle.NO_BORDER);
        header.setPaddingBottom(8f);
        header.setPaddingTop(8f);
        if (headerTitle.equals("ITEM DESCRIPTION")) {
            header.setHorizontalAlignment(Element.ALIGN_LEFT);
            header.setPaddingLeft(10f);
        } else if (headerTitle.equals("TOTAL")) {
            header.setHorizontalAlignment(Element.ALIGN_RIGHT);
            header.setPaddingRight(10f);
        } else {
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        table.addCell(header);
    }

    private static void addTableRow(PdfPTable table, String text, Font font, BaseColor bgColor, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingBottom(8f);
        cell.setPaddingTop(8f);
        cell.setHorizontalAlignment(alignment);
        if (alignment == Element.ALIGN_LEFT) cell.setPaddingLeft(10f);
        if (alignment == Element.ALIGN_RIGHT) cell.setPaddingRight(10f);
        table.addCell(cell);
    }

    private static void addTotalRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPaddingBottom(5f);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        valueCell.setPaddingBottom(5f);
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}
