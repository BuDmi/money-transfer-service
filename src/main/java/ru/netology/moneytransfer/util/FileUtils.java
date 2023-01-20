package ru.netology.moneytransfer.util;

import ru.netology.moneytransfer.model.Card;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

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

    public static ConcurrentHashMap<String, Card> readCardsFromFile(String path, String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path + file))) {
            String str;
            ConcurrentHashMap<String, Card> registeredCards = new ConcurrentHashMap<>();
            while((str = reader.readLine()) != null) {
                String[] tmp = str.split(",");
                String cardNumber = tmp[0];
                String cardValidTill = tmp[1];
                String cardCvc = tmp[2];
                Double balance = (double) (Integer.parseInt(tmp[3]) / 100);
                Card card = new Card(cardNumber, cardValidTill, cardCvc, balance);
                registeredCards.put(cardNumber, card);
            }
            return registeredCards;
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
