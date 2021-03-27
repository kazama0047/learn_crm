package com.crm.service.workbench;

import com.crm.model.Contacts;

import java.util.List;

/**
 * @author Kazama
 * @create 2021-03-12-21:47
 */
public interface ContactsService {
    List<Contacts> getContactsListByName(String name);
}
