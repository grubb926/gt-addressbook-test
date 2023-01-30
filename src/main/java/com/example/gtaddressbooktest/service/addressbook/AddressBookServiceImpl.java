package com.example.gtaddressbooktest.service.addressbook;

import com.example.gtaddressbooktest.entity.AddressBook;
import com.example.gtaddressbooktest.entity.AddressBookEntry;
import com.example.gtaddressbooktest.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private FileService fileService;

    public AddressBookServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    public AddressBook generateAddressBookFromFile(File file) throws FileNotFoundException {
        final List<List<String>> records = fileService.readFromFile(file);
        final AddressBook addressBook = new AddressBook();

        records.forEach(record -> {
            final String name =  record.get(0);
            final String gender =  record.get(1);
            final LocalDate dateOfBirth =  LocalDate.parse(record.get(2), DateTimeFormatter.ofPattern("dd/MM/yy", Locale.ENGLISH));

            final AddressBookEntry addressBookEntry = new AddressBookEntry(name, gender, dateOfBirth);
            addressBook.addAddressBookEntry(addressBookEntry);
        });

        return addressBook;
    }

    public AddressBookEntry findOldestPerson(AddressBook addressBook) {
        final List<AddressBookEntry> entries = addressBook.getAddressBookEntries();
        AddressBookEntry oldestPerson = entries.get(0);

        for (AddressBookEntry entry : entries) {
            oldestPerson = compareAge(entry, oldestPerson) ? entry : oldestPerson;
        }

        return oldestPerson;
    }

    private boolean compareAge(AddressBookEntry firstPerson, AddressBookEntry secondPerson) {
        return firstPerson.dateOfBirth().isBefore(secondPerson.dateOfBirth());
    }
}
