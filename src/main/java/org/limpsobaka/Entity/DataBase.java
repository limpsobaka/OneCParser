package org.limpsobaka.Entity;

import java.util.Properties;

public class DataBase {
  private final String dbAddress;
  private final String dbName;
  private final String dbLogin;
  private final String dbLoginDomain;
  private final String dbPassword;
  private final String encrypt;
  private final String trustServerCertificate;

  public DataBase(Properties properties) {
    dbAddress = properties.getProperty("dbAddress");
    dbName = properties.getProperty("dbName");
    dbLogin = properties.getProperty("dbLogin");
    dbLoginDomain = properties.getProperty("dbLoginDomain");
    dbPassword = properties.getProperty("dbPassword");
    encrypt = properties.getProperty("encrypt");
    trustServerCertificate = properties.getProperty("trustServerCertificate");
  }

  public String getDbAddress() {
    return dbAddress;
  }

  public String getDbName() {
    return dbName;
  }

  public String getDbLogin() {
    return dbLogin;
  }

  public String getDbLoginDomain() {
    return dbLoginDomain;
  }

  public String getDbPassword() {
    return dbPassword;
  }

  public String getEncrypt() {
    return encrypt;
  }

  public String getTrustServerCertificate() {
    return trustServerCertificate;
  }
}
