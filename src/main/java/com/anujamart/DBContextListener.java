package com.anujamart;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DBContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing ANUJA Mart database connection pool and schema...");
        DBConnection.initDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Closing ANUJA Mart database connection pool...");
        DBConnection.close();
    }
}
