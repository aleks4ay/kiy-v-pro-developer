package org.aleks4ay.developer.dao;

import java.util.List;

public interface BaseDao<T> {
    List<T> findAll();
    String getEntityName();
}
