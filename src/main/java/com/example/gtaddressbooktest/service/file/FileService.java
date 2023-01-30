package com.example.gtaddressbooktest.service.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface FileService {

    List<List<String>> readFromFile(File file) throws FileNotFoundException;
}
