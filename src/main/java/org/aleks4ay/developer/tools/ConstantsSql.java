package org.aleks4ay.developer.tools;

public final class ConstantsSql {

    public static final String WORKER_GET_ONE = "SELECT * FROM worker WHERE id = ?;";
    public static final String WORKER_GET_ALL = "SELECT * FROM worker;";
    public static final String WORKER_DELETE = "DELETE FROM worker WHERE id = ?;";
    public static final String WORKER_CREATE = "INSERT INTO worker (name, id) VALUES (?, ?);";
    public static final String WORKER_UPDATE = "UPDATE worker SET name=? where id=?;";

    public static final String TMC_GET_ONE = "SELECT * FROM tmc WHERE id = ?;";
    public static final String TMC_GET_ALL = "SELECT * FROM tmc;";
    public static final String TMC_DELETE = "DELETE FROM tmc WHERE id = ?;";
    public static final String TMC_CREATE = "INSERT INTO tmc (id_parent, code, descr, " +
            "size_a, size_b, size_c, is_folder, descr_all, type, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String TMC_UPDATE = "UPDATE tmc SET id_parent=?, code=?, descr=?, " +
            "size_a=?, size_b=?, size_c=?, is_folder=?, descr_all=?, type=? WHERE id = ?;";


    public static final String ORDER_GET_ONE = "SELECT * FROM orders o WHERE id = ?;";
    public static final String ORDER_GET_ALL = "SELECT * FROM orders order by ;";
    public static final String ORDER_GET_ALL_ID = "SELECT id FROM orders;";
    public static final String ORDER_GET_ALL_FILLED = "select o.id, j.doc_number, j.t_create, o.t_factory, o.duration, " +
            "c.name as client, w.name as manager, os.status " +
            "from orders o, journal j, client c, worker w, order_status os WHERE (os.id = o.id and os.status = 'NEW') " +
            "and o.id = j.id and o.id_client = c.id and o.id_manager = w.id order by o.id desc;";
    public static final String ORDER_CREATE = "insert into orders (id_client, id_manager, duration, t_factory, price, id)" +
            " VALUES (?, ?, ?, ?, ?, ?);";
    public static final String ORDER_UPDATE = "UPDATE orders " +
            "SET id_client=?, id_manager=?, duration=?, t_factory=?, price=? where id=?;";

    public static final String DESCRIPTION_GET_ONE = "SELECT d.*, e.description as emb FROM descriptions d LEFT JOIN embodiment e on d.embodiment = e.id and id = ?;";
    public static final String DESCRIPTION_GET_BY_ORDER_ID = "SELECT d.*, e.description as emb FROM descriptions d LEFT JOIN embodiment e on d.embodiment = e.id and id_order = ?;";
    public static final String DESCRIPTION_GET_ALL_NEW =
            "select d1.*, st.status, st.is_parsing, st.is_techno_product, st.type, st.designer_name from " +
            "(SELECT d.*, e.description as emb FROM descriptions d LEFT JOIN embodiment e on d.embodiment = e.id) as d1 " +
            "inner join description_status st on d1.id = st.id and st.status = 'NEW' order by d1.id;";

    public static final String DESCRIPTION_UPDATE = "UPDATE descriptions SET id_order=?, position=?, id_tmc=?, quantity=?, descr_second=?, " +
            "size_a=?, size_b=?, size_c=?, embodiment=? WHERE id = ?;";

}
