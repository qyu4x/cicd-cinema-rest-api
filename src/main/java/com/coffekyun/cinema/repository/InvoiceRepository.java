package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Invoice;
import com.coffekyun.cinema.entity.Order;

import java.util.List;

public interface InvoiceRepository {

    List<Invoice> get(String idOrder);

}
