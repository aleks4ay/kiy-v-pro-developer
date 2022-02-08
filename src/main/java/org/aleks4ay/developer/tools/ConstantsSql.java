package org.aleks4ay.developer.tools;

public final class ConstantsSql {

    public static final String PARAMETER = "<PARAMETER>";
    public static final String BY_YEAR = " and EXTRACT(year FROM j.t_create) = <PARAMETER> ";
    public static final String BY_DATE_CREATE = " and j.t_create in (<PARAMETER>) ";
    public static final String BY_TYPE = " and d.type in (<PARAMETER>) ";
    public static final String BY_STATUS = " and d.status in (<PARAMETER>)";
    public static final String BY_STATUS_NEW = " and d.status = 'NEW' ";
    public static final String BY_STATUS_KB = "  and d.status in ('KB_NEW','KB_START','KB_QUESTION','KB_CONTINUED') ";
    public static final String BY_NUMBER = " and (j.doc_number like '%<PARAMETER>' or j.doc_number like '%<PARAMETER>%' " +
            " or j.doc_number like '<PARAMETER>%') ";
    public static final String BY_MANAGER = " and o.manager = '<PARAMETER>' ";
    public static final String BY_DEVELOPER = " and d.designer_name = '<PARAMETER>' ";

    public static final String SORT_ORDER_BY_NUMBER = " order by j.doc_number;";
    public static final String SORT_ORDER_BY_DATE_CREATE = " order by j.t_create;";
    public static final String SORT_ORDER_BY_DATE_FACTORY = " order by o.t_factory;";
    //    public static final String SORT_ORDER_BY_DATE_SHIPMENT = " order by o.t_factory;";
    public static final String SORT_ORDER_BY_MANAGER = " order by manager;";
    public static final String SORT_ORDER_BY_CLIENT = " order by client;";
    public static final String KI_ORDERS = "КІ-";
    public static final String KP_ORDERS = "ПК-";


    public static final String TMC_GET_ALL = "SELECT * FROM tmc;";

    public static final String ORDER_BASE = "select distinct o.id, j.doc_number, j.t_create, o.t_factory, o.duration, " +
            " c.name as client, w.name as manager, o.status" +
            " from orders o, journal j, client c, worker w, descriptions d" +
            " WHERE o.id = j.id and o.id_client = c.id and o.id_manager = w.id and o.id = d.id_order ";

    public static final String DESCRIPTION_START = "select r1.*, e.description as emb, dev.name as developer from" +
            " (SELECT d.*, t.descr, t.descr_all FROM descriptions d, tmc t where d.id_tmc = t.id ";
    public static final String DESCRIPTION_END = " order by d.id) as r1 left join embodiment e on r1.embodiment = e.id " +
            " left join developer dev on dev.pseudo_name = r1.designer_name;";


    public static final String ORDER_UPDATE_STATUS = "UPDATE orders SET status=? where id=?;";

    public static final String DESCRIPTION_UPDATE_STATUS = "update descriptions set status = ? where id = ?;";
    public static final String DESCRIPTION_UPDATE_TYPE = "update descriptions set type = ? where id = ?;";
    public static final String DESCRIPTION_UPDATE_DESIGNER = "update descriptions set designer_name = ? where id = ?;";
    public static final String CREATE_IMAGE = "insert into descr_image (id_description, image, name) VALUES (?, ?, ?);";
    public static final String FIND_IMAGE = "select * from descr_image where id_description = ?;";
    public static final String DESCRIPTION_GET_ALL_ID_WITH_IMAGE = "select id_description from descr_image;";


    public static final String ORDER_TIME_CREATE = "insert into order_time (id_order, status, time) VALUES (?, ?, ?);";
    public static final String ORDER_TIME_FIND_ALL = "SELECT * FROM order_time;";

    public static final String DESCRIPTION_TIME_CREATE = "insert into description_time (id_description, status, time) VALUES (?, ?, ?);";
    public static final String DESCRIPTION_TIME_DELETE = "delete from description_time where id_description = ? and status != 'NEW';";
    public static final String DESCRIPTION_TIME_FIND_ALL = "SELECT * FROM description_time;";

    public static final String PSEUDO_NAME_FIND_ALL = "SELECT * FROM developer;";
    public static final String PSEUDO_NAME_CREATE = "insert into developer (pseudo_name, name) VALUES (?, ?)";

    //to copy work from old DB
    public static final String GET_ALL_FROM_OLD_DB = "select * from order_view where t_create > '2021-07-01T00:00:00';";
    public static final String DESCRIPTION_UPDATE_STATUS_FROM_OLD = "update descriptions set status = ? where id = ? and status != ?;";
    public static final String DESCRIPTION_UPDATE_TYPE_FROM_OLD = "update descriptions set type = ? where id = ? and type != ?;";
    public static final String DESCRIPTION_UPDATE_DESIGNER_FROM_OLD = "update descriptions set designer_name = ? where id = ? and designer_name is null;";
    public static final String DESCRIPTION_TIME_CREATE_FROM_OLD = "insert into description_time (id_description, status, time) values(?,?,?);";
    public static final String ORDER_TIME_CREATE_FROM_OLD = "insert into order_time (id_order, status, time) values(?,?,?);";
}
