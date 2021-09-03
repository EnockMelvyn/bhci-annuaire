package com.example.AnnuaireBHCI.helper;

import com.example.AnnuaireBHCI.model.Contact;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "DIRECTIONS", "NOMS", "FONCTIONS", "POSTES", "MATRICULES" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Contact> csvToTutorials(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Contact> contacts = new ArrayList<Contact>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Contact contact = new Contact(
                        csvRecord.get("NOMS"),
                        csvRecord.get("FONCTIONS"),
                        csvRecord.get("POSTES"),
                        csvRecord.get("DIRECTIONS"),
                        csvRecord.get("ORDRE"),
                        csvRecord.get("MATRICULES")
                        );

                contacts.add(contact);
            }

            return contacts;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
