package com.example.gtaddressbooktest.model;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {

    private final List<AddressBookEntry> addressBookEntries;

    public AddressBook() {
        addressBookEntries = new ArrayList<>();
    }

    public AddressBook(List<AddressBookEntry> addressBookEntries) {
        this.addressBookEntries = addressBookEntries;
    }

    public void addAddressBookEntry(final AddressBookEntry entry) {
        addressBookEntries.add(entry);
    }

    public List<AddressBookEntry> getAddressBookEntries() {
        return addressBookEntries;
    }
}
