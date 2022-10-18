package org.limpsobaka.DB;

import org.limpsobaka.Entity.Base;
import org.limpsobaka.Entity.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

public class DB {
  public static int dbWriteBase(DataBase db, Queue<Base> baseList) {
    StringBuilder query = new StringBuilder();
    query.append("MERGE INTO base_info t\n");
    query.append("USING (VALUES ");
    while (!baseList.isEmpty()) {
      Base base = baseList.poll();
      Date dateNow = new Date();
      SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");

      query.append("('" + base.getServerFullName() + "', " +
              "'" + base.getServerName() + "', " +
              "'" + base.getServerPort() + "', " +
              "'" + base.getBaseID() + "', " +
              "'" + base.getBaseName() + "', " +
              "'" + base.getDescription() + "', " +
              "'" + base.getDbServerType() + "', " +
              "'" + base.getDbServerName() + "', " +
              "'" + base.getDbName() + "', " +
              "'" + base.getDbLogin() + "')");
      if (!baseList.isEmpty()) {
        query.append(",\n");
      } else {
        query.append(") AS v (hostname, hostname_short, port, base_uid, base_name, base_description, db_type, db_hostname, db_name, db_login)\n" +
                "ON t.base_uid = v.base_uid\n" +
                "-- Replace when the key exists\n" +
                "WHEN MATCHED THEN\n" +
                "UPDATE SET\n" +
                "t.hostname = v.hostname,\n" +
                "t.hostname_short = v.hostname_short,\n" +
                "t.port = v.port,\n" +
                "t.base_name = v.base_name,\n" +
                "t.base_description = v.base_description,\n" +
                "t.db_type = v.db_type,\n" +
                "t.db_hostname = v.db_hostname,\n" +
                "t.db_name = v.db_name,\n" +
                "t.db_login = v.db_login,\n" +
                "t.actual_date = CAST('" + formatForDateNow.format(dateNow) + "' as datetime)\n" +
                "-- Insert new keys\n" +
                "WHEN NOT MATCHED THEN\n" +
                "INSERT (hostname, hostname_short, port, base_uid, base_name, base_description, db_type, db_hostname, db_name, db_login)\n" +
                "VALUES (v.hostname, v.hostname_short, v.port, v.base_uid, v.base_name, v.base_description, v.db_type, v.db_hostname, v.db_name, v.db_login);");
      }
    }
    int rows = 0;
    try (Connection connect = dbConnect(db);
         Statement statement = dbStatement(connect)) {
      rows = statement.executeUpdate(query.toString());
    } catch (SQLException e) {
      System.out.println("Выполнения запроса БД: " + e.getMessage());
    }
    return rows;
  }

  private static Connection dbConnect(DataBase dataBase) {
    Connection conn;
    try {
      conn = DriverManager.getConnection(
              "jdbc:sqlserver://" + dataBase.getDbAddress()
                      + ";databaseName=" + dataBase.getDbName()
                      + ";encrypt=" + dataBase.getEncrypt()
                      + ";trustServerCertificate=" + dataBase.getTrustServerCertificate(),
              dataBase.getDbLogin(),
              dataBase.getDbPassword()
      );
    } catch (SQLException e) {
      System.out.println("Ошибка подключения к базе: " + e.getMessage());
      return null;
    }
    return conn;
  }

  private static Statement dbStatement(Connection connection) {
    Statement statement;
    try {
      statement = connection.createStatement();
    } catch (SQLException e) {
      System.out.println("Ошибка подключения к базе: " + e.getMessage());
      return null;
    }
    return statement;
  }
}