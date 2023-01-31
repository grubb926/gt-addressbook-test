package com.example.gtaddressbooktest.service.addressBook;

import com.example.gtaddressbooktest.entity.AddressBook;
import com.example.gtaddressbooktest.entity.AddressBookEntry;
import com.example.gtaddressbooktest.service.addressbook.AddressBookService;
import com.example.gtaddressbooktest.service.addressbook.AddressBookServiceImpl;
import com.example.gtaddressbooktest.service.file.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressBookServiceImplTest {

    @Mock
    private FileService fileService;

    @Mock
    private File file;

    private AddressBookService addressBookService;

    @BeforeEach
    void setUp() {
        addressBookService = new AddressBookServiceImpl(fileService);
    }

    @Test
    public void whenGeneratingAddressBook_withValidFile_thenReturnsValidAddressBook() throws FileNotFoundException {
        when(fileService.readFromFile(file)).thenReturn(setUpFileServiceReturnList());

        final AddressBook result = addressBookService.generateAddressBookFromFile(file);
        final List<AddressBookEntry> resultEntries = result.getAddressBookEntries();
        final List<AddressBookEntry> expectedEntries = setUpExpectedAddressBook().getAddressBookEntries();

        for (int i = 0; i < resultEntries.size(); i++) {
            assertEquals(expectedEntries.get(i).name(), resultEntries.get(i).name());
            assertEquals(expectedEntries.get(i).gender(), resultEntries.get(i).gender());
            assertEquals(expectedEntries.get(i).dateOfBirth(), resultEntries.get(i).dateOfBirth());
        }
    }

    @Test
    public void whenGeneratingAddressBook_withInvalidFile_throwsException() throws FileNotFoundException {
        final File invalidFile = new File("invalid-path");

        when(fileService.readFromFile(invalidFile)).thenThrow(FileNotFoundException.class);
        Assertions.assertThrows(FileNotFoundException.class, () -> addressBookService.generateAddressBookFromFile(invalidFile));
    }
    @Test
    public void whenFindingOldestPerson_returnsEntryForOldestPerson() {
        final AddressBook addressBook = setUpExpectedAddressBook();
        final AddressBookEntry result = addressBookService.findOldestPerson(addressBook);
        assertEquals(addressBook.getAddressBookEntries().get(1), result);
    }

    @Test
    public void whenFindingAmountOfSpecifiedGender_andGenderIsFemale_thenReturnsThree() {
        final int result = addressBookService.findQuantityOfGivenGender(setUpExpectedAddressBook(), "female");
        assertEquals(3, result);
    }

    @Test
    public void whenFindingAmountOfSpecifiedGender_andGenderIsMale_thenReturnsTwo() {
        final int result = addressBookService.findQuantityOfGivenGender(setUpExpectedAddressBook(), "male");
        assertEquals(2, result);
    }

    @Test
    public void whenFindingAgeDifferenceInDays_andFirstIsOlderThanSecond_returnsExpectedLongValue() {
        final AddressBook addressBook = setUpExpectedAddressBook();
        final long result = addressBookService.findAgeDifferenceInDays(addressBook.getAddressBookEntries().get(4), addressBook.getAddressBookEntries().get(2));
        assertEquals(1629, result);
    }

    @Test
    public void whenFindingAgeDifferenceInDays_andFirstIsOlderThanSecond_returnsAPositiveLongValue() {
        final AddressBook addressBook = setUpExpectedAddressBook();
        final long result = addressBookService.findAgeDifferenceInDays(addressBook.getAddressBookEntries().get(2), addressBook.getAddressBookEntries().get(4));
        assertEquals(1629, result);
    }

    private List<List<String>> setUpFileServiceReturnList() {
        final List<List<String>> addressBook = new ArrayList<>();

        addressBook.add(Arrays.asList("Annika Beasley", "Female", "16/04/82"));
        addressBook.add(Arrays.asList("Alfie Mayo", "Male", "25/07/64"));
        addressBook.add(Arrays.asList("Stacey Castillo", "Female", "07/12/93"));
        addressBook.add(Arrays.asList("Mark Ferguson", "Male", "22/06/90"));
        addressBook.add(Arrays.asList("Poppie Cohen", "Female", "22/06/89"));

        return addressBook;
    }

    private AddressBook setUpExpectedAddressBook() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        final AddressBookEntry entry1 = new AddressBookEntry("Annika Beasley", "Female", LocalDate.parse("16/04/82", formatter));
        final AddressBookEntry entry2 = new AddressBookEntry("Alfie Mayo", "Male", LocalDate.parse("25/07/64", formatter));
        final AddressBookEntry entry3 = new AddressBookEntry("Stacey Castillo", "Female", LocalDate.parse("07/12/93", formatter));
        final AddressBookEntry entry4 = new AddressBookEntry("Mark Ferguson", "Male", LocalDate.parse("22/06/90", formatter));
        final AddressBookEntry entry5 = new AddressBookEntry("Poppie Cohen", "Female", LocalDate.parse("22/06/89", formatter));

        return new AddressBook(Arrays.asList(entry1, entry2, entry3, entry4, entry5));
    }
}