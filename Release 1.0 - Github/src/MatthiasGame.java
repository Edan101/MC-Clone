import Client.Player.Player;
import Client.Renderer.Entity;
import Client.Renderer.Render;
import Server.Server;
import Client.World.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.Random;

public class MatthiasGame implements Runnable {
    public int playerID;
    public MatthiasGame(int playerID) {
        this.playerID = playerID;
    }
    public void run() {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void start() {
        try {
            init();
            while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                runGame();
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        finally {
            cleanup();
        }
    }
    public void init() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(800, 600));
        Display.create();

        Keyboard.create();
        Mouse.create();

        GL11.glClearColor(0.5f, 0.8f, 1.0f, 0.0f);
        Mouse.setGrabbed(true);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_CULL_FACE);

        Player.init("192.168.1.66", 12345, playerID);
        World.init();
    }
    public long last = System.nanoTime();
    public long tickTime = 0;
    public void runGame() {
        long now = System.nanoTime();
        long passed = now - last;
        last = now;

        tickTime += passed;
        if (tickTime >= 1000000000L/20L) {
            tickTime = 0;
            Player.tick();
        }

        int dx = Mouse.getDX();
        int dy = Mouse.getDY();
        Player.rotate(dx, -dy);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        World.render();
        Entity.renderEntities();

        Player.update(passed);
        setupCam();
        Display.update();
    }

    private void setupCam() {
        float aspect = (float) Display.getWidth() / (float) Display.getHeight();

        GL11.glMatrixMode(GL11.GL_PROJECTION_MATRIX);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GLU.gluPerspective(70.0f, aspect, 0.05f, 1000f);

        GL11.glCullFace(GL11.GL_BACK);

        movCam();
    }

    private void movCam() {
        GL11.glRotatef(Player.xRot, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(Player.yRot, 0.0f, 1.0f, 0.0f);

        GL11.glTranslatef(-Player.xPos, -Player.yPos-Player.camHeight, -Player.zPos);
    }

    public void cleanup() {
        Keyboard.destroy();
        Mouse.destroy();
        Display.destroy();
        System.exit(12345);
    }
}