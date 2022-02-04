package org.aleks4ay.developer.run;

import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.PseudoNameDao;

public class ExecuteSql {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    PseudoNameDao dao = new PseudoNameDao(connectionPool);



    public static void main(String[] args) {
        new ExecuteSql().run();
    }

    public void run() {
        System.out.println("-----Start update developer name ----------");

        addNewDesignerName();

        System.out.println("-----End update developer name --------");
    }

    public void addNewDesignerName() {
        dao.saveStatement("update developer set name = 'Гармай' where pseudo_name = 'KB1'; " +
                "update developer set name = 'Губарев' where pseudo_name = 'KB2';" +
                "update developer set name = 'Дячок Д. М.' where pseudo_name = 'KEV';");
    }
}
