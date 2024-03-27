package Client.Chunk;

import Client.Block.Block;
import Client.Block.Grass;
import Client.Renderer.Render;
import Client.Renderer.Texture;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Chunk {
    public Block[][][] blocks = new Block[16][256][16];
    public boolean dirty = true;
    public int xPos;
    public int zPos;
    public int lists = GL11.glGenLists(1);

    public int texID;
    public Chunk(int xPos, int zPos) {
        this.xPos = xPos;
        this.zPos = zPos;
        texID = Texture.loadTexture("Resources/Grass.png", GL11.GL_NEAREST);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 64; j++) {
                for (int k = 0; k < 16; k++) {
                    blocks[i][j][k] = new Grass(i+xPos, j, k+zPos, true);
                }
            }
        }
    }

    private void rebuild() {
        GL11.glNewList(lists, GL11.GL_COMPILE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 16; k++) {
                    if (blocks[i][j][k] != null) {
                        blocks[i][j][k].drawBlock();
                    }
                }
            }
        }
        Render.drawFrame();

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEndList();
    }
    public void render() {
        if (dirty) {
            dirty = false;
            rebuild();
        }
        GL11.glCallList(lists);
    }
    public void applyChanges(byte[] data) {
        for (int i = 0; i < data.length/4; i++) {
            byte block = data[i*4];
            byte x = data[i*4+1];
            byte y = data[i*4+2];
            byte z = data[i*4+3];

            if (block == (byte)0x01) {
                blocks[x][y][z] = null;
                dirty = true;
            }
            if (block == (byte)0x02) {
                blocks[x][y][z] = new Grass(x, y, z, true);
                dirty = true;
            }
        }
    }

    public Block getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public void setBlock(int x, int y, int z, Block b) {
        blocks[x][y][z] = b;
        //System.out.println("set: " + b + ", at: " + x + ", " + y + ", " + z);
        dirty = true;
    }
}
