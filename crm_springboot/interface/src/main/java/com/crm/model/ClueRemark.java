package com.crm.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClueRemark implements Serializable {
	
	private String id;
	private String noteContent;
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	private String editFlag;
	private String clueId;
}
