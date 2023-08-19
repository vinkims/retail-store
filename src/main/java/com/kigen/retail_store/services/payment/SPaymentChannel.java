package com.kigen.retail_store.services.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.payment.PaymentChannelDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.EPaymentChannel;
import com.kigen.retail_store.repositories.PaymentChannelDAO;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SPaymentChannel implements IPaymentChannel {

    @Autowired
    private PaymentChannelDAO paymentChannelDAO;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public Boolean checkExistsByName(String name) {
        return paymentChannelDAO.existsByName(name);
    }

    @Override
    public EPaymentChannel create(PaymentChannelDTO paymentChannelDTO) {
        EPaymentChannel paymentChannel = new EPaymentChannel();
        paymentChannel.setDescription(paymentChannelDTO.getDescription());
        paymentChannel.setName(paymentChannelDTO.getName());

        save(paymentChannel);
        return paymentChannel;
    }

    @Override
    public Optional<EPaymentChannel> getById(Integer paymentChannelId) {
        return paymentChannelDAO.findById(paymentChannelId);
    }

    @Override
    public EPaymentChannel getById(Integer paymentChannelId, Boolean handleException) {
        Optional<EPaymentChannel> paymentChannel = getById(paymentChannelId);
        if (!paymentChannel.isPresent() && handleException) {
            throw new NotFoundException("payment channel with specified id not found", "paymentChannelId");
        }
        return paymentChannel.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EPaymentChannel> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {
        
        String search = pageDTO.getSearch();

        SpecBuilder<EPaymentChannel> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EPaymentChannel>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        Specification<EPaymentChannel> spec = specBuilder.build();

        return paymentChannelDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EPaymentChannel paymentChannel) {
        paymentChannelDAO.save(paymentChannel);
    }
    
}
