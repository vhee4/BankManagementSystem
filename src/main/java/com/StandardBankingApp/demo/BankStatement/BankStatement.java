package com.StandardBankingApp.demo.BankStatement;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Slf4j
public class BankStatement {
//    static Logger log = LoggerFactory.getLogger(BankStatement.class);
    private static final String FILE = "C:\\Users\\chidinma.afogu\\Documents\\Sample.pdf";
//    float width = 50f;

    public static void generateStatement() throws FileNotFoundException, DocumentException {
        Rectangle rectangle = new Rectangle(PageSize.A4);
        Document document = new Document(rectangle);
        log.info("Setting size of document");
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document,outputStream);
        document.open();
        document.add(new Paragraph("Sample text"));
        document.add(new Chunk("TJA Bank Statement"));
        document.add(new Phrase("\nI want to sleep"));
        document.add(new Paragraph(""));

        Anchor anchor = new Anchor("I-academy website"); // for adding a link
        anchor.setReference("https://africaprudential.com/i-academy/");
        document.add(anchor);

        List orderedList = new List(List.ORDERED);
        orderedList.add(new ListItem("Emperor"));
        orderedList.add(new ListItem("Noah"));
        document.add(orderedList);

        List unorderedList = new List(List.UNORDERED);
        unorderedList.add(new ListItem("testing"));
        unorderedList.add(new ListItem("testing2"));
        document.add(unorderedList);

        log.info("Setting table to 3 columns");
        PdfPTable table = new PdfPTable(3);
        PdfPCell serialNo = new PdfPCell(new Paragraph("S/N"));
        PdfPCell firstnameColumn = new PdfPCell(new Paragraph("First Name"));
        PdfPCell lastnameColumn = new PdfPCell(new Paragraph("Last Name"));

        log.info("Populating list");
        String[] firstNamesArray = {"Adeolu", "Oyin", "Noah"};
        String[] lastNamesArray = {"Oduniyi", "Alasoluyi", "Johnson"};

        table.addCell(serialNo);
        table.addCell(firstnameColumn);
        table.addCell(lastnameColumn);

        for (int i=0; i<firstNamesArray.length; i++){
            PdfPCell serialNo1 = new PdfPCell(new Paragraph(String.valueOf(i+1)));
            PdfPCell firstName = new PdfPCell(new Paragraph(firstNamesArray[i]));
            PdfPCell lastName = new PdfPCell(new Paragraph(lastNamesArray[i]));
            table.addCell(serialNo1).setBackgroundColor(BaseColor.BLUE);
            table.addCell(firstName);
            table.addCell(lastName);
        }

        document.add(table);



        document.close();
        log.info("File has been created");

    }

    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        generateStatement();

    }
}
