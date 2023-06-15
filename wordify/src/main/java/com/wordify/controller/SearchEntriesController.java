package com.wordify.controller;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.json.ObjectMapperSingleton;
import com.wordify.model.dto.DefinitionContextDto;
import com.wordify.model.dto.DefinitionRequestDto;
import com.wordify.model.dto.EntryDto;
import com.wordify.model.dto.SearchRequestDto;
import com.wordify.service.api.EntryService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchEntriesController {
    public void searchEntries(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String json;
        EntryService service = new EntryService();
        ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
        SearchRequestDto searchRequest = objectMapper.readValue(request.getParameter("json"),SearchRequestDto.class);
        searchRequest.setLoginUser(Integer.parseInt((String) request.getAttribute("loginUser")));
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
    public void searchEntriesTest(SearchRequestDto searchRequest,HttpServletResponse response) throws Exception{
        String json;
        EntryService service = new EntryService();
        ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
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
    public void getDetailsTest(DefinitionRequestDto requestDto ,HttpServletResponse response) throws Exception{
        String json;
        EntryService service = new EntryService();
        ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
        DefinitionContextDto list = service.getEntryDetails(requestDto);
        try{
        json = objectMapper.writeValueAsString(list);
        System.out.println(json);
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