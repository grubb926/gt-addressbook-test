package com.example.gtaddressbooktest.service.file;

import java.io.File;
import java.util.List;

public interface FileService {

    List<List<String>> readFromFile(File file);
}
