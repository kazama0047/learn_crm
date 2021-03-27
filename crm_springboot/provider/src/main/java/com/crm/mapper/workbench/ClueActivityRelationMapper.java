package com.crm.mapper.workbench;

import com.crm.model.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationMapper {


    int unbund(String id);

    int bund(ClueActivityRelation car);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
