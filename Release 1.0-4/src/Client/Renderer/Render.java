package Client.Renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

public class Render {
    public static FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(2900000);
    public static FloatBuffer texCoordBuffer = BufferUtils.createFloatBuffer(2900000);
    public static int vertices = 0;
    public static void drawFrame() {
        vertexBuffer.flip();
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glVertexPointer(3, 0, vertexBuffer);

        texCoordBuffer.flip();
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glTexCoordPointer(2, 0, texCoordBuffer);

        GL11.glDrawArrays(GL11.GL_QUADS, 0, vertices);

        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        clear();
    }
    public static void vertex(float x, float y, float z, float u, float v) {
        vertexBuffer.put(vertices*3, x);
        vertexBuffer.put(vertices*3+1, y);
        vertexBuffer.put(vertices*3+2, z);

        texCoordBuffer.put(vertices*2, u);
        texCoordBuffer.put(vertices*2+1, v);

        vertices++;
    }
    public static void clear() {
        vertices = 0;
        vertexBuffer.clear();
        texCoordBuffer.clear();
    }
}
