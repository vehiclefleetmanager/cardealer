package com.example.cardealer.utils;

import com.example.cardealer.repairs.entity.Part;
import com.example.cardealer.repairs.entity.Repair;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GeneratePdfDocument {

    public void pdfRepair(HttpServletResponse response, Repair repair) {
        String documentNumber = repair.getId() + "/" + repair.getRepairDate();
        String modelOfCar = repair.getCar().getMark() + " " + repair.getCar().getModel();
        String bodyNumberOfCar = repair.getCar().getBodyNumber();
        String prodYearOfCar = repair.getCar().getProductionYear().toString();
        String distanceOfCar = repair.getCar().getDistance().toString();
        String repairDescription = repair.getRepairDescription();
        String repairAmount = repair.getRepairAmount().toString();
        String workerOfService = repair.getEmployee().getFirstName() + " " + repair.getEmployee().getLastName();

        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph(new Phrase("Numer dokumentu: " + documentNumber)));
            document.add(new Paragraph(new Phrase("Pojazd: " + modelOfCar)));
            document.add(new Paragraph(new Phrase("Numer nadwozia: " + bodyNumberOfCar)));
            document.add(new Paragraph(new Phrase("Rok produkcji: " + prodYearOfCar)));
            document.add(new Paragraph(new Phrase("Przebieg: " + distanceOfCar)));
            document.add(new Paragraph(new Phrase("Opis naprawy: " + repairDescription)));
            document.add(new Paragraph(new Phrase("Cześci: " + partsOfRepair(repair))));
            document.add(new Paragraph(new Phrase("Wartość naprawy: " + repairAmount)));
            document.add(new Paragraph(new Phrase("Mechanik: " + workerOfService)));
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder partsOfRepair(Repair repair) {
        Collection<Part> tempPartCollection = Arrays.asList(
                new Part("łożysko", new BigDecimal("20")),
                new Part("wachacz", new BigDecimal("350")),
                new Part("sworzeń", new BigDecimal("140"))
        );
        repair.setParts(tempPartCollection);
        StringBuilder partsOfRepair = new StringBuilder();
        List<String> namesOfParts = repair.getParts().stream().map(Part::getPartName).collect(Collectors.toList());
        if (!namesOfParts.isEmpty()) {
            for (int part = 0; part <= namesOfParts.size() - 1; part++) {
                partsOfRepair.append(
                        namesOfParts.get(part).concat(" "));
            }
        }
        return partsOfRepair;
    }
}
