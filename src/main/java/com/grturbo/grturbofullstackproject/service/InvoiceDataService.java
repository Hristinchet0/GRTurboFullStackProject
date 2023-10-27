package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.User;

public interface InvoiceDataService {

    void saveInvoiceData(InvoiceData data, User user);
}
