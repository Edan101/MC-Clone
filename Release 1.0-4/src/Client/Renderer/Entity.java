package Client.Renderer;

import Client.World.Players;
import org.lwjgl.util.vector.Vector3f;

public class Entity {
    public static Vector3f hitbox = new Vector3f(0.5f, 1.8f, 0.5f);
    public static void renderEntities() {
        for (Vector3f v: Players.players.values()) {
            float x = v.x;
            float y = v.y;
            float z = v.z;
            Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, 1.0f, 0.0f);
            Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, 1.0f, 1.0f);
            Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 1.0f);
            Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, 0.0f, 0.0f);

            Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f);
            Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f);
            Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 1.0f);
            Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, 1.0f, 1.0f);

            Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f);
            Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, 1.0f, 1.0f);
            Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, 0.0f, 1.0f);
            Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f);

            Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f);
            Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f);
            Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 1.0f);
            Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, 1.0f, 1.0f);

            Render.vertex(1.0f + x, 1.0f + y, 0.0f + z, 1.0f, 0.0f);
            Render.vertex(1.0f + x, 0.0f + y, 0.0f + z, 1.0f, 1.0f);
            Render.vertex(0.0f + x, 0.0f + y, 0.0f + z, 0.0f, 1.0f);
            Render.vertex(0.0f + x, 1.0f + y, 0.0f + z, 0.0f, 0.0f);

            Render.vertex(1.0f + x, 1.0f + y, 1.0f + z, 1.0f, 0.0f);
            Render.vertex(0.0f + x, 1.0f + y, 1.0f + z, 0.0f, 0.0f);
            Render.vertex(0.0f + x, 0.0f + y, 1.0f + z, 0.0f, 1.0f);
            Render.vertex(1.0f + x, 0.0f + y, 1.0f + z, 1.0f, 1.0f);

            Render.drawFrame();
        }
    }
}
