package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.dto.InvoiceRequest;

public interface InvoiceService {

    byte[] getInvoice(InvoiceRequest invoiceRequest);


}
