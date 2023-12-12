package com.mtgo.exam.orderservice.utils;

import com.mtgo.exam.orderservice.model.Order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class JsonReader {

    public static Order readOrderFromJson() {
        Order order = new Order();
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
                        try{
                            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm"));
                        } catch (DateTimeParseException e){
                            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        }
                    }).create();
            Reader reader = Files.newBufferedReader(Paths.get("src/test/resources/data/order.json"));
            order = gson.fromJson(reader, Order.class);
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return order;
    }
}
