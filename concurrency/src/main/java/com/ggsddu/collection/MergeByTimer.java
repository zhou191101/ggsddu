package com.ggsddu.collection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author zhoup
 */
public class MergeByTimer {

	private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

	private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	private static ConcurrentHashMap<String, Set<String>> map1 = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, Set<String>> map2 = new ConcurrentHashMap<>();
	private static volatile boolean flag = true;

	public static void main(String[] args){


		scheduler.scheduleAtFixedRate(() -> {
			if(flag){
				flag = false;
				System.out.println(map1);
				map1.clear();
			} else{
				flag = true;
				System.out.println(map2);
				map2.clear();
			}
		}, 20, 20, TimeUnit.SECONDS);

		try{
			ServerSocket serverSocket = new ServerSocket(9999);
			Socket socket = serverSocket.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true){
				String line = br.readLine();
				String[] lines = line.split(",");
				Set<String> set = new HashSet<>();
				set.add(lines[1]);
				Thread.sleep(200);
				executorService.submit(() -> {
					if(flag){
						map1.merge(lines[0], set, (v1, v2) -> {
							v1.addAll(v2);
							return v1;
						});
					} else{
						map2.merge(lines[0], set, (v1, v2) -> {
							v1.addAll(v2);
							return v1;
						});
					}
				});
			}
		} catch(IOException e){
			e.printStackTrace();
		} catch(InterruptedException e){
			e.printStackTrace();
		}


	}

	private static void mergeDataAndPrint(){

	}


}
