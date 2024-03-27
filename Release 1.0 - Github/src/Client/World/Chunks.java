package Client.World;

import Client.Chunk.Chunk;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Chunks {
    public static ConcurrentHashMap<Long, byte[]> chunks = new ConcurrentHashMap<>();
}
