package com.coffekyun.cinema.repository;

import com.coffekyun.cinema.entity.Invoice;
import com.coffekyun.cinema.entity.Order;
import com.coffekyun.cinema.util.TimeConversion;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Invoice> get(String idOrder) {
        String sql = "SELECT os.id,ss.price, ol.quantity, os.total_price,ss.show_date, ss.start_time,fm.title,  sl.seats_code, sl.studios_name , os.created_at FROM orders as os JOIN orders_detail as ol ON os.id = ol.orders_id JOIN schedules as ss ON ol.schedules_id = ss.id JOIN films as fm ON ss.films_id = fm.id JOIN seats_detail sl on os.id = sl.orders_id WHERE os.id = :id";

        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter("id", idOrder);

        List<Object[]> invoices =  (List<Object[]>) nativeQuery.getResultList();

        List<Invoice> invoiceList = new ArrayList<>();
        for (int i = 0; i < invoices.size(); i++) {
            Invoice invoice = new Invoice();

            Date showDate = (Date) invoices.get(i)[4];
            LocalDate localDateShowDate = TimeConversion.convertToLocalDateViaInstant(showDate);
            Date startTime = (Date) invoices.get(i)[5];
            LocalTime localTimeStartTime = TimeConversion.convertToLocalTimeViaInstant(startTime);
            Date orderedAt = (Date)  invoices.get(i)[9];
            LocalDateTime localDateTimeOrderedAt = TimeConversion.convertToLocalDateTimeViaInstant(orderedAt);

            invoice.setOrderId(invoices.get(i)[0].toString());
            invoice.setPrice((BigDecimal) invoices.get(i)[1]);
            invoice.setQuantity((Integer) invoices.get(i)[2]);
            invoice.setTotalPrice((BigDecimal) invoices.get(i)[3]);
            invoice.setShowDate(localDateShowDate);
            invoice.setStartTime(localTimeStartTime);
            invoice.setTitle(invoices.get(i)[6].toString());
            invoice.setSeatCode(invoices.get(i)[7].toString());
            invoice.setStudioName(invoices.get(i)[8].toString());
            invoice.setOrderedAt(localDateTimeOrderedAt);

            invoiceList.add(invoice);
        }
        return invoiceList;
    }
}
