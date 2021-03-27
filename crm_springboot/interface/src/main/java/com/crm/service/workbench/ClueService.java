package com.crm.service.workbench;

import com.crm.model.Clue;
import com.crm.model.Tran;

/**
 * @author Kazama
 * @create 2021-03-09-16:23
 */
public interface ClueService {
    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, Tran t, String createBy);
}
