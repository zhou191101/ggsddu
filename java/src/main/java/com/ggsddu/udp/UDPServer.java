package com.ggsddu.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

    public static void main(String[] args) {
        try (DatagramSocket datagramSocket = new DatagramSocket(6789)) {
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(request);
                final DatagramPacket reply = new DatagramPacket(request.getData(),
                        request.getLength(), request.getAddress(), request.getPort());
                datagramSocket.send(reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
