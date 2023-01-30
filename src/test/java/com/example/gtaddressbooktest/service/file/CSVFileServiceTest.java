package com.example.gtaddressbooktest.service.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CSVFileServiceTest {

    @Value(value = "AddressBookTest")
    private Resource file;

    private FileService fileService;

    @BeforeEach
    void setUp () {
        fileService = new CSVFileService();
    }

    @Test
    public void whenReadingFromFile_withValidFile_returnsExpectedValues() throws IOException {
        final List<List<String>> results = fileService.readFromFile(file.getFile());
        final List<List<String>> expected = setUpExpectedResults();

        for (int i = 0; i < results.size(); i++) {
            assertEquals(expected.get(i).get(0), results.get(i).get(0));
            assertEquals(expected.get(i).get(1), results.get(i).get(1));
            assertEquals(expected.get(i).get(2), results.get(i).get(2));
        }
    }

    @Test
    public void whenReadingFromFile_withInvalidFile_throwsException() {
        File invalidFile = null;
        Assertions.assertThrows(RuntimeException.class, () -> fileService.readFromFile(invalidFile));
    }

    private List<List<String>> setUpExpectedResults() {
        final List<List<String>> addressBook = new ArrayList<>();

        addressBook.add(Arrays.asList("Annika Beasley", "Female", "16/04/82"));
        addressBook.add(Arrays.asList("Alfie Mayo", "Male", "25/07/64"));
        addressBook.add(Arrays.asList("Stacey Castillo", "Female", "07/12/93"));
        addressBook.add(Arrays.asList("Mark Ferguson", "Male", "22/06/90"));
        addressBook.add(Arrays.asList("Poppie Cohen", "Female", "22/06/89"));

        return addressBook;
    }

}