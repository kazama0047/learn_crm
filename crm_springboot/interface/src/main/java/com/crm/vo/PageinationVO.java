package com.crm.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kazama
 * @create 2021-03-05-22:44
 */
@Data
public class PageinationVO<T> implements Serializable {
    private int total;
    private List<T> dataList;

}
