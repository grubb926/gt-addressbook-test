package com.example.gtaddressbooktest.service.file;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CSVFileService implements FileService {

    private static final String DELIMITER_COMMA = ",";
    @Override
    public List<List<String>> readFromFile(File file) throws FileNotFoundException {
        final List<List<String>> records = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }

        return records;
    }

    private List<String> getRecordFromLine(String line) {
        final List<String> values = new ArrayList<>();

        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(DELIMITER_COMMA);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next().trim());
            }
        }

        return values;
    }
}
