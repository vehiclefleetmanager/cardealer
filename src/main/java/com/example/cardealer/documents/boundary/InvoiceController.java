package com.example.cardealer.documents.boundary;

import com.example.cardealer.config.Clock;
import com.example.cardealer.documents.control.InvoiceService;
import com.example.cardealer.documents.entiity.Invoice;
import com.example.cardealer.users.control.CurrentUser;
import com.example.cardealer.utils.GeneratePdfDocument;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/invoices")
@AllArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CurrentUser currentUser;
    private final GeneratePdfDocument generatePdfDocument;
    private final Clock clock;

    @GetMapping()
    public String getSystemInvoices(@RequestParam(defaultValue = "0") int page, Model model) {
        if (currentUser.getUser().getRoles().stream().anyMatch(role -> role.getName().matches("CLIENT"))) {
            model.addAttribute("invoices", invoiceService.findAllInvoicesOfUser(currentUser.getUser().getId(), PageRequest.of(page, 10))
                    .stream().map(InvoiceResponse::from).collect(toList()));
            model.addAttribute("pages", invoiceService.findAllInvoicesOfUser(currentUser.getUser().getId(), PageRequest.of(page, 10)));
        } else {
            model.addAttribute("invoices", invoiceService.findAll()
                    .stream().map(InvoiceResponse::from).collect(toList()));
            model.addAttribute("pages", invoiceService.findAll(PageRequest.of(page, 10)));
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "invoices/invoices";
    }

    @RequestMapping(value = "/generate/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public void generatePrintToPdf(@PathVariable("id") Long id, HttpServletResponse response) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        String invoiceNumber = invoice.getInvoiceNumber();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy_HH:mm:ss");
        String time = format.format(clock.time());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=invoice_" + invoiceNumber + "_" + time + ".pdf";
        response.setHeader(headerKey, headerValue);
        generatePdfDocument.pdfInvoice(response, invoice);
    }
}
