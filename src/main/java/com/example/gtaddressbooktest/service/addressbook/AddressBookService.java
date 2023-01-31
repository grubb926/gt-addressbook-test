package com.example.gtaddressbooktest.service.addressbook;

import com.example.gtaddressbooktest.model.AddressBook;
import com.example.gtaddressbooktest.model.AddressBookEntry;

import java.io.File;
import java.io.FileNotFoundException;

public interface AddressBookService {

    AddressBook generateAddressBookFromFile(File file) throws FileNotFoundException;
    AddressBookEntry findOldestPerson(AddressBook addressBook);
    int findQuantityOfGivenGender(AddressBook addressBook, String gender);
    long findAgeDifferenceInDays(AddressBookEntry firstPerson, AddressBookEntry secondPerson);

}
