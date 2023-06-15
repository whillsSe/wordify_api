package com.wordify.controller;

import java.io.PrintWriter;
import java.sql.SQLException;

import com.wordify.model.dto.EntryRegistrationDto;
import com.wordify.service.api.RegisterService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterEntryController {
    public void registerEntry(EntryRegistrationDto requestEntry,HttpServletResponse response) throws java.io.IOException, ServletException, ClassNotFoundException {
        RegisterService service = new RegisterService();
        try{
            service.registerEntry(requestEntry);
        }catch (SQLException e){
            e.printStackTrace();
        }
        response.setContentType("text/plane");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print("登録完了しました！多分ね！");
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }
}
