package com.crm.mapper.workbench;

import com.crm.model.Customer;

import java.util.List;

public interface CustomerMapper {

    Customer getCustomerByName(String company);

    int save(Customer cus);

    List<String> getCustomerName(String name);
}
