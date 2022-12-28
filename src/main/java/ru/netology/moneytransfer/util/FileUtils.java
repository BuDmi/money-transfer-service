package ru.netology.moneytransfer.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
    public static Boolean createNewFile(String path, String fileName) {
        File file = new File(path, fileName);

        try {
            if (!file.exists()) {
                return file.createNewFile();
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static Map<String, String> readCardsFromFile(String path, String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path + file))) {
            String str;
            Map<String, String> settings = new HashMap<>();
            while((str = reader.readLine()) != null) {
                String[] tmp = str.split("=");
                if (tmp.length == 2) {
                    settings.put(tmp[0], tmp[1]);
                }
            }
            return settings;
        } catch (IOException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            throw new IOException(ex);
        }
    }

    public static void writeTextToFile(String path, String file, String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + file, true))) {
            writer.write(text + "\n");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            throw new IOException(ex);
        }
    }
}
