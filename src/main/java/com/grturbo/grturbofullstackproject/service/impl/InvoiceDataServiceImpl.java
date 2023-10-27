package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.InvoiceDataRepository;
import com.grturbo.grturbofullstackproject.service.InvoiceDataService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceDataServiceImpl implements InvoiceDataService {

    private final InvoiceDataRepository invoiceDataRepository;

    public InvoiceDataServiceImpl(InvoiceDataRepository invoiceDataRepository) {
        this.invoiceDataRepository = invoiceDataRepository;
    }

    @Override
    public void saveInvoiceData(InvoiceData data, User user) {

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

        invoiceDataRepository.save(invoiceData);
    }
}
