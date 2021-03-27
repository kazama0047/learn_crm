package com.crm.service.workbench;

import com.alibaba.dubbo.config.annotation.Service;
import com.crm.mapper.workbench.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kazama
 * @create 2021-03-12-22:39
 */
@Component
@Service(interfaceClass = CustomerService.class,version = "1.0",timeout = 15000)
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<String> getCustomerName(String name) {
        return customerMapper.getCustomerName(name);
    }
}
