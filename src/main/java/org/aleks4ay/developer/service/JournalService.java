package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.model.Journal;

public class JournalService extends AbstractService<Journal> {

    public JournalService(BaseDao<Journal> journalDao) {
        super(journalDao);
    }
}