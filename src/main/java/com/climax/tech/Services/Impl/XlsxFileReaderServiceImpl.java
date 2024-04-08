package com.climax.tech.Services.Impl;

import com.climax.tech.Entities.Client;
import com.climax.tech.Services.FileReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class XlsxFileReaderServiceImpl implements FileReader {
    @Override
    public List<Client> readFile(MultipartFile file) throws IOException {

        List<Client> clients = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getRowNum() == 0) {
                    continue;
                }

                String nom = "";
                if (row.getCell(0) != null) {
                    nom = row.getCell(0).getStringCellValue();
                }

                String prenom = "";
                if (row.getCell(1) != null) {
                    prenom = row.getCell(1).getStringCellValue();
                }

                int age = 0;
                if (row.getCell(2) != null) {
                    age = (int) row.getCell(2).getNumericCellValue();
                }

                String profession = "";
                if (row.getCell(3) != null) {
                    profession = row.getCell(3).getStringCellValue();
                }

                int salaire = 0;
                if (row.getCell(4) != null) {
                    salaire = (int) row.getCell(4).getNumericCellValue();
                }

                clients.add(new Client(nom, prenom, age, profession, salaire));
            }
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
