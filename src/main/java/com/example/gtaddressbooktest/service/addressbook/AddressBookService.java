package com.example.gtaddressbooktest.service.addressbook;

import com.example.gtaddressbooktest.entity.AddressBook;
import com.example.gtaddressbooktest.entity.AddressBookEntry;

import java.io.File;
import java.io.FileNotFoundException;

public interface AddressBookService {

    AddressBook generateAddressBookFromFile(File file) throws FileNotFoundException;
    AddressBookEntry findOldestPerson(AddressBook addressBook);
    int countAmountOfGender(AddressBook addressBook, String gender);
    long findAgeDifferenceInDays(AddressBookEntry firstPerson, AddressBookEntry secondPerson);

}
