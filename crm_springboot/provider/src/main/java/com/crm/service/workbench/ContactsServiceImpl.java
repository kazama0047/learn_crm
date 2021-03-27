package com.crm.service.workbench;


import com.alibaba.dubbo.config.annotation.Service;
import com.crm.mapper.workbench.ContactsMapper;
import com.crm.model.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kazama
 * @create 2021-03-12-21:47
 */
@Component
@Service(interfaceClass = ContactsService.class,version = "1.0",timeout = 15000)
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;
    @Override
    public List<Contacts> getContactsListByName(String name) {
        return contactsMapper.getContactsListByName(name);
    }
}
