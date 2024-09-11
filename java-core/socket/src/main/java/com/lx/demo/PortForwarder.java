package com.lx.demo;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PortForwarder {
    public static void main(String[] args) throws IOException {
        int localPort = 443;
        String remoteHost = "127.0.0.1";
        int remotePort = 8443;

        ServerSocket serverSocket = new ServerSocket(localPort);
        System.out.println("Listening on port " + localPort + "...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection from " + clientSocket.getRemoteSocketAddress() + " has been established.");
            new Thread(() -> {
                try {
                    forward(clientSocket, remoteHost, remotePort);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void forward(Socket clientSocket, String remoteHost, int remotePort) throws IOException {
        Socket remoteSocket = new Socket(remoteHost, remotePort);
        new Thread(() -> {
            try {
                // Forward data from client to remote
                IOUtils.copy(clientSocket.getInputStream(), remoteSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                // Forward data from remote to client
                IOUtils.copy(remoteSocket.getInputStream(), clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}