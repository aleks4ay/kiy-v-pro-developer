package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.model.Worker;

public class WorkerService extends AbstractService<Worker> {

    public WorkerService(BaseDao<Worker> workerDao) {
        super(workerDao);
    }
}