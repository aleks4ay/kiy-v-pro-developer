package org.aleks4ay.developer.tools;

public final class ConstantsSql {

    public static final String TMC_GET_ALL = "SELECT * FROM tmc;";

    public static final String ORDER_GET_ALL_LIKE = "select o.id, j.doc_number, j.t_create, o.t_factory, o.duration, " +
            "c.name as client, w.name as manager, o.status" +
            " from orders o, journal j, client c, worker w WHERE o.id = j.id and o.id_client = c.id and o.id_manager = w.id" +
            " and EXTRACT(year FROM j.t_create) = ";
    public static final String ORDER_GET_ALL_NEW = "select o.id, j.doc_number, j.t_create, o.t_factory, o.duration," +
            "    c.name as client, w.name as manager, o.status" +
            "    from orders o, journal j, client c, worker w WHERE o.status = 'NEW'" +
            "    and o.id = j.id and o.id_client = c.id and o.id_manager = w.id ";
    public static final String ORDER_GET_ALL_KB = "select o.id, j.doc_number, j.t_create, o.t_factory, o.duration," +
            "    c.name as client, w.name as manager, o.status from orders o, journal j, client c, worker w " +
            "    WHERE o.status in ('KB_NEW','KB_START','KB_QUESTION','KB_CONTINUED')" +
            "    and o.id = j.id and o.id_client = c.id and o.id_manager = w.id ";

    public static final String ORDER_UPDATE_STATUS = "UPDATE orders SET status=? where id=?;";

    public static final String DESCRIPTION_GET_BY_ORDER_ID = "SELECT d.*, e.description as emb FROM descriptions d " +
            "LEFT JOIN embodiment e on d.embodiment = e.id and id_order = ?;";
    public static final String DESCRIPTION_GET_ALL = "select d1.*, t.descr, t.descr_all from" +
            " (SELECT d.*, e.description as emb FROM descriptions d LEFT JOIN embodiment e on d.embodiment = e.id) as d1" +
            " inner join tmc t on d1.id_tmc = t.id  order by d1.id;";
    public static final String DESCRIPTION_GET_ALL_NEW = "select d1.*, t.descr, t.descr_all from" +
            " (SELECT d.*, e.description as emb FROM descriptions d LEFT JOIN embodiment e on d.embodiment = e.id" +
            " where d.status = 'NEW') as d1" +
            " inner join tmc t on d1.id_tmc = t.id  order by d1.id;";
    public static final String DESCRIPTION_GET_ALL_KB = "select d1.*, t.descr, t.descr_all from" +
            " (SELECT d.*, e.description as emb FROM descriptions d LEFT JOIN embodiment e on d.embodiment = e.id" +
            " where d.status in ('KB_NEW','KB_START','KB_QUESTION','KB_CONTINUED') ) as d1" +
            " inner join tmc t on d1.id_tmc = t.id order by d1.id;";
    public static final String DESCRIPTION_UPDATE_STATUS = "update descriptions set status = ? where id = ?;";
    public static final String DESCRIPTION_UPDATE_TYPE = "update descriptions set type = ? where id = ?;";
    public static final String DESCRIPTION_UPDATE_DESIGNER = "update descriptions set designer_name = ? where id = ?;";
    public static final String CREATE_IMAGE = "insert into descr_image (id_description, image, name) VALUES (?, ?, ?);";
    public static final String FIND_IMAGE = "select * from descr_image where id_description = ?;";
    public static final String DESCRIPTION_GET_ALL_ID_WITH_IMAGE = "select id_description from descr_image;";
    public static final String EMPTY_TABLES =   "delete from description_time where true; " +
                                                "delete from descr_image where true; " +
                                                "delete from descriptions where true;" +
                                                "delete from order_time where true;" +
                                                "delete from orders where true;" +
                                                "delete from journal where true;" +
                                                "ALTER SEQUENCE times_id_seq RESTART WITH 1;";
    public static final String DESCRIPTION_IMAGE_TABLE_CREATE = "drop table if exists descr_image;" +
            "CREATE TABLE descr_image (   id bigint PRIMARY KEY DEFAULT nextval('times_id_seq')," +
            "                             id_description VARCHAR(13) NOT NULL," +
            "                             name VARCHAR(255) NOT NULL," +
            "                             image bytea," +
            "                             time TIMESTAMP default now()," +
            "                             FOREIGN KEY (id_description) REFERENCES descriptions (id) ON DELETE CASCADE);";

    public static final String ORDER_TIME_CREATE = "insert into order_time (id_order, status, time) VALUES (?, ?, ?);";
    public static final String ORDER_TIME_FIND_ALL = "SELECT * FROM order_time;";
    public static final String ORDER_TIME_FIND_BY_ORDER_ID = "SELECT * FROM order_time WHERE id_order = ?;";

    public static final String DESCRIPTION_TIME_CREATE = "insert into description_time (id_description, status, time) VALUES (?, ?, ?);";

    public static final String DESCRIPTION_TIME_FIND_ALL = "SELECT * FROM description_time;";
    public static final String DESCRIPTION_TIME_FIND_ALL_BY_ORDER_ID = "SELECT * FROM description_time WHERE id_description LIKE ?;";

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
