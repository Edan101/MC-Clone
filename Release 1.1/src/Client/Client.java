package Client;

import Client.Block.Block;
import Client.Block.Grass;
import Client.Chunk.Chunk;
import Client.World.Chunks;
import Client.World.Players;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements Runnable {
    public InetAddress ip;
    public int port;
    public DatagramSocket socket;
    public Client(String ip, int port, int id) {
        try {
            this.ip = InetAddress.getByName(ip);
            this.port = port;
            this.socket = new DatagramSocket();
            String join = new String("join:"+id+":");
            sendData(join);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[262144];
            DatagramPacket p = new DatagramPacket(data, data.length);
            try {
                socket.receive(p);
            } catch (IOException e) {
                //
            }
            String messageData = new String(data);
            String[] message = messageData.split(":");
            if (message[0].equals("chunkLoad")) {

                String[] bytes = message[1].split(",");
                byte[] chunkData = new byte[bytes.length];

                for (int i = 0; i < bytes.length; i++) {
                    if (bytes[i].getBytes().length < 5) {
                        chunkData[i] = (byte)(Integer.parseInt(bytes[i]) & (byte)0xFF);
                    }
                }
                //System.out.println(chunkData[0] + ", " + chunkData[1] + ", " + chunkData[2] + ", " + chunkData[3]);

                Chunks.chunks.put(0L, chunkData);

            }
            if (message[0].equals("pos")) {
                int id = Integer.parseInt(message[1]);
                if (!Players.players.containsKey(id)) {
                    Players.players.put(id, new Vector3f(Float.parseFloat(message[2]), Float.parseFloat(message[3]), Float.parseFloat(message[4])));
                } else {
                    Players.players.remove(id);
                    Players.players.put(id, new Vector3f(Float.parseFloat(message[2]), Float.parseFloat(message[3]), Float.parseFloat(message[4])));
                }
            }

        }
    }
    public void sendData(String message) {
        byte[] data = message.getBytes();
        DatagramPacket p = new DatagramPacket(data, data.length, ip, port);
        try {
            socket.send(p);
        } catch (IOException e) {
            //
        }
    }
}
