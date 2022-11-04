package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.dto.InvoiceRequest;
import com.coffekyun.cinema.entity.Invoice;
import com.coffekyun.cinema.exception.DataNotFoundException;
import com.coffekyun.cinema.repository.InvoiceRepository;
import com.coffekyun.cinema.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final static Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public byte[] getInvoice(InvoiceRequest invoiceRequest) {
        log.info("get invoice with user id {} ", invoiceRequest.getIdUser());
        List<String> orderIds = orderRepository.findByUserId(invoiceRequest.getIdUser());
        if (orderIds.isEmpty()) {
            log.info("invoice with user id {} is not available", invoiceRequest.getIdUser());
            throw new DataNotFoundException("user with id " + invoiceRequest.getIdUser() + " has no order");
        }

        String id = orderIds.get(orderIds.size()-1);
        return generateInvoice(id);
    }

    private byte[] generateInvoice(String idOrder) {
        log.info("Generating pdf invoice for id order {}", idOrder);
        List<Invoice> invoices = invoiceRepository.get(idOrder);
        try {
            File design = ResourceUtils.getFile("classpath:jasper/invoice.jrxml");
            JasperReport report = JasperCompileManager.compileReport(design.getAbsolutePath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, buildParametersMap(), new JRBeanCollectionDataSource(invoices));

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (IOException | JRException exception) {
            log.error("Error has occured {}", exception.getMessage());
        }

        return null;
    }

    private Map<String, Object> buildParametersMap() {
        Map<String, Object> pdfInvoiceParams = new HashMap<>();
        pdfInvoiceParams.put("poweredby", "Toho Cinemas Roppongi Hills");
        return pdfInvoiceParams;
    }
}
