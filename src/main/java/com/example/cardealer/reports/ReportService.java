package com.example.cardealer.reports;

import com.example.cardealer.config.Clock;
import com.example.cardealer.documents.boundary.InvoiceRepository;
import com.example.cardealer.documents.entiity.Invoice;
import com.example.cardealer.utils.enums.Transaction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
@Getter
@RequiredArgsConstructor
public class ReportService {
    private final Clock clock;
    private final InvoiceRepository invoiceRepository;

    BigDecimal rateTax = new BigDecimal(".18");
    BigDecimal valueOfZero = BigDecimal.ZERO;
    BigDecimal taxValue = BigDecimal.ZERO;
    BigDecimal saleValue = BigDecimal.ZERO;
    BigDecimal purchaseValue = BigDecimal.ZERO;
    BigDecimal profitValue = BigDecimal.ZERO;

    public void getReportWithSevenLastDays() {
        LocalDate today = clock.date();
        LocalDate sevenDayEarly = today.minusDays(7L);
        Collection<Invoice> invoices = invoiceRepository.findInvoiceByDateScope(sevenDayEarly, today);
        BigDecimal tempPurchaseValue = valueOfZero;
        BigDecimal tempSaleValue = valueOfZero;
        if (invoices.size() > 0) {
            summaryInvoiceAmount(invoices, tempPurchaseValue, tempSaleValue);
            BigDecimal profitValueBeforeTax = saleValue.subtract(purchaseValue).setScale(2, RoundingMode.CEILING);
            taxValue = profitValueBeforeTax.multiply(rateTax).setScale(2, RoundingMode.CEILING);
            profitValue = profitValueBeforeTax.subtract(taxValue).setScale(2, RoundingMode.CEILING);
        } else {
            purchaseValue = valueOfZero;
            saleValue = valueOfZero;
            taxValue = valueOfZero;
            profitValue = valueOfZero;
        }
    }

    public void getReportWithDateScope(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        Collection<Invoice> invoices = invoiceRepository.findInvoiceByDateScope(fromDate, toDate);
        BigDecimal tempPurchaseValue = valueOfZero;
        BigDecimal tempSaleValue = valueOfZero;
        if (invoices.size() > 0) {
            summaryInvoiceAmount(invoices, tempPurchaseValue, tempSaleValue);
            BigDecimal profitValueBeforeTax = saleValue.subtract(purchaseValue);
            taxValue = profitValueBeforeTax.multiply(rateTax);
            profitValue = profitValueBeforeTax.subtract(taxValue);
        } else {
            purchaseValue = valueOfZero;
            saleValue = valueOfZero;
            taxValue = valueOfZero;
            profitValue = valueOfZero;
        }
    }

    private void summaryInvoiceAmount(Collection<Invoice> invoices, BigDecimal tempPurchaseValue, BigDecimal tempSaleValue) {
        for (Invoice inv : invoices) {
            if (inv.getTransaction().name().matches(Transaction.PURCHASE.name())) {
                tempPurchaseValue = tempPurchaseValue.add(inv.getInvoiceAmount());
            } else if (inv.getTransaction().name().matches(Transaction.SALE.name())) {
                tempSaleValue = tempSaleValue.add(inv.getInvoiceAmount());
            }
        }
        saleValue = tempSaleValue;
        purchaseValue = tempPurchaseValue;
    }
}
