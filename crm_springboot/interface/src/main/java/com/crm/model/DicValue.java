package com.crm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kazama
 * @create 2021-03-09-16:31
 */
@Data
public class DicValue implements Serializable {
    private String id;
    private String value;
    private String text;
    private String orderNo;
    private String typeCode;
}
