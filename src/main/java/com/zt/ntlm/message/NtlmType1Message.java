package com.zt.ntlm.message;

import java.util.Arrays;
import java.util.logging.StreamHandler;

/**
 * @Author: zt
 * @Date: 2018/12/22 16:35
 */
public class NtlmType1Message {

    /**
     * 协议，固定为:"NTLMSSP\0"
     */
    private static byte[] protocol;

    /**
     * 类型一的消息
     */
    private byte type = 0x01;

    /**
     * 3个0
     */
    private byte[] zero3 = new byte[3];

    private short flags = (short) 0xb203;           // 0xb203
    /**
     * domain string length
     */
    private short dom_len_1;

    /**
     * domain string length
     */
    private short dom_len_2;

    /**
     * domain string offset
     */
    private short dom_off;
    /**
     * 2个0
     */
    private byte[] zero2 = new byte[2];

    private short host_len_1;

    private short host_len_2;

    private short host_off = 0x20;

    private byte[] zero2_v1 = new byte[2];

    /**
     * host String ASCII
     */
    private String host;

    /**
     * domain String  ASCII
     */
    private String domain;

    static {
        protocol = new byte[8];
        protocol = "NTLMSSP\0".getBytes();
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
        this.host_len_1 = (short) host.length();
        this.host_len_2 = (short) host.length();
        this.dom_off = (short) (this.host_off + this.host_len_1);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
        this.dom_len_1 = (short) domain.length();
        this.dom_len_2 = (short) domain.length();
    }


    public String cotent(){
       StringBuilder stringBuilder=new StringBuilder();
       stringBuilder.append(type)
               .append( Arrays.toString(zero3))
               .append(flags)
               .append(dom_len_1)
               .append(dom_len_2)
               .append(dom_off)
               .append(Arrays.toString(zero2))
               .append(host_len_1)
               .append(host_len_2)
               .append(host_off)
               .append(Arrays.toString(zero2_v1))
               .append(host)
               .append(domain);
       return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "NtlmType1Message{" +
                "type=" + type +
                ", zero3=" + Arrays.toString(zero3) +
                ", flags=" + flags +
                ", dom_len_1=" + dom_len_1 +
                ", dom_len_2=" + dom_len_2 +
                ", dom_off=" + dom_off +
                ", zero2=" + Arrays.toString(zero2) +
                ", host_len_1=" + host_len_1 +
                ", host_len_2=" + host_len_2 +
                ", host_off=" + host_off +
                ", zero2_v1=" + Arrays.toString(zero2_v1) +
                ", host='" + host + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
