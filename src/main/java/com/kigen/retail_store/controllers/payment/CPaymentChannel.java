package com.kigen.retail_store.controllers.payment;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.payment.PaymentChannelDTO;
import com.kigen.retail_store.models.EPaymentChannel;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.payment.IPaymentChannel;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/payment/channel")
public class CPaymentChannel {
    
    @Autowired
    private IPaymentChannel sPaymentChannel;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createPaymentChannel(@Valid @RequestBody PaymentChannelDTO paymentChannelDTO) throws URISyntaxException {

        EPaymentChannel paymentChannel = sPaymentChannel.create(paymentChannelDTO);

        return ResponseEntity
            .created(new URI("/" + paymentChannel.getId()))
            .body(new SuccessResponse(201, "successfully created payment channel", new PaymentChannelDTO(paymentChannel)));
    }

    @GetMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getPaymentChannelsList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);
        pageDTO.setSortVal("id");

        Page<EPaymentChannel> paymentChannels = sPaymentChannel.getPaginatedList(pageDTO, null);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched payment channels", paymentChannels, 
                PaymentChannelDTO.class, EPaymentChannel.class));
    }

    @GetMapping(path = "/{paymentChannelId}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getPaymentChannel(@PathVariable Integer paymentChannelId) {

        EPaymentChannel paymentChannel = sPaymentChannel.getById(paymentChannelId, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched payment channel", new PaymentChannelDTO(paymentChannel)));
    }
}
