package com.example.gtaddressbooktest;

import com.example.gtaddressbooktest.entity.AddressBook;
import com.example.gtaddressbooktest.entity.AddressBookEntry;
import com.example.gtaddressbooktest.service.addressbook.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class AddressBookRunner implements CommandLineRunner {

    @Autowired
    private AddressBookService addressBookService;

    @Value(value = "AddressBook")
    private Resource file;

    public AddressBookRunner(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @Override
    public void run(String... args) throws Exception {
        final AddressBook addressBook = addressBookService.generateAddressBookFromFile(file.getFile());
        int amountOfMales = addressBookService.findQuantityOfGivenGender(addressBook, "Male");
        System.out.println("Amount of Males: " + amountOfMales);

        final AddressBookEntry oldestPerson = addressBookService.findOldestPerson(addressBook);
        System.out.println("Oldest person: " + oldestPerson.name());

        final AddressBookEntry bill = addressBook.getAddressBookEntries().get(0);
        final AddressBookEntry paul = addressBook.getAddressBookEntries().get(1);

        final long daysDifference = addressBookService.findAgeDifferenceInDays(bill, paul);

        System.out.println("There is a difference of " + daysDifference + " days between Bill and Paul's age");
    }
}
