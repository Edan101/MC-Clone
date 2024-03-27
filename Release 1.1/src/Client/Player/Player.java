package Client.Player;

import Client.Block.Block;
import Client.Block.Grass;
import Client.Block.Leaves;
import Client.Client;
import Client.World.Players;
import Client.World.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Player {
    public static float xPos = 8;
    public static float yPos = 35;
    public static float zPos = 8;
    public static int xRot;
    public static int yRot;
    public static Vector3f lookVector;
    public static Vector3f walkVector;
    public static int reach = 4;
    public static float camHeight = 1.625f;
    public static float dx;
    public static float dy;
    public static float dz;
    public static byte b = 0x02;
    public static void rotate(int dx1, int dy1) {
        if (xRot + dy1 >= -90 && xRot + dy1 <= 90) {
            xRot += dy1;
        }
        yRot += dx1;
    }
    public static boolean leftClick = false;
    public static boolean rightClick = false;
    public static Vector3f hitbox = new Vector3f(0.5f, 1.8f, 0.5f);
    public static Client client;
    public static String ip;
    public static int port;
    public static int id;
    public static float aFactor = 0.00001f;
    public static void init(String ip1, int port1, int id1) {
        ip = ip1;
        port = port1;
        id = id1;
        client = new Client(ip1, port1, id1);
        Thread c = new Thread(client);
        c.start();
    }
    public static void update(long a) {
        lookVector = new Vector3f((float)Math.sin(Math.toRadians(yRot))*(float)Math.cos(Math.toRadians(xRot)),
                (float)Math.sin(Math.toRadians(-xRot)),
                (float)Math.cos(Math.toRadians(yRot))*(float)Math.cos(Math.toRadians(xRot)));

        walkVector = new Vector3f((float)Math.sin(Math.toRadians(yRot)),
                0f,
                (float)Math.cos(Math.toRadians(yRot)));

        Vector3f[] bs = raycast();
        Vector3f breakB = bs[0];
        Vector3f placeB = bs[1];

        if (Mouse.isButtonDown(0) && breakB != null && !leftClick) {
            leftClick = true;
            int x = (int) breakB.x;
            int y = (int) breakB.y;
            int z = (int) breakB.z;

            World.setBlock(x, y, z, (byte)0x01);
            serverBreak(x, y, z);
        } else if (!Mouse.isButtonDown(0) && leftClick) {
            leftClick = false;
        }

        try {
            if (Mouse.isButtonDown(1) && placeB != null && !rightClick) {
                rightClick = true;
                int x = (int) placeB.x;
                int y = (int) placeB.y;
                int z = (int) placeB.z;

                if (testCollisionPlace(x, y, z)) {
                    World.setBlock(x, y, z, b);
                    serverPlace(x, y, z);
                }
            } else if (!Mouse.isButtonDown(1) && rightClick) {
                rightClick = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            b = (byte)0x02;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            b = (byte)0x03;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
            b = (byte)0x04;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
            b = (byte)0x05;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
            b = (byte)0x06;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
            b = (byte)0x07;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
            b = (byte)0x08;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
            b = (byte)0x09;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
            b = (byte)0x0A;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            dz = zPos - 0.001f*walkVector.z * a * aFactor;
            dx = xPos + 0.001f*walkVector.x * a * aFactor;
            xPos = testCollision(dx, yPos, zPos, dx, xPos);
            zPos = testCollision(xPos, yPos, dz, dz, zPos);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            dz = zPos + 0.001f*walkVector.z * a * aFactor;
            dx = xPos - 0.001f*walkVector.x * a * aFactor;
            xPos = testCollision(dx, yPos, zPos, dx, xPos);
            zPos = testCollision(xPos, yPos, dz, dz, zPos);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            dz = zPos + 0.001f*walkVector.x * a * aFactor;
            dx = xPos + 0.001f*walkVector.z * a * aFactor;
            xPos = testCollision(dx, yPos, zPos, dx, xPos);
            zPos = testCollision(xPos, yPos, dz, dz, zPos);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            dz = zPos - 0.001f*walkVector.x * a * aFactor;
            dx = xPos - 0.001f*walkVector.z * a * aFactor;
            xPos = testCollision(dx, yPos, zPos, dx, xPos);
            zPos = testCollision(xPos, yPos, dz, dz, zPos);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            dy = yPos+0.001f * a * aFactor;
            yPos = testCollision(xPos, dy, zPos, dy, yPos);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            dy = yPos-0.001f * a * aFactor;
            yPos = testCollision(xPos, dy, zPos, dy, yPos);
        }
    }

    private static void serverBreak(int x, int y, int z) {
        String message = new String("break:"+x+":"+y+":"+z+":");
        client.sendData(message);
    }
    private static void serverPlace(int x, int y, int z) {
        String message = new String("place:"+x+":"+y+":"+z+":"+b+":");
        client.sendData(message);
    }

    private static boolean testCollisionPlace(int x, int y, int z) {
        boolean toRet = true;

        Vector3f[] hitpoints = new Vector3f[20];
        hitpoints[0] = new Vector3f(hitbox.x/2f+xPos, hitbox.y+yPos, hitbox.z/2f+zPos);
        hitpoints[1] = new Vector3f(-hitbox.x/2f+xPos, hitbox.y+yPos, hitbox.z/2f+zPos);
        hitpoints[2] = new Vector3f(hitbox.x/2f+xPos, hitbox.y+yPos, -hitbox.z/2f+zPos);
        hitpoints[3] = new Vector3f(-hitbox.x/2f+xPos, hitbox.y+yPos, -hitbox.z/2f+zPos);
        hitpoints[4] = new Vector3f(hitbox.x/2f+xPos, (3f/4f)*hitbox.y+yPos, hitbox.z/2f+zPos);
        hitpoints[5] = new Vector3f(-hitbox.x/2f+xPos, (3f/4f)*hitbox.y+yPos, hitbox.z/2f+zPos);
        hitpoints[6] = new Vector3f(hitbox.x/2f+xPos, (3f/4f)*hitbox.y+yPos, -hitbox.z/2f+zPos);
        hitpoints[7] = new Vector3f(-hitbox.x/2f+xPos, (3f/4f)*hitbox.y+yPos, -hitbox.z/2f+zPos);
        hitpoints[8] = new Vector3f(hitbox.x/2f+xPos, (2f/4f)*hitbox.y+yPos, hitbox.z/2f+zPos);
        hitpoints[9] = new Vector3f(-hitbox.x/2f+xPos, (2f/4f)*hitbox.y+yPos, hitbox.z/2f+zPos);
        hitpoints[10] = new Vector3f(hitbox.x/2f+xPos, (2f/4f)*hitbox.y+yPos, -hitbox.z/2f+zPos);
        hitpoints[11] = new Vector3f(-hitbox.x/2f+xPos, (2f/4f)*hitbox.y+yPos, -hitbox.z/2f+zPos);
        hitpoints[12] = new Vector3f(hitbox.x/2f+xPos, (1f/4f)*hitbox.y+yPos, hitbox.z/2f+zPos);
        hitpoints[13] = new Vector3f(-hitbox.x/2f+xPos, (1f/4f)*hitbox.y+yPos, hitbox.z/2f+zPos);
        hitpoints[14] = new Vector3f(hitbox.x/2f+xPos, (1f/4f)*hitbox.y+yPos, -hitbox.z/2f+zPos);
        hitpoints[15] = new Vector3f(-hitbox.x/2f+xPos, (1f/4f)*hitbox.y+yPos, -hitbox.z/2f+zPos);
        hitpoints[16] = new Vector3f(hitbox.x/2f+xPos, +yPos, hitbox.z/2f+zPos);
        hitpoints[17] = new Vector3f(-hitbox.x/2f+xPos, +yPos, hitbox.z/2f+zPos);
        hitpoints[18] = new Vector3f(hitbox.x/2f+xPos, +yPos, -hitbox.z/2f+zPos);
        hitpoints[19] = new Vector3f(-hitbox.x/2f+xPos, +yPos, -hitbox.z/2f+zPos);

        for (Vector3f p: hitpoints) {
            int x1 = (int)Math.floor(p.x);
            int y1 = (int)Math.floor(p.y);
            int z1 = (int)Math.floor(p.z);

            if (x1 == x && y1 == y && z1 == z) {
                toRet = false;
            }
        }

        return toRet;
    }

    public static float testCollision(float x, float y, float z, float nw, float ol) {
        float toRet = nw;

        Vector3f[] hitpoints = new Vector3f[20];
        hitpoints[0] = new Vector3f(hitbox.x/2f+x, hitbox.y+y, hitbox.z/2f+z);
        hitpoints[1] = new Vector3f(-hitbox.x/2f+x, hitbox.y+y, hitbox.z/2f+z);
        hitpoints[2] = new Vector3f(hitbox.x/2f+x, hitbox.y+y, -hitbox.z/2f+z);
        hitpoints[3] = new Vector3f(-hitbox.x/2f+x, hitbox.y+y, -hitbox.z/2f+z);
        hitpoints[4] = new Vector3f(hitbox.x/2f+x, (3f/4f)*hitbox.y+y, hitbox.z/2f+z);
        hitpoints[5] = new Vector3f(-hitbox.x/2f+x, (3f/4f)*hitbox.y+y, hitbox.z/2f+z);
        hitpoints[6] = new Vector3f(hitbox.x/2f+x, (3f/4f)*hitbox.y+y, -hitbox.z/2f+z);
        hitpoints[7] = new Vector3f(-hitbox.x/2f+x, (3f/4f)*hitbox.y+y, -hitbox.z/2f+z);
        hitpoints[8] = new Vector3f(hitbox.x/2f+x, (2f/4f)*hitbox.y+y, hitbox.z/2f+z);
        hitpoints[9] = new Vector3f(-hitbox.x/2f+x, (2f/4f)*hitbox.y+y, hitbox.z/2f+z);
        hitpoints[10] = new Vector3f(hitbox.x/2f+x, (2f/4f)*hitbox.y+y, -hitbox.z/2f+z);
        hitpoints[11] = new Vector3f(-hitbox.x/2f+x, (2f/4f)*hitbox.y+y, -hitbox.z/2f+z);
        hitpoints[12] = new Vector3f(hitbox.x/2f+x, (1f/4f)*hitbox.y+y, hitbox.z/2f+z);
        hitpoints[13] = new Vector3f(-hitbox.x/2f+x, (1f/4f)*hitbox.y+y, hitbox.z/2f+z);
        hitpoints[14] = new Vector3f(hitbox.x/2f+x, (1f/4f)*hitbox.y+y, -hitbox.z/2f+z);
        hitpoints[15] = new Vector3f(-hitbox.x/2f+x, (1f/4f)*hitbox.y+y, -hitbox.z/2f+z);
        hitpoints[16] = new Vector3f(hitbox.x/2f+x, +y, hitbox.z/2f+z);
        hitpoints[17] = new Vector3f(-hitbox.x/2f+x, +y, hitbox.z/2f+z);
        hitpoints[18] = new Vector3f(hitbox.x/2f+x, +y, -hitbox.z/2f+z);
        hitpoints[19] = new Vector3f(-hitbox.x/2f+x, +y, -hitbox.z/2f+z);

        for (Vector3f p: hitpoints) {
            int x1 = (int)Math.floor(p.x);
            int y1 = (int)Math.floor(p.y);
            int z1 = (int)Math.floor(p.z);

            if (World.getBlock(x1, y1, z1) != null) {
                toRet = ol;
            }
        }

        return toRet;
    }
    public static Vector3f[] raycast() {
        Block breakB = null;
        Vector3f placeB = null;
        for (int i = 0; i < reach*1000; i++) {
            int x = (int)Math.floor(lookVector.x * i / 1000 + xPos);
            int y = (int)Math.floor(lookVector.y * i / 1000 + yPos + camHeight);
            int z = (int)Math.floor(-lookVector.z * i / 1000 + zPos);
            Block block = World.getBlock(x, y, z);
            if (block != null) {
                breakB = block;
                break;
            }
            placeB = new Vector3f(x, y, z);
        }
        if (breakB != null) {
            return new Vector3f[]{new Vector3f(breakB.x, breakB.y, breakB.z), placeB};
        } else {
            return new Vector3f[2];
        }
    }

    public static void loadChunk() {
        String message = new String("loadChunk:"+id+":"+xPos+":"+yPos+":");
        client.sendData(message);
    }

    public static void tick() {
        String message = new String("pos:"+id+":"+xPos+":"+yPos+":"+zPos+":");
        client.sendData(message);
    }
}
