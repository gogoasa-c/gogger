package com.github.gogoasac.gogger.output;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class OutputBuffer {
    private final List<OutputStream> outputStreamList;
    private final FileOutputGenerator fileOutputGenerator;

    @Setter
    @Value("${logging.gogger.log-file-enabled:true}")
    private Boolean isFileLoggingEnabled;

    public OutputBuffer(final FileOutputGenerator fileOutputGenerator) {
        this.fileOutputGenerator = fileOutputGenerator;
        this.outputStreamList = new ArrayList<>();
        this.outputStreamList.add(System.out);
    }

    @PostConstruct
    private void init() {
        if (Boolean.TRUE.equals(this.isFileLoggingEnabled)) {
            this.outputStreamList.add(this.fileOutputGenerator.getFileOutputStream());
        }
    }

    public void write(byte[] byteArray) throws IOException {
        for (OutputStream outputStream : this.outputStreamList) {
            outputStream.write(byteArray);
        }
    }
}
