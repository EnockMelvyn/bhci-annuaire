package com.example.AnnuaireBHCI.helper;

import com.example.AnnuaireBHCI.model.Contact;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "DIRECTIONS", "NOMS", "FONCTIONS", "POSTES","MATRICULES" };
    static String SHEET = "Annuaire";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Contact> excelToContacts(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Contact> contacts = new ArrayList<Contact>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Contact contact = new Contact();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            contact.setDirection(currentCell.getStringCellValue());
                            break;

                        case 1:
                            contact.setNom(currentCell.getStringCellValue());
                            break;

                        case 2:
                            contact.setFonction(currentCell.getStringCellValue());
                            break;

                        case 3:
                            contact.setPosteTel(""+Math.round(currentCell.getNumericCellValue()));
                            //contact.setPosteTel(currentCell.getStringCellValue());
                            break;

                        case 4:
                            contact.setMatricule(""+Math.round(currentCell.getNumericCellValue()));
                            //contact.setMatricule(currentCell.getStringCellValue());
                            break;


                        default:
                            break;
                    }

                    cellIdx++;
                }

                contacts.add(contact);
            }

            workbook.close();
            return contacts;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
