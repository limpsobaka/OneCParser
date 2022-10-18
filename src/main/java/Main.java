import org.limpsobaka.DB.DB;
import org.limpsobaka.Entity.Base;
import org.limpsobaka.Entity.DataBase;
import org.limpsobaka.File.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class Main {
  public static void main(String[] args) throws Exception {
    SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");
    File file = new File(new File(args[0]).getAbsolutePath());
    Properties properties = new Properties();
    properties.load(new FileReader(file));

    Queue<Base> baseList = new LinkedList<>();
    baseList.addAll(Parser.parseBaseFile(properties));
    if (baseList.isEmpty()) {
      System.out.println("В файлах не найдена информация о базах.");
      System.exit(1);
    }
    DataBase dataBase = new DataBase(properties);
    int result = DB.dbWriteBase(dataBase, baseList);

    try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
      fileWriter.write(formatForDateNow.format(new Date()) + " - Обработано записей в БД: " + result + "\n");
    } catch (IOException e) {
      System.out.println("Не удалось записать лог. Ошибка: " + e.getMessage());
    }
  }
}
