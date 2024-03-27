package Client.Renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {
    public static int loadTexture(String texture, int mode) {
        IntBuffer ib = BufferUtils.createIntBuffer(1);
        GL11.glGenTextures(ib);
        int id = ib.get(0);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mode);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, mode);

        try {
            BufferedImage img = ImageIO.read(new File(texture));
            int w = img.getWidth();
            int h = img.getHeight();
            ByteBuffer pixels = BufferUtils.createByteBuffer(w * h * 4);

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int raw = img.getRGB(i, j);

                    byte b = (byte)((raw >> 0) & (byte)0xFF);
                    byte g = (byte)((raw >> 8) & (byte)0xFF);
                    byte r = (byte)((raw >> 16) & (byte)0xFF);
                    byte a = (byte)((raw >> 24) & (byte)0xFF);

                    pixels.put((i + j*w)*4, (byte)r);
                    pixels.put((i + j*w)*4+1, (byte)g);
                    pixels.put((i + j*w)*4+2, (byte)b);
                    pixels.put((i + j*w)*4+3, (byte)a);

                }
            }

            GLU.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, GL11.GL_RGBA, w, h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load textures");
        }
    }
}
