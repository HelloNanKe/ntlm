package com.zt.ntlm;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @Author: zt
 * @Date: 2018/12/21 18:02
 */
@Controller
@RequestMapping(value = "/ntlm")
public class TestMain {

    private static final String SERVER_HOST = "192.168.1.175";
    //    目标应用的端口
    private static final int SERVER_PORT = 80;
    //    目标应用的协议
    private static final String SERVER_PROTOCO = "http";

    private static final String USER_NAME="Administrator";

    private static final String PWD="Cipher2018!";

    public static void main(String[] args) throws IOException {
//        doGet();
    }

    @RequestMapping(value = "/test")
    public void ntlmTest(HttpServletResponse response){
        doGet(response);
    }

    private static void doGet(HttpServletResponse response) {
        String userpwd = "Administrator:Cipher2018!";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        NTCredentials creds = new NTCredentials(userpwd);
        httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
        HttpHost target = new HttpHost(SERVER_HOST, SERVER_PORT, SERVER_PROTOCO);
        HttpGet httpget = new HttpGet("/");
        HttpResponse response1 = null;
        try {
            response1 = httpclient.execute(target, httpget);
            HttpEntity entity1 = response1.getEntity();
            String res = entityToString(entity1);

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            PrintWriter out=response.getWriter();
            out.write("<script>\n" +
                    "\tlocation.href='http://192.168.1.175';\n" +
                    "</script>");


            System.err.println(res);
            if (entity1 != null) {
                entity1.consumeContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
            if (!ObjectUtils.isEmpty(httpget)) {
                httpget.releaseConnection();
            }
            if (!ObjectUtils.isEmpty(httpclient)) {
                httpclient.close();
            }
        }
    }

    private static String entityToString(HttpEntity entity) throws IOException {
        String result = "";
        if (entity != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent(), "utf-8");
            String s = inputStreamReader.getEncoding();
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line + "\n";
            }
            inputStreamReader.close();
            bufferedReader.close();
        }
        return result;
    }

    private static void doGet1() throws IOException {
    }


}
