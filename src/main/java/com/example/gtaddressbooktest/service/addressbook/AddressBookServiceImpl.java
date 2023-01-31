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

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    private final FileService fileService;

    @Autowired
    public AddressBookServiceImpl(final FileService fileService) {
        this.fileService = fileService;
    }

    public AddressBook generateAddressBookFromFile(final File file) throws FileNotFoundException {
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

    public AddressBookEntry findOldestPerson(final AddressBook addressBook) {
        final List<AddressBookEntry> entries = addressBook.getAddressBookEntries();
        AddressBookEntry oldestPerson = entries.get(0);

        // Changed to a for i loop so that it doesn't check index 0 with itself
        for (int i = 1; i < entries.size(); i++) {
            final AddressBookEntry entry = entries.get(i);
            oldestPerson = isFirstEntryOlder(entries.get(i), oldestPerson) ? entry : oldestPerson;
        }

        return oldestPerson;
    }
    public int findQuantityOfGivenGender(final AddressBook addressBook, final String gender) {
        int amount = 0;
        for (final AddressBookEntry addressBookEntry : addressBook.getAddressBookEntries()) {
            if (addressBookEntry.gender().equalsIgnoreCase(gender)) {
                amount += 1;
            }
        }
        return amount;
    }

    @Override
    public long findAgeDifferenceInDays(final AddressBookEntry firstEntry, final AddressBookEntry secondEntry) {
        final LocalDate firstPersonDOB = firstEntry.dateOfBirth();
        final LocalDate secondPersonDOB = secondEntry.dateOfBirth();

        long daysDifference;

        // ensures a positive number regardless of passed in entries
        if (isFirstEntryOlder(firstEntry, secondEntry))
            daysDifference = DAYS.between(firstPersonDOB, secondPersonDOB);
        else
            daysDifference = DAYS.between(secondPersonDOB, firstPersonDOB);

        return daysDifference;
    }

    private boolean isFirstEntryOlder(final AddressBookEntry firstEntry, final AddressBookEntry secondEntry) {
        return firstEntry.dateOfBirth().isBefore(secondEntry.dateOfBirth());
    }
}
