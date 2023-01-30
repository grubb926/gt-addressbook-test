package com.example.gtaddressbooktest.service.addressbook;

import com.example.gtaddressbooktest.entity.AddressBook;

import java.io.File;
import java.io.FileNotFoundException;

public interface AddressBookService {

    AddressBook generateAddressBookFromFile(File file) throws FileNotFoundException;

}
