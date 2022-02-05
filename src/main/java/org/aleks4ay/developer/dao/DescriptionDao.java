package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.DescriptionMapper;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.PseudoName;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DescriptionDao extends AbstractDao<Description> implements BaseDao<Description> {

    private final PseudoNameDao pseudoNameDao = new PseudoNameDao(ConnectionPool.getInstance());

    public DescriptionDao(ConnectionPool connectionPool) {
        super(new DescriptionMapper(), connectionPool);
    }


    public List<Description> findAll(String sql) {
        return findAbstractAll(sql);
    }

    @Override
    public String getEntityName() {
        return "Description";
    }

    public boolean updateStatusName(String id, String statusName) {
        return updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_STATUS, id, statusName);
    }

    public boolean updateTypeName(String id, String typeName) {
        return updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_TYPE, id, typeName);
    }

    public void updateDesignerName(String id, String designer) {
        updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_DESIGNER, id, designer);
    }

    public Map<String, String> getDesignerPseudoNames() {
        return pseudoNameDao.findAll().stream()
                .collect(Collectors.toMap(PseudoName::getPseudoName, PseudoName::getName));
    }

    public void createPseudoName(String pseudoName, String name) {
        pseudoNameDao.create(pseudoName, name);
    }
}