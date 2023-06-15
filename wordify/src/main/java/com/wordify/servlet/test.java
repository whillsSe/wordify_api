package com.wordify.servlet;
import java.io.*;
import java.util.concurrent.*;


import com.wordify.controller.testController;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/test/searchEntries",asyncSupported = true)
public class test extends HttpServlet {
    private ExecutorService executor;
    @Override
    public void init() throws ServletException {
        // ExecutorServiceを初期化
        executor = Executors.newFixedThreadPool(5);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
         {
            //重要：asyncContextを使用すれば、requestをControllerで処理させることが出来る。役割分担的にはこちらが良い。
        setAccessControlHeaders(response);
        AsyncContext asyncContext = request.startAsync();
        Runnable task = () -> {
            //ここでControllerクラスの作成・メソッドの実行を行う事になる。
            try {
                HttpServletResponse httpResponse = (HttpServletResponse) asyncContext.getResponse();
                testController controller = new testController();
                controller.searchEntriesTest(request, httpResponse);
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
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CORS設定
        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        String htmlContent = "<!DOCTYPE html>\n"
        + "<html>\n"
        + "<head>\n"
        + "  <meta charset=\"UTF-8\">\n"
        + "  <title>サンプルページ</title>\n"
        + "</head>\n"
        + "<body>\n"
        + "  <h1>サンプルページ</h1>\n"
        + "  <p>これは簡素なサンプルページです。</p>\n"
        + "</body>\n"
        + "</html>";

// レスポンスの設定
response.setContentType("text/html");
response.setCharacterEncoding("UTF-8");

// HTMLをレスポンスとして返す
PrintWriter out = response.getWriter();
out.println(htmlContent);
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
    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8000"); // ここで許可するオリジンを指定します
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT"); // 許可するHTTPメソッドを指定します
        resp.setHeader("Access-Control-Max-Age", "3600"); // プリフライトレスポンスをキャッシュする時間を秒単位で指定します
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorizarion");  // 許可するリクエストヘッダーを指定します
    }
}