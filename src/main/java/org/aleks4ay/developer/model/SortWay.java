package org.aleks4ay.developer.model;

import org.aleks4ay.developer.tools.ConstantsSql;

public enum SortWay implements Comparable<SortWay>{
    NUMBER {
        @Override
        public String toStringRus() {
            return "По номеру заказа";
        }
        @Override
        public String getSql() {
            return ConstantsSql.SORT_ORDER_BY_NUMBER;
        }
    },
    DATE_CREATE {
        @Override
        public String toStringRus() {
            return "По дате заказа";
        }
        @Override
        public String getSql() {
            return ConstantsSql.SORT_ORDER_BY_DATE_CREATE;
        }
    },
    DATE_FACTORY {
        @Override
        public String toStringRus() {
            return "По дате 'в производство'";
        }
        @Override
        public String getSql() {
            return ConstantsSql.SORT_ORDER_BY_DATE_FACTORY;
        }
    },
    DATE_SHIPMENT {
        @Override
        public String toStringRus() {
            return "По дате отгрузки (из 1С)";
        }
//        @Override
//        public String getSql() {
//            return ConstantsSql.SORT_ORDER_BY_DATE_SHIPMENT;
//        }
    },
    DATE_SHIPMENT_REAL {
        @Override
        public String toStringRus() {
            return "По реальной дате отгрузки";
        }
    },
    CLIENT {
        @Override
        public String toStringRus() {
            return "По клиенту";
        }
        @Override
        public String getSql() {
            return ConstantsSql.SORT_ORDER_BY_CLIENT;
        }
    },
    MANAGER {
        @Override
        public String toStringRus() {
            return "По менеджеру";
        }
        @Override
        public String getSql() {
            return ConstantsSql.SORT_ORDER_BY_MANAGER;
        }
    },
    DATE_KB {
        @Override
        public String toStringRus() {
            return "по дате пост. в КБ";
        }
        @Override
        public String getSql() {
            return ConstantsSql.SORT_ORDER_BY_DATE_FACTORY;
        }
    },
    DATE_PARSING {
        @Override
        public String toStringRus() {
            return "по дате поступления";
        }
        @Override
        public String getSql() {
            return ConstantsSql.SORT_ORDER_BY_DATE_FACTORY;
        }
    };

    public String toStringRus() {
        return "";
    }

    public String getSql() {
        return "";
    }
}
