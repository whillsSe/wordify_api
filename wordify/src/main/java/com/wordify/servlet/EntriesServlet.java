package com.wordify.servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.controller.RegisterEntryController;
import com.wordify.controller.SearchEntriesController;
import com.wordify.json.ObjectMapperSingleton;
import com.wordify.model.dto.DefinitionRequestDto;
import com.wordify.model.dto.EntryRegistrationDto;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns="/Entries",asyncSupported = true)
public class EntriesServlet extends HttpServlet {
    private ExecutorService executor;

    @Override
    public void init() throws ServletException {
        // ExecutorServiceを初期化
        executor = Executors.newFixedThreadPool(5);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        setAccessControlHeaders(response);
        DefinitionRequestDto definitionRequest = new DefinitionRequestDto();
        definitionRequest.setWordId(Integer.parseInt((String) request.getParameter("wordId")));
        definitionRequest.setPhoneticId(Integer.parseInt((String) request.getParameter("phoneticId")));
        definitionRequest.setLoginUser(Integer.parseInt((String) request.getParameter("loginUser")));
        definitionRequest.setType((String) request.getParameter("type"));
        List<Integer> idList = new ArrayList<>();
        if(definitionRequest.getType() == "specific"){
            String idsParam = request.getParameter("ids");
        if (idsParam != null && !idsParam.isEmpty()) {
            String[] idArray = idsParam.split(",");
            for (String id : idArray) {
                try {
                    int parsedId = Integer.parseInt(id);
                    idList.add(parsedId);
                } catch (NumberFormatException e) {
                    // 数字に変換できない場合の処理
                    e.printStackTrace();
                }
            }
        }
        }
          definitionRequest.setIds(idList);
        AsyncContext asyncContext = request.startAsync();
        Runnable task = () -> {
            System.out.println("リクエストとりあえずtodoitoru");//OK
            try {
                SearchEntriesController controller = new SearchEntriesController();
                controller.getDetailsTest(definitionRequest, response);
                asyncContext.complete();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        };

        // タスクをExecutorServiceに提出
        executor.execute(task);
    }
    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
        setAccessControlHeaders(response);
        ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        EntryRegistrationDto requestEntry = objectMapper.readValue(requestBody.toString(),EntryRegistrationDto.class);
        AsyncContext asyncContext = request.startAsync();
        Runnable task = () ->{
            RegisterEntryController controller = new RegisterEntryController();
            try {
                controller.registerEntry(requestEntry,response);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ServletException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            asyncContext.complete();
        };
        executor.submit(task);
    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CORS設定
        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8000"); // ここで許可するオリジンを指定します
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT"); // 許可するHTTPメソッドを指定します
        resp.setHeader("Access-Control-Max-Age", "3600"); // プリフライトレスポンスをキャッシュする時間を秒単位で指定します
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorizarion");  // 許可するリクエストヘッダーを指定します
    }
    @Override
    public void destroy() {
        // すべてのタスクが終了したらExecutorServiceをシャットダウン
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}

