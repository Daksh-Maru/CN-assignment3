package com.my_first_http_server.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class json {
    private static ObjectMapper myObjectMapper = defaulObjectMapper();

    public static ObjectMapper defaulObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }
    /*It simply used to create a JsonNode object from json_string */
    /*json_string -> Json_node -> object */
    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMapper.readTree(jsonSrc);
    }

    /*It converts the JsonNode to object value */
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    /*It converts the object value to JsonNode*/
    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    /*This simply generates a json file by calling the generateJson func. */
    /*First object -> Json Node -> json_string */
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    /*This generates a jason string format from some object --> method ObjectWriter's object.writeValueAsString. */
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if(pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(o);
    }
}
