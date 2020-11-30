package com.example.cardealer.utils;

import com.example.cardealer.documents.entiity.Invoice;
import com.example.cardealer.repairs.entity.Part;
import com.example.cardealer.repairs.entity.Repair;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GeneratePdfDocument {

    BaseFont baseFont;
    Font font = new Font(baseFont, 14);

    {
        try {
            baseFont = BaseFont.createFont("Helvetica", "Cp1250", true);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void pdfRepairReport(HttpServletResponse response, Repair repair) {
        String documentNumber = repair.getId() + "/" + repair.getRepairDate();
        String modelOfCar = repair.getCar().getMark() + " " + repair.getCar().getModel();
        String bodyNumberOfCar = repair.getCar().getBodyNumber();
        String prodYearOfCar = repair.getCar().getProductionYear().toString();
        String distanceOfCar = repair.getCar().getDistance().toString();
        String repairDescription = repair.getRepairDescription();
        String repairAmount = repair.getRepairAmount().toString();
        Collection<String> partsOfRepair = repair.getParts().stream().map(Part::getPartName).collect(Collectors.toList());
        String workerOfService = repair.getEmployee().getFirstName() + " " + repair.getEmployee().getLastName();
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph(new Phrase("Numer dokumentu: " + documentNumber, font)));
            document.add(new Paragraph(new Phrase("Pojazd: " + modelOfCar, font)));
            document.add(new Paragraph(new Phrase("Numer nadwozia: " + bodyNumberOfCar, font)));
            document.add(new Paragraph(new Phrase("Rok produkcji: " + prodYearOfCar, font)));
            document.add(new Paragraph(new Phrase("Przebieg: " + distanceOfCar, font)));
            document.add(new Paragraph(new Phrase("Opis naprawy: " + repairDescription, font)));
            document.add(new Paragraph(new Phrase("Części: " + partsOfRepair + ", ", font)));
            document.add(new Paragraph(new Phrase("Wartość naprawy: " + repairAmount, font)));
            document.add(new Paragraph(new Phrase("Mechanik: " + workerOfService, font)));
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void pdfInvoice(HttpServletResponse response, Invoice invoice) {
        String documentNumber = invoice.getInvoiceNumber();
        BigDecimal invoiceAmount = invoice.getInvoiceAmount();
        String employeeName = invoice.getEmployee().getFirstName() + " " + invoice.getEmployee().getLastName();
        String createdAt = invoice.getCreatedAt().toString();
        String typeInvoice = invoice.getTransaction().getType();
        String positionInvoice = invoice.getAgreement().getContent();
        String customer = invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName();
        String customerAddress = invoice.getCustomer().getAddress();
        String customerPesel = invoice.getCustomer().getPesel();
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph(new Phrase("Faktura " + typeInvoice, font)));
            document.add(new Paragraph(new Phrase("Data dokumentu: " + createdAt, font)));
            document.add(new Paragraph(new Phrase("Numer: " + documentNumber, font)));
            document.add(new Paragraph(new Phrase("Odbiorca/Płatnik: " + customer, font)));
            document.add(new Paragraph(new Phrase("Pesel: " + customerPesel, font)));
            document.add(new Paragraph(new Phrase("Adres: " + customerAddress, font)));
            document.add(new Paragraph(new Phrase("Pozycja dokumentu: " + positionInvoice, font)));
            document.add(new Paragraph(new Phrase("Kwota faktury: " + invoiceAmount, font)));
            document.add(new Paragraph(new Phrase("Dokument wystawił: " + employeeName, font)));
            document.add(new Paragraph(new Phrase("Dokument odebrał: " + customer, font)));
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
