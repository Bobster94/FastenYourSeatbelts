package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.in;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author Bas
 */
public class pdfController {
    
    private PDDocument document;
    private PDPageContentStream contentStream;
    
    public pdfController() {
        try {
            //Create new pdf document in A4 format
            document = new PDDocument();
            PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
            document.addPage(page);

            // Start a new contentstream which will hold all the data
            contentStream = new PDPageContentStream(document, page);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void createPdf(
            String[] labelsCustomer, String[] contentCustomer, 
            String[] labelsLuggage, String[] contentLuggage, 
            String status) {
        try {
            PDType1Font timesBold = PDType1Font.TIMES_BOLD;
            PDType1Font helvetiaBold = PDType1Font.HELVETICA_BOLD;
            PDType1Font helvetia = PDType1Font.HELVETICA;
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //get current date time with Date()
            Date dateToday = new Date();
            String date = dateFormat.format(dateToday);
            
            // Document header
            contentStream.beginText();
            contentStream.setFont(timesBold, 24);
            contentStream.moveTextPositionByAmount(50, 700);
            contentStream.setNonStrokingColor(150, 0, 0);
            contentStream.drawString("Corendon");
            contentStream.endText();
            
            // Line under document header
            contentStream.drawLine(50, 693, 500, 693);
            
            // Customer Content
            contentStream.beginText();
            contentStream.setFont(helvetiaBold, 24);
            contentStream.moveTextPositionByAmount(50, 660);
            contentStream.setNonStrokingColor(0, 0, 0);
            contentStream.drawString("Customer details:");
            
            contentStream.setFont(helvetia, 20);
            for (int i = 0; i < labelsCustomer.length; i++) {
                contentStream.moveTextPositionByAmount(0, -25);
                contentStream.drawString(labelsCustomer[i]+": " + contentCustomer[i]);
            }
            contentStream.endText();
                      
            // Luggage Content
            contentStream.beginText();
            contentStream.setFont(helvetiaBold, 24);
            contentStream.moveTextPositionByAmount(300, 660);
            contentStream.setNonStrokingColor(0, 0, 0);
            contentStream.drawString("Luggage details:");
            
            contentStream.setFont(helvetia, 20);
            for (int i = 0; i < labelsLuggage.length; i++) {
                contentStream.moveTextPositionByAmount(0, -25);
                contentStream.drawString(labelsLuggage[i]+": " + contentLuggage[i]);
            }
            
            // Status Content
            contentStream.moveTextPositionByAmount(-250, -100);
            contentStream.drawString("Status: "+status);
            contentStream.moveTextPositionByAmount(0, -25);
            contentStream.drawString("Date: "+date);
            contentStream.endText();
            
            // Signature area
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(50, 150);
            contentStream.drawString("Customer Signature");
            
            contentStream.moveTextPositionByAmount(300, 0);
            contentStream.drawString("Corendon Signature");
            contentStream.endText();
            
            contentStream.drawLine(50,100,250,100);
            contentStream.drawLine(350,100,550,100); 
            
        } catch (IOException ex) {
            Logger.getLogger(pdfController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(String filename) {
        try {
            // Close contentStream
            contentStream.close();

            // Save the document
            document.save(filename);
            document.close();
        } catch (IOException | COSVisitorException ex) {
            Logger.getLogger(pdfController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
