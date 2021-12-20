package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.PseudoNameMapper;
import org.aleks4ay.developer.model.PseudoName;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.List;

public class PseudoNameDao extends AbstractDao<PseudoName> implements BaseDao<PseudoName> {

    public PseudoNameDao(ConnectionBase connectionBase) {
        super(new PseudoNameMapper(), connectionBase);
    }

    public boolean create(String pseudoName, String name) {
        return updateAbstract(ConstantsSql.PSEUDO_NAME_CREATE, new PseudoName(pseudoName, name));
    }

    @Override
    public List<PseudoName> findAll() {
        return findAbstractAll(ConstantsSql.PSEUDO_NAME_FIND_ALL);
    }

    @Override
    public String getEntityName() {
        return "PseudoName";
    }
}
