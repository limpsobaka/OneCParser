package org.limpsobaka.Entity;

public class Base {
  private String serverFullName;
  private String serverName;
  private int serverPort;
  private String baseID;
  private String baseName;
  private String description;
  private String dbServerType;
  private String dbServerName;
  private String dbName;
  private String dbLogin;

  public Base(String serverFullName, String serverName, int serverPort,
              String baseID, String baseName, String description,
              String dbServerType, String dbServerName, String dbName, String dbLogin) {
    this.serverFullName = serverFullName;
    this.serverName = serverName;
    this.serverPort = serverPort;
    this.baseID = baseID;
    this.baseName = baseName;
    this.description = description;
    this.dbServerType = dbServerType;
    this.dbServerName = dbServerName;
    this.dbName = dbName;
    this.dbLogin = dbLogin;
  }

  public String getServerFullName() {
    return serverFullName;
  }

  public String getServerName() {
    return serverName;
  }

  public int getServerPort() {
    return serverPort;
  }

  public String getBaseID() {
    return baseID;
  }

  public String getBaseName() {
    return baseName;
  }

  public String getDescription() {
    return description;
  }

  public String getDbServerType() {
    return dbServerType;
  }

  public String getDbServerName() {
    return dbServerName;
  }

  public String getDbName() {
    return dbName;
  }

  public String getDbLogin() {
    return dbLogin;
  }

  @Override
  public String toString() {
    return "Base{" +
            "serverFullName='" + serverFullName + '\'' +
            ", serverName='" + serverName + '\'' +
            ", serverPort='" + serverPort + '\'' +
            ", baseID='" + baseID + '\'' +
            ", baseName='" + baseName + '\'' +
            ", description='" + description + '\'' +
            ", dbServerType='" + dbServerType + '\'' +
            ", dbServerName='" + dbServerName + '\'' +
            ", dbName='" + dbName + '\'' +
            ", dbLogin='" + dbLogin + '\'' +
            "}\n";
  }
}