package com.ggsddu.collection;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author zhoup
 */
public class SocketClient {
	public static void main(String[] args){
		try{

			Socket socket = new Socket("localhost", 9999);
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			while(true){
				String str = bufferedReader.readLine();
				bufferedWriter.write(str);
				System.out.println(str);
				bufferedWriter.write("\n");
				bufferedWriter.flush();
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
