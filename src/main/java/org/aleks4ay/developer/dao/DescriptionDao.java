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

    @Override
    public List<Description> findAll() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_GET_ALL);
    }

    public List<Description> findAllNew() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_GET_ALL_NEW);
    }

    public List<Description> findAllKb() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_GET_ALL_KB);
    }

    @Override
    public String getEntityName() {
        return "Description";
    }

    public List<Description> findByOrderId(String orderId) {
        return findAbstractAllById(ConstantsSql.DESCRIPTION_GET_BY_ORDER_ID, orderId);
    }

    public boolean updateStatusName(String id, String statusName) {
        return updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_STATUS, id, statusName);
    }

    public boolean updateTypeName(String id, String typeName) {
        return updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_TYPE, id, typeName);
    }

    public boolean updateDesignerName(String id, String designer) {
        return updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_DESIGNER, id, designer);
    }

    public Map<String, String> getDesignerPseudoNames() {
        return pseudoNameDao.findAll().stream()
                .collect(Collectors.toMap(PseudoName::getPseudoName, PseudoName::getName));
    }

    public void createPseudoName(String pseudoName, String name) {
        pseudoNameDao.create(pseudoName, name);
    }
}