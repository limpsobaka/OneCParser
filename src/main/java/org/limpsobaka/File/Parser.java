package org.limpsobaka.File;

import org.limpsobaka.Entity.Base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class Parser {
  public static Queue<Base> parseBaseFile(Properties properties) throws UnknownHostException {

    List<String> fileContent = SmbUtil.smbDownload(properties);

    int serverPort = 0;
    String serverName = "";
    String serverFullName = "";
    Queue<Base> baseList = new LinkedList<>();

    for (String fl : fileContent) {
      String[] splitedFileContent = fl.split("\r\n");
      for (String line : splitedFileContent) {
        String[] splitedInput = line.split(",");
        if (splitedInput.length == 11) {
          serverPort = Integer.parseInt(splitedInput[2]);
          serverName = splitedInput[3].substring(1, splitedInput[3].length() - 1);
          serverFullName = InetAddress.getByName(serverName).getCanonicalHostName();
        }
        if (line.contains(";DBMS=")) {
          splitedInput = line.split(",");
          String baseID = splitedInput[0].substring(1);
          String baseName = splitedInput[1].substring(1, splitedInput[1].length() - 1);
          String description = splitedInput[2].substring(1, splitedInput[2].length() - 1);
          String dbServerType = splitedInput[3].substring(1, splitedInput[3].length() - 1);
          String dbServerName = splitedInput[4].substring(1, splitedInput[4].length() - 1);
          String dbName = splitedInput[5].substring(1, splitedInput[5].length() - 1);
          String dbLogin = splitedInput[6].substring(1, splitedInput[6].length() - 1);
          baseList.add(new Base(serverFullName, serverName, serverPort, baseID, baseName, description, dbServerType,
                  dbServerName, dbName, dbLogin));
        }
      }
    }
    return baseList;
  }
}
