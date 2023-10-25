package com.example.billingservice.web;

import com.example.billingservice.entities.Bill;
import com.example.billingservice.repository.BillRepository;
import com.example.billingservice.repository.ProductItemRepository;
import com.example.billingservice.services.CustomerRestClient;
import com.example.billingservice.services.ProductRestClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/billings")
@AllArgsConstructor
public class BillRestController {

    //@Autowired // N'est pas recommander
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    @GetMapping("/fullBill/{id}")
    public Bill bill(@PathVariable Long id){
        Bill bill = billRepository.findById(id).get();

        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));

        bill.getProductItems().forEach(productItem -> {
            productItem.setProduct(productRestClient.getProductById(productItem.getProductId()));
        });

        return bill;

    }



}
