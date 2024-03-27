package Client.Block;

import Client.Renderer.Render;
import Client.World.World;
import org.lwjgl.opengl.GL11;

public abstract class Block {
    public boolean solid;
    public int x;
    public int y;
    public int z;
    private int lists;
    public String texture;
    public Block(int x, int y, int z, boolean solid) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.solid = solid;
    }
    public void drawBlock() {

        if (World.getBlock(x, y, z + 1) != null) {
            if (!World.getBlock(x, y, z + 1).solid) {
                drawFront();
            }
        } else {
            drawFront();
        }
        if (World.getBlock(x, y, z - 1) != null) {
            if (!(World.getBlock(x, y, z - 1)).solid) {
                drawBack();
            }
        } else {
            drawBack();
        }
        if (World.getBlock(x - 1, y, z) != null) {
            if (!(World.getBlock(x - 1, y, z)).solid) {
                drawLeft();
            }
        } else {
            drawLeft();
        }
        if (World.getBlock(x + 1, y, z) != null) {
            if (!(World.getBlock(x + 1, y, z)).solid) {
                drawRight();
            }
        } else {
            drawRight();
        }
        if (World.getBlock(x, y + 1, z) != null) {
            if (!(World.getBlock(x, y + 1, z)).solid) {
                drawTop();
            }
        } else {
            drawTop();
        }
        if (World.getBlock(x, y - 1, z) != null) {
            if (!(World.getBlock(x, y - 1, z)).solid) {
                drawBottom();
            }
        } else {
            drawBottom();
        }
    }

    private void drawBottom() {
        Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, 1.0f, 0.0f);
        Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, 1.0f, 1.0f);
        Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 1.0f);
        Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, 0.0f, 0.0f);
    }

    private void drawTop() {
        Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f);
        Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f);
        Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 1.0f);
        Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, 1.0f, 1.0f);
    }

    private void drawRight() {
        Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f);
        Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, 1.0f, 1.0f);
        Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, 0.0f, 1.0f);
        Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f);
    }

    private void drawLeft() {
        Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f);
        Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f);
        Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 1.0f);
        Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, 1.0f, 1.0f);
    }

    private void drawBack() {
        Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, 1.0f, 0.0f);
        Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, 1.0f, 1.0f);
        Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 1.0f);
        Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f);
    }

    private void drawFront() {
        Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f);
        Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, 0.0f, 0.0f);
        Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, 0.0f, 1.0f);
        Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, 1.0f, 1.0f);
    }

    public void render() {
        GL11.glCallList(lists);
    }
}
