package com.crm.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TranHistory implements Serializable {
	
	private String id;
	private String stage;
	private String money;
	private String expectedDate;
	private String createTime;
	private String createBy;
	private String tranId;
	private String possibility;
}
