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
    public int tex;
    public boolean multiTex;
    public Block(int x, int y, int z, boolean solid, int tex, boolean multiTex) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.solid = solid;
        this.tex = tex;
        this.multiTex = multiTex;
    }
    public void drawBlock() {

        if (World.getBlock(x, y, z + 1) != null) {
            if (!World.getBlock(x, y, z + 1).solid && solid) {
                drawFront();
            }
        } else {
            drawFront();
        }
        if (World.getBlock(x, y, z - 1) != null) {
            if (!(World.getBlock(x, y, z - 1)).solid && solid) {
                drawBack();
            }
        } else {
            drawBack();
        }
        if (World.getBlock(x - 1, y, z) != null) {
            if (!(World.getBlock(x - 1, y, z)).solid && solid) {
                drawLeft();
            }
        } else {
            drawLeft();
        }
        if (World.getBlock(x + 1, y, z) != null) {
            if (!(World.getBlock(x + 1, y, z)).solid && solid) {
                drawRight();
            }
        } else {
            drawRight();
        }
        if (World.getBlock(x, y + 1, z) != null) {
            if (!(World.getBlock(x, y + 1, z)).solid && solid) {
                drawTop();
            }
        } else {
            drawTop();
        }
        if (World.getBlock(x, y - 1, z) != null) {
            if (!(World.getBlock(x, y - 1, z)).solid && solid) {
                drawBottom();
            }
        } else {
            drawBottom();
        }
    }

    private void drawBottom() {
        float u;
        if (multiTex) {
            u = (tex+5)/16f;
        } else {
            u = tex / 16f;
        }
        float u1 = u+1f/16f;
        float v = 0;
        float v1 = v+1f/16f;
        Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, u1, v);
        Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, u1, v1);
        Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, u, v1);
        Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, u, v);

        if (!solid) {
            Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, u1, v);
            Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, u, v);
            Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, u, v1);
            Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, u1, v1);
        }
    }

    private void drawTop() {
        float u;
        if (multiTex) {
            u = (tex+4)/16f;
        } else {
            u = tex / 16f;
        }
        float u1 = u+1f/16f;
        float v = 0;
        float v1 = v+1f/16f;
        Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, u1, v);
        Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, u, v);
        Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, u, v1);
        Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, u1, v1);
        if (!solid) {
            Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, u1, v);
            Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, u1, v1);
            Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, u, v1);
            Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, u, v);
        }
    }

    private void drawRight() {
        float u;
        if (multiTex) {
            u = (tex+3)/16f;
        } else {
            u = tex / 16f;
        }
        float u1 = u+1f/16f;
        float v = 0;
        float v1 = v+1f/16f;
        Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, u1, v);
        Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, u1, v1);
        Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, u, v1);
        Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, u, v);
        if (!solid) {
            Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, u1, v);
            Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, u, v);
            Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, u, v1);
            Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, u1, v1);
        }
    }

    private void drawLeft() {
        float u;
        if (multiTex) {
            u = (tex+2)/16f;
        } else {
            u = tex / 16f;
        }
        float u1 = u+1f/16f;
        float v = 0;
        float v1 = v+1f/16f;
        Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, u1, v);
        Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, u, v);
        Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, u, v1);
        Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, u1, v1);
        if (!solid) {
            Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, u1, v);
            Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, u1, v1);
            Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, u, v1);
            Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, u, v);
        }
    }

    private void drawBack() {
        float u;
        if (multiTex) {
            u = (tex+1)/16f;
        } else {
            u = tex / 16f;
        }
        float u1 = u+1f/16f;
        float v = 0;
        float v1 = v+1f/16f;
        Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, u1, v);
        Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, u1, v1);
        Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, u, v1);
        Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, u, v);
        if (!solid) {
            Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, u1, v);
            Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, u, v);
            Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, u, v1);
            Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, u1, v1);
        }
    }

    private void drawFront() {
        float u;
        if (multiTex) {
            u = (tex)/16f;
        } else {
            u = tex / 16f;
        }
        float u1 = u+1f/16f;
        float v = 0;
        float v1 = v+1f/16f;
        Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, u1, v);
        Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, u, v);
        Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, u, v1);
        Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, u1, v1);
        if (!solid) {
            Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, u1, v);
            Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, u1, v1);
            Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, u, v1);
            Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, u, v);
        }
    }

    public void render() {
        GL11.glCallList(lists);
    }
}
