package com.crm.mapper.workbench;

import com.crm.model.Clue;

public interface ClueMapper {
    int save(Clue clue);

    Clue detail(String id);

    Clue getById(String clueId);

    int delete(String clueId);
}