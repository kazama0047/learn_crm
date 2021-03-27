package com.crm.mapper.workbench;

import com.crm.model.Contacts;

import java.util.List;

public interface ContactsMapper {
    int save(Contacts con);

    List<Contacts> getContactsListByName(String name);
}