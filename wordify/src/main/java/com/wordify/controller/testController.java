package com.wordify.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.json.ObjectMapperSingleton;
import com.wordify.model.dto.EntryDto;
import com.wordify.model.dto.SearchRequestDto;
import com.wordify.service.api.EntryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class testController {
    public void searchEntriesTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        SearchRequestDto searchRequest = objectMapper.readValue(requestBody.toString(), SearchRequestDto.class);
        String json;
        EntryService service = new EntryService();
        List<EntryDto> list = service.getEntriesByQuery(searchRequest);
        try{
            json = objectMapper.writeValueAsString(list);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            throw new Exception(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
        } catch (IOException e) {
            throw new ServletException(e);
        }
}
}
