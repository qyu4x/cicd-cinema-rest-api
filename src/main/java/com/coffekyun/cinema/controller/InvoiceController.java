package com.coffekyun.cinema.controller;

import com.coffekyun.cinema.model.dto.InvoiceRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import com.coffekyun.cinema.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;



@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final static Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @Operation(
          summary = "generate pdf invoice based on user id"
    )
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping(value = "/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public void reportInvoice(HttpServletResponse response, @RequestParam("id") String id) {
        log.info("#calling controller reportInvoice");
        try {
            ByteArrayInputStream invoice = new ByteArrayInputStream(invoiceService.getInvoice(new InvoiceRequest(id)));
            response.addHeader("Content-Disposition", "attachment; filename=" + id +".pdf");
            response.setContentType("application/octet-stream");

            IOUtils.copy(invoice, response.getOutputStream());
            response.flushBuffer();
            log.info("success create file pdf for user id {} ",id );
        }catch (Exception exception) {
            log.error("failed to generate pdf file {} , error {} ", id, exception.getMessage());
        }
    }
}
