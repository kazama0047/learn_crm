package com.crm.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClueActivityRelation implements Serializable {
	
	private String id;
	private String clueId;
	private String activityId;

}
