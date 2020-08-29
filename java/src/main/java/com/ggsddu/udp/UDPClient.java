package com.ggsddu.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static void main(String[] args) {

        try (DatagramSocket datagramSocket = new DatagramSocket()) {

            String line = "Hello world!";
            byte[] m = line.getBytes();
            InetAddress aHost = InetAddress.getByName("127.0.0.1");
            int port = 6789;
            DatagramPacket request = new DatagramPacket(m, line.length(), aHost, port);
            datagramSocket.send(request);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(reply);
            System.out.println("Reply: " + new String(reply.getData()));
        } catch (IOException se) {
            se.printStackTrace();
        }
    }
}
