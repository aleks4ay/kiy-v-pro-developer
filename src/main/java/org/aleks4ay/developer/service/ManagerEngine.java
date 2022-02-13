package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.ConnectionBase;
import org.aleks4ay.developer.dao.OtherDao;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.HashMap;
import java.util.Map;

import static org.aleks4ay.developer.tools.ConstantsSql.DELIMITER;

public class ManagerEngine {

    public Map<String, String> detManagers (ConnectionBase connectionBase) {
        OtherDao dao = new OtherDao(connectionBase);
        return dao.selectBase(ConstantsSql.MANAGERS_ALL);
    }

    public Map<String, String> detDesigners (ConnectionBase connectionBase) {
        OtherDao dao = new OtherDao(connectionBase);
        Map<String, String> result = new HashMap<>();
        Map<String, String> rawMap = dao.selectBase(ConstantsSql.DEVELOPER_ALL);
        for (Map.Entry<String, String> d : rawMap.entrySet()) {
            String partOgDesignerId = d.getKey();
            String keyResultMap = d.getValue();
            if (result.containsKey(keyResultMap)) {
                String newValue = result.get(keyResultMap) + DELIMITER + partOgDesignerId;
                result.put(keyResultMap, newValue);
            } else {
                result.put(keyResultMap, partOgDesignerId);
            }
        }
        return result;
    }
}


