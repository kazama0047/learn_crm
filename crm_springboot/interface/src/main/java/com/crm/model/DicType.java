package com.crm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kazama
 * @create 2021-03-09-16:32
 */
@Data
public class DicType implements Serializable {
    private String code;
    private String name;
    private String description;
}
