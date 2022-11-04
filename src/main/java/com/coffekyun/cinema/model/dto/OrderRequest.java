package com.coffekyun.cinema.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OrderRequest {

    @NotEmpty(message = "The full id is required.")
    @Size(min = 2, max = 100, message = "The length of name must be between 2 and 100 characters.")
    private String userId;

    List<OrderDetailRequest> orderDetailRequests;

}
