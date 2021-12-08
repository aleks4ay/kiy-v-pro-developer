package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.model.Embodiment;

public class EmbodimentService extends AbstractService<Embodiment> {

    public EmbodimentService(BaseDao<Embodiment> embodimentDao) {
        super(embodimentDao);
    }
}