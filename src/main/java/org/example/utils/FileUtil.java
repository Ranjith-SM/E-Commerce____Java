package org.example.utils;

import java.io.File;

public class FileUtil {
    private static File credentialFile;
    public static File getCredentialFile() {
        if(credentialFile == null) {
            credentialFile = new File("src/main/java/org/example/assets/credentials.csv");
        }
        return credentialFile;
    }

    public static String getFilePath() {
        return "src/main/java/org/example/assets/";
    }
}
