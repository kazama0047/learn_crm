package com.crm.service.workbench;

import com.crm.model.Tran;
import com.crm.model.TranHistory;

import java.util.List;
import java.util.Map;

/**
 * @author Kazama
 * @create 2021-03-12-19:40
 */
public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran t);

    Map<String, Object> getCharts();
}
