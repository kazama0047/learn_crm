package com.crm.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerRemark implements Serializable {

	private String id;
	private String noteContent;
	private String createTime;
	private String createBy;
	private String editTime;
	private String editBy;
	private String editFlag;
	private String customerId;
}
