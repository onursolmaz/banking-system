package com.akbank.bankingsystem.service;

import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    @KafkaListener(topics = "logs", groupId = "log_consumer_group")
    public void listenTransfer(@Payload String message){
       try {
           File file=new File("logs.txt");

           if(!file.exists()){
               file.createNewFile();
           }

           FileWriter writer=new FileWriter(file,true);
           writer.write(message+"\n");
           writer.close();

       }catch (IOException e){
           System.out.println(e.toString());
       }
    }


    @SneakyThrows
    public List<String> getLogsByAccountNumber(String number){

        File file=new File("logs.txt");

        FileReader reader=new FileReader(file);
        BufferedReader bReader=new BufferedReader(reader);
        String line;

        ArrayList<String> logs = new ArrayList<>();
        while ((line=bReader.readLine())!=null){
            if(line.startsWith(number)){
                logs.add("log: "+line);
            }
        }
        return logs;
    }

}

