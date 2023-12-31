package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    /**
     * Считывает текстовый файл и возвращает текст в формате String
     *
     * @param path Путь к текстовому файлу
     * @return Текст файла в формате String
     * @throws IOException При проблеме считывания файла
     */
    public static String readFromFile(String path) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder builder = new StringBuilder();

        String line;
        boolean needSpace = false;

        while ((line = reader.readLine()) != null) {
            if (needSpace) {
                builder.append(" ");
            }
            builder.append(line);
            needSpace = true;
        }
        reader.close();
        return builder.toString();
    }

    /**
     * Записывает информацию в файл. При наличии текста в файле производит дозаписывание информации
     *
     * @param line     Текст для записи
     * @param fileName Имя файла куда записываем
     * @return true - запись произошла успешно; false - запись произошла неудачно
     * @throws IOException При проблеме с записью файла
     */
    public static boolean writeToFile(String line, String fileName) throws IOException {

        Writer writer = new FileWriter(fileName, true);

        writer.write(line + "\n");
        writer.flush();
        writer.close();
        return true;
    }

    /**
     * Формирует коллекцию строк, состаящую из файлов по переданной директории. Папки при поиске игнорируются
     *
     * @param path      Путь для поиска файлов
     * @param extension Расширение файлов. При переданном null или пустой строке в строку попадают все файлы
     * @return коллекция с названиями файлов из директории
     */
    public static List<String> getFilesList(String path, String extension) {

        List<String> list = new ArrayList<>();
        boolean emptyExtension = (extension == null) || "".equals(extension);

        // Если переданном не null, или не пустой строке проверяем на корректность переданного расширения
        if (!emptyExtension) {
            int extLength = extension.length();
            int counter = 0;
            int index = 0;
            while ((index = extension.indexOf('.', index)) != -1) {
                counter++;
                index++;
            }

            // проверка на корректность переданного расширения файла
            if (extLength < 3 || extLength > 5 || extension.charAt(0) != '.' || counter > 1) {
                return list;
            }
        }

        File[] files = new File(path).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                String tempBookName = file.getName();
                if (emptyExtension || tempBookName.endsWith(extension)) {
                    list.add(tempBookName);
                }
            }
        }
        return list;
    }
}