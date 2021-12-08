package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.model.Client;

public class ClientService extends AbstractService<Client> {

    public ClientService(BaseDao<Client> clientDao) {
        super(clientDao);
    }
}