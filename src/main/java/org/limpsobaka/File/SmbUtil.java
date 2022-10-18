package org.limpsobaka.File;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;

public class SmbUtil {
  public static List<String> smbDownload(Properties properties) {

    String remoteIps = properties.getProperty("serverIps");
    String remoteUrl = properties.getProperty("shareUrl");
    String username = properties.getProperty("shareUsername");
    String password = properties.getProperty("sharePassword");
    String filename = properties.getProperty("baseFilename");
    File shareFile;
    InputStream shareIs = null;
    ByteArrayOutputStream shareBaos;
    SMBClient client = null;
    Session session = null;
    DiskShare diskShare = null;
    Connection connection;
    AuthenticationContext authenticationContext;
    List<String> fileContentList = new ArrayList<>();
    String[] splitedIps = remoteIps.split(";");

    for (String remoteIp : splitedIps) {
      try {
        client = new SMBClient();
        connection = client.connect(remoteIp);
        authenticationContext = new AuthenticationContext(username, password.toCharArray(), null);
        session = connection.authenticate(authenticationContext);
        diskShare = (DiskShare) session.connectShare(remoteUrl);
        for (FileIdBothDirectoryInformation f : diskShare.list("\\", "reg_*")) {
          for (FileIdBothDirectoryInformation f1 : diskShare.list("\\" + f.getFileName(), filename)) {
            shareFile = diskShare.openFile("\\" + f.getFileName() + "\\" + f1.getFileName(),
                    EnumSet.of(AccessMask.GENERIC_READ),
                    EnumSet.of(FileAttributes.FILE_ATTRIBUTE_NORMAL), SMB2ShareAccess.ALL,
                    SMB2CreateDisposition.FILE_OPEN_IF, EnumSet.noneOf(SMB2CreateOptions.class));
            shareIs = shareFile.getInputStream();
            shareBaos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            while (shareIs.read(buffer) != -1) {
              shareBaos.write(buffer);
              buffer = new byte[4096];
            }
            fileContentList.add(new String(shareBaos.toByteArray(), StandardCharsets.UTF_8));
          }
        }
      } catch (Exception e) {
        System.out.println("Произошла ошибка при загрузке файлов: " + e.getMessage());
      } finally {
        try {
          if (null != shareIs) {
            shareIs.close();
          }
          if (null != diskShare) {
            diskShare.close();
          }
          if (null != session) {
            session.close();
          }
          if (null != client) {
            client.close();
          }
        } catch (IOException e) {
          System.out.println("Произошла ошибка при закрытии подключений: " + e.getMessage());
        }
      }
    }
    return fileContentList;
  }
}