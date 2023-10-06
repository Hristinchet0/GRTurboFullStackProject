//package com.grturbo.grturbofullstackproject.model.entity;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//
//@Entity
//@Table(name = "invoice_data")
//public class InvoiceData {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotNull
//    @NotEmpty
//    @Column(name = "company_name")
//    private String companyName;
//
//    @NotNull
//    @NotEmpty
//    @Column(name = "registered_address")
//    private String registeredAddress;
//
//    @Column(name = "vat_registration")
//    private boolean vatRegistration;
//
//    @NotNull
//    @NotEmpty
//    @Column(name = "identification_number_UIC")
//    private String identificationNumberUIC;
//
//    @NotNull
//    @NotEmpty
//    @Column(name = "financially_accountable_person_name")
//    private String financiallyAccountablePersonName;
//
//    @NotNull
//    @NotEmpty
//    @Column(name = "phone_number")
//    private String phoneNumber;
//
//    @OneToOne
//    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
//    private Order order;
//
//    public InvoiceData() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getCompanyName() {
//        return companyName;
//    }
//
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//
//    public String getRegisteredAddress() {
//        return registeredAddress;
//    }
//
//    public void setRegisteredAddress(String registeredAddress) {
//        this.registeredAddress = registeredAddress;
//    }
//
//    public boolean isVatRegistration() {
//        return vatRegistration;
//    }
//
//    public void setVatRegistration(boolean vatRegistration) {
//        this.vatRegistration = vatRegistration;
//    }
//
//    public String getIdentificationNumberUIC() {
//        return identificationNumberUIC;
//    }
//
//    public void setIdentificationNumberUIC(String identificationNumberUIC) {
//        this.identificationNumberUIC = identificationNumberUIC;
//    }
//
//    public String getFinanciallyAccountablePersonName() {
//        return financiallyAccountablePersonName;
//    }
//
//    public void setFinanciallyAccountablePersonName(String financiallyAccountablePersonName) {
//        this.financiallyAccountablePersonName = financiallyAccountablePersonName;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
//}
