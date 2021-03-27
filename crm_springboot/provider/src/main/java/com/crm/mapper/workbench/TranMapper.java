package com.crm.mapper.workbench;

import com.crm.model.Tran;

import java.util.List;
import java.util.Map;

public interface TranMapper {

    int save(Tran t);

    Tran detail(String id);

    int changeStage(Tran t);

    int getTotal();

    List<Map<String, Object>> getCharts();
}
