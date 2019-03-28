package com.distribuido;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThreadSCANNER {
	  public static void main(String[] args){
	    for(int j= 0; j <= 65534; j += 10) {
	    	ArrayList<Thread> threads = new ArrayList<Thread>();
		    Map<Thread,ThreadSCAN> threadsSCAN = new HashMap<Thread,ThreadSCAN>();
		    for(int i=0; i<10; i++){
		    	if(j+i <= 65534) {
		    		ThreadSCAN threadSCAN = new ThreadSCAN();
		    		Thread thread = new Thread(threadSCAN, j+i + "");
		    		thread.start();
		    		threads.add(thread);
		    		threadsSCAN.put(thread, threadSCAN);
		    	}
		    }
		    
		    for(Thread thread: threads) {
		    	try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	System.out.println(threadsSCAN.get(thread).getResult());
		    	
		    }
	    }
	  }
	}