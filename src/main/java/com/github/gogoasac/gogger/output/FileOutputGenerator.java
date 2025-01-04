package com.github.gogoasac.gogger.output;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileOutputGenerator {
    private int counter = 1;

    @Value("${logging.gogger.file-path:out/}")
    private String fileDirectory;

    public FileOutputStream getFileOutputStream() {
        File logFile = new File(fileDirectory + generateFileName());
        File parentDir = logFile.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            boolean success = parentDir.mkdirs();

            if (!success) throw new RuntimeException("File directory was not created for log file.");
        }

        try {
            return new FileOutputStream(logFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        return String.format("%s_%d.log", date, counter++);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void resetCounter() {
        this.counter = 1;
    }


}
