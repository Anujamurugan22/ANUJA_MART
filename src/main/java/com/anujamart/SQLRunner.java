package com.anujamart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class SQLRunner {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("       ANUJA MART SQL RUNNER");
        System.out.println("==================================");
        System.out.println("Read-only SQL queries mattum run pannalaam.");
        System.out.println("Exit panna: exit");
        System.out.println();

        while (true) {

            System.out.print("SQL> ");
            String sql = scanner.nextLine().trim();

            if (sql.equalsIgnoreCase("exit")) {
                System.out.println("SQL Runner closed.");
                break;
            }

            if (sql.isEmpty()) {
                continue;
            }

            String normalizedSql = sql
                    .replaceAll(";+$", "")
                    .trim();

            if (!normalizedSql.matches(
                    "(?is)^(SELECT|SHOW|DESCRIBE|DESC)\\b.*")) {
                System.out.println(
                        "Only SELECT, SHOW, and DESCRIBE queries are allowed.");
                continue;
            }

            if (normalizedSql.contains(";")) {
                System.out.println(
                        "One query at a time. Multiple queries are not allowed.");
                continue;
            }

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(normalizedSql)) {

                ps.setQueryTimeout(30);

                try (ResultSet rs = ps.executeQuery()) {

                    ResultSetMetaData meta = rs.getMetaData();
                    int columns = meta.getColumnCount();
                    int rows = 0;

                    for (int i = 1; i <= columns; i++) {
                        System.out.print(meta.getColumnLabel(i));

                        if (i < columns) {
                            System.out.print(" | ");
                        }
                    }

                    System.out.println();
                    System.out.println("----------------------------------");

                    while (rs.next()) {

                        for (int i = 1; i <= columns; i++) {
                            Object value = rs.getObject(i);

                            System.out.print(
                                    value == null ? "NULL" : value);

                            if (i < columns) {
                                System.out.print(" | ");
                            }
                        }

                        System.out.println();
                        rows++;
                    }

                    System.out.println("----------------------------------");
                    System.out.println("Rows returned: " + rows);
                }

            } catch (Exception e) {
                System.out.println("SQL Error: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }
}