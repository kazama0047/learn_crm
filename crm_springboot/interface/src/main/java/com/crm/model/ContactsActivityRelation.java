package com.crm.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactsActivityRelation implements Serializable {

	private String id;
	private String contactsId;
	private String activityId;
}
