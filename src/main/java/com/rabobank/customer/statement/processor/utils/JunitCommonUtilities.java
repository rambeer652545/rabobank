package com.rabobank.customer.statement.processor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JunitCommonUtilities {

  public static String asJsonString(final Object obj) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(obj);
  }

  public static Object asJsonToObject(String filePath, Class<?> clazz)
      throws JsonProcessingException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode jsonNode = mapper.readValue(new File(filePath),
          JsonNode.class);
      String jsonString = mapper.writeValueAsString(jsonNode);
      return mapper.readValue(new File(filePath), clazz);
    } catch (Exception e) {
      log.error("Test case error = >", e);
    }
    return null;
  }
}