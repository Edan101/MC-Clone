package Client.World;

import org.lwjgl.util.vector.Vector3f;

import java.util.concurrent.ConcurrentHashMap;

public class Players {
    public static ConcurrentHashMap<Integer, Vector3f> players = new ConcurrentHashMap<>();
}
