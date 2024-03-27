package Client.World;

import Client.Block.Block;
import Client.Block.Grass;
import Client.Block.Leaves;
import Client.Block.OakPlanks;
import Client.Chunk.Chunk;
import Client.Player.Player;

public class World {
    public static Chunk chunk;
    public static void init() {
        chunk = new Chunk(0, 0);
        Player.loadChunk();
    }
    public static void render() {
        if (!Chunks.chunks.isEmpty()) {
            chunk.applyChanges(Chunks.chunks.get(0L));
            Chunks.chunks.clear();
        }
        chunk.render();
    }
    public static Block getBlock(int x, int y, int z) {

        if (x >= 0 && x < 16 && z >= 0 && z < 16 && y >= 0 && y < 127 && chunk != null) {

            return chunk.getBlock(x, y, z);

        } else {
            return null;
        }
    }

    public static void setBlock(int x, int y, int z, byte b1) {
        Block b;
        if (b1 == (byte)0x02) {
            b = new Grass(x, y, z, true, 0, true);
        } else if (b1 == (byte)0x03) {
            b = new Leaves(x, y, z, false, 6, false);
        } else if (b1 == (byte)0x04) {
            b = new OakPlanks(x, y, z, true, 7, false);
        } else {
            b = null;
        }
        if (x >= 0 && x < 16 && z >= 0 && z < 16 && y >= 0 && y < 256) {
            chunk.setBlock(x, y, z, b);
        }
    }
}
