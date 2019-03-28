package com.distribuido;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class ThreadSCAN implements Runnable {
    private volatile String result;

	public static boolean checkOpenPort(int port) {
	    ServerSocket ss = null;
	    DatagramSocket ds = null;
	    try {
	        ss = new ServerSocket(port);
	        ss.setReuseAddress(true);
	        ds = new DatagramSocket(port);
	        ds.setReuseAddress(true);
	        return true;
	    } catch (IOException e) {
	    } finally {
	        if (ds != null) {
	            ds.close();
	        }

	        if (ss != null) {
	            try {
	                ss.close();
	            } catch (IOException e) {
	            }
	        }
	    }
	    return false;
	}
	
	public static boolean checkUDP(int port) {
		try {
			InetAddress address = InetAddress.getByName("127.0.0.1");
			byte[] buffer = new byte[512];

			DatagramSocket ds;
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
			byte[] bytes = new byte[32];
		
			ds = new DatagramSocket();
			ds.setSoTimeout(1000);
			ds.connect(address, port);
			ds.send(dp);
			ds.isConnected();
			dp = new DatagramPacket(bytes, bytes.length);
			ds.receive(dp);
			ds.close();
		} catch (SocketTimeoutException ex) {
			return false;
		} catch (IOException ex) {
			if(ex.getMessage().toString().equals("ICMP Port Unreachable")) {
				return true;
			}
		}
		return false;
	}
	
    @Override
    public void run() {
    	int i = Integer.valueOf(Thread.currentThread().getName());
		if(!checkOpenPort(i)) {
			if(checkUDP(i)) {
				result = ("Porta: " + i + " FECHADA - UDP");
			}else {
				result = ("Porta: " + i + " FECHADA - TCP");
			}
		}else {
			result = ("Porta: " + i + " ABERTA");
		}
    }

    public String getResult() {
        return result;
    }
}