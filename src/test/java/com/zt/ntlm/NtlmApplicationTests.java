package com.zt.ntlm;

import com.zt.ntlm.common.HttpKey;
import com.zt.ntlm.service.impl.NTLMEngineImpl;
import com.zt.ntlm.utils.HttpClientUtils;
import org.apache.http.Header;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.impl.auth.NTLMEngineException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NtlmApplicationTests {

    @Autowired
    private NTLMEngine ntlmEngine;


    private String userName = "Administrator";

    private String PWD = "Cipher2018!";

    private String domain = "zhoutao";

    private String workStation = "192.168.1.175";

    @Test
    public void testNtlmType1Message() {
        try {
            String typeMessage = ntlmEngine.generateType1Msg(null, null);
            System.out.println(typeMessage);
        } catch (NTLMEngineException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNtlmType3Message() {
        try {
            ntlmEngine.generateType3Msg(userName, PWD, domain, workStation, "test");
        } catch (NTLMEngineException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetType2Response() {
        Map<String, String> headerMap = new HashMap<>();
        try {
            headerMap.put("Authorization", "Negotiate " + ntlmEngine.generateType1Msg(null, null));
            Map<String, Object> map = HttpClientUtils.doGet("http://192.168.1.175", headerMap);
            Header[] headers = (Header[]) map.get(HttpKey.HEADERS);
            String status = headerMap.get(HttpKey.STATUS_CODE);
            System.out.println("状态码:=>" + status);
            String messageType2 = null;
            for (Header header : headers) {
                System.out.println(header.getName() + "=>" + header.getValue());
                if ("WWW-Authenticate".equals(header.getName())) {
                    messageType2 = header.getValue();
                }
            }

//            NTLMEngineImpl.Type2Message type2Message = new NTLMEngineImpl.Type2Message(messageType2);
//            byte[] challenge = type2Message.getChallenge();
//            String challengeStr=new String(challenge);
            messageType2=messageType2.substring(9,messageType2.length());
            String type3Message = ntlmEngine.generateType3Msg(userName, PWD, null, null, messageType2);


            System.out.println("type3Message=>" + type3Message);

        } catch (NTLMEngineException e) {
            e.printStackTrace();
        }

    }

}

