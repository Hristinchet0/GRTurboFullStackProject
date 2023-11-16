package com.grturbo.grturbofullstackproject.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.InvoiceDataRepository;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InvoiceDataServiceImpl.class})
@ExtendWith(SpringExtension.class)
class InvoiceDataServiceImplTest {
    @MockBean
    private InvoiceDataRepository invoiceDataRepository;

    @Autowired
    private InvoiceDataServiceImpl invoiceDataServiceImpl;

    @Test
    void testSaveInvoiceData() {
        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCompanyName("Company Name");
        invoiceData.setCustomer(new User());
        invoiceData.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData.setId(123L);
        invoiceData.setIdentificationNumberUIC("42");
        invoiceData.setPhoneNumber("4105551212");
        invoiceData.setRegisteredAddress("42 Main St");
        invoiceData.setVatRegistration(true);

        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setInvoiceData(invoiceData);
        user.setLastName("Doe");
        user.setOrders(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(user);
        invoiceData1.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData1.setId(123L);
        invoiceData1.setIdentificationNumberUIC("42");
        invoiceData1.setPhoneNumber("4105551212");
        invoiceData1.setRegisteredAddress("42 Main St");
        invoiceData1.setVatRegistration(true);

        User user1 = new User();
        user1.setAddress("42 Main St");
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setInvoiceData(invoiceData1);
        user1.setLastName("Doe");
        user1.setOrders(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        InvoiceData invoiceData2 = new InvoiceData();
        invoiceData2.setCompanyName("Company Name");
        invoiceData2.setCustomer(user1);
        invoiceData2.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData2.setId(123L);
        invoiceData2.setIdentificationNumberUIC("42");
        invoiceData2.setPhoneNumber("4105551212");
        invoiceData2.setRegisteredAddress("42 Main St");
        invoiceData2.setVatRegistration(true);
        when(invoiceDataRepository.save(any())).thenReturn(invoiceData2);

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setInvoiceData(new InvoiceData());
        user2.setLastName("Doe");
        user2.setOrders(new HashSet<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        InvoiceData invoiceData3 = new InvoiceData();
        invoiceData3.setCompanyName("Company Name");
        invoiceData3.setCustomer(user2);
        invoiceData3.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData3.setId(123L);
        invoiceData3.setIdentificationNumberUIC("42");
        invoiceData3.setPhoneNumber("4105551212");
        invoiceData3.setRegisteredAddress("42 Main St");
        invoiceData3.setVatRegistration(true);

        User user3 = new User();
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setInvoiceData(invoiceData3);
        user3.setLastName("Doe");
        user3.setOrders(new HashSet<>());
        user3.setPassword("iloveyou");
        user3.setPhoneNumber("4105551212");
        user3.setRoles(new ArrayList<>());
        user3.setUsername("janedoe");

        InvoiceData invoiceData4 = new InvoiceData();
        invoiceData4.setCompanyName("Company Name");
        invoiceData4.setCustomer(user3);
        invoiceData4.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData4.setId(123L);
        invoiceData4.setIdentificationNumberUIC("42");
        invoiceData4.setPhoneNumber("4105551212");
        invoiceData4.setRegisteredAddress("42 Main St");
        invoiceData4.setVatRegistration(true);

        InvoiceData invoiceData5 = new InvoiceData();
        invoiceData5.setCompanyName("Company Name");
        invoiceData5.setCustomer(new User());
        invoiceData5.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData5.setId(123L);
        invoiceData5.setIdentificationNumberUIC("42");
        invoiceData5.setPhoneNumber("4105551212");
        invoiceData5.setRegisteredAddress("42 Main St");
        invoiceData5.setVatRegistration(true);

        User user4 = new User();
        user4.setAddress("42 Main St");
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setInvoiceData(invoiceData5);
        user4.setLastName("Doe");
        user4.setOrders(new HashSet<>());
        user4.setPassword("iloveyou");
        user4.setPhoneNumber("4105551212");
        user4.setRoles(new ArrayList<>());
        user4.setUsername("janedoe");

        InvoiceData invoiceData6 = new InvoiceData();
        invoiceData6.setCompanyName("Company Name");
        invoiceData6.setCustomer(user4);
        invoiceData6.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData6.setId(123L);
        invoiceData6.setIdentificationNumberUIC("42");
        invoiceData6.setPhoneNumber("4105551212");
        invoiceData6.setRegisteredAddress("42 Main St");
        invoiceData6.setVatRegistration(true);

        User user5 = new User();
        user5.setAddress("42 Main St");
        user5.setCity("Oxford");
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setInvoiceData(invoiceData6);
        user5.setLastName("Doe");
        user5.setOrders(new HashSet<>());
        user5.setPassword("iloveyou");
        user5.setPhoneNumber("4105551212");
        user5.setRoles(new ArrayList<>());
        user5.setUsername("janedoe");
        invoiceDataServiceImpl.saveInvoiceData(invoiceData4, user5);
        verify(invoiceDataRepository).save(any());
    }
}

