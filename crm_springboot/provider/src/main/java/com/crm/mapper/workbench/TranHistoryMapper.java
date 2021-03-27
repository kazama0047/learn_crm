package com.crm.mapper.workbench;


import com.crm.model.TranHistory;

import java.util.List;

public interface TranHistoryMapper {

    int save(TranHistory th);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
