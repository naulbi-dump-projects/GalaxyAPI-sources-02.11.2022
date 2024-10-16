package ru.galaxy773.multiplatform.api.utils.zip;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@UtilityClass
public class ZipUtil {

    public void unzip(String destinationFolder, String zipFile) {
        File directory = new File(destinationFolder);

        if (!directory.exists())
            directory.mkdirs();

        byte[] buffer = new byte[2048];

        try {
            FileInputStream fInput = new FileInputStream(zipFile);
            ZipInputStream zipInput = new ZipInputStream(fInput);

            ZipEntry entry = zipInput.getNextEntry();

            while (entry != null) {
                String entryName = entry.getName();
                File file = new File(destinationFolder + File.separator + entryName);

                if (entry.isDirectory()) {
                    File newDir = new File(file.getAbsolutePath());
                    if (!newDir.exists()) {
                        boolean success = newDir.mkdirs();
                        if (success == false) {
                            System.out.println("Problem creating Folder");
                        }
                    }
                } else {
                    FileOutputStream fOutput = new FileOutputStream(file);
                    int count = 0;
                    while ((count = zipInput.read(buffer)) > 0) {
                        fOutput.write(buffer, 0, count);
                    }
                    fOutput.close();
                }
                zipInput.closeEntry();
                entry = zipInput.getNextEntry();
            }

            zipInput.closeEntry();

            zipInput.close();
            fInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
