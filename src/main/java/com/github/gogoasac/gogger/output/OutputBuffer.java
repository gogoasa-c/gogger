package com.github.gogoasac.gogger.output;

import com.github.gogoasac.gogger.output.kafka.KafkaService;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class OutputBuffer {
    private final List<OutputStream> outputStreamList;
    private final FileOutputGenerator fileOutputGenerator;
    private final KafkaService kafkaService;

    @Setter
    @Value("${logging.gogger.log-file-enabled:true}")
    private Boolean isFileLoggingEnabled;

    public OutputBuffer(final FileOutputGenerator fileOutputGenerator, KafkaService kafkaService) {
        this.fileOutputGenerator = fileOutputGenerator;
        this.kafkaService = kafkaService;

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
        this.kafkaService.sendMessage(new String(byteArray));

        for (OutputStream outputStream : this.outputStreamList) {
            outputStream.write(byteArray);
        }
    }
}
