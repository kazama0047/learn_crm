package com.crm.mapper.settings;


import com.crm.model.DicValue;

import java.util.List;

/**
 * @author Kazama
 * @create 2021-03-09-16:34
 */
public interface DicValueMapper {
    List<DicValue> getListByCode(String code);
}
