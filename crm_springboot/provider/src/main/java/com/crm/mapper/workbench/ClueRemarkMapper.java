package com.crm.mapper.workbench;

import com.crm.model.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {
    List<ClueRemark> getListByClueId(String clueId);

    int delete(ClueRemark clueRemark);
}