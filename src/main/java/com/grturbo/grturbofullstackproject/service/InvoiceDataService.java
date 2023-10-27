package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.InvoiceDataRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceDataService {

    private final InvoiceDataRepository invoiceDataRepository;

    public InvoiceDataService(InvoiceDataRepository invoiceDataRepository) {
        this.invoiceDataRepository = invoiceDataRepository;
    }

    public InvoiceData saveInvoiceData(InvoiceData data, User user) {

        InvoiceData invoiceData = user.getInvoiceData();

        if (invoiceData == null) {
            invoiceData = new InvoiceData();
        }

        invoiceData.setCompanyName(data.getCompanyName());
        invoiceData.setCustomer(data.getCustomer());
        invoiceData.setFinanciallyAccountablePersonName(data.getFinanciallyAccountablePersonName());
        invoiceData.setPhoneNumber(data.getPhoneNumber());
        invoiceData.setVatRegistration(data.isVatRegistration());
        invoiceData.setRegisteredAddress(data.getRegisteredAddress());
        invoiceData.setIdentificationNumberUIC(data.getIdentificationNumberUIC());
        invoiceData.setCustomer(user);

        return invoiceDataRepository.save(invoiceData);
    }
}
