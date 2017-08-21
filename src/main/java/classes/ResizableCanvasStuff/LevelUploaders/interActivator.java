package classes.ResizableCanvasStuff.LevelUploaders;

public class interActivator {
    LevelUploader LuP;

    public interActivator(LevelUploader luP) {
        this.LuP = luP;
    }

    public void activateRight() {
            try {
                if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 1))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 1))] == null) &&
                    (LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 1))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 0))] == null)) {
                    LuP.gc.translate(-LuP.gm.get("MOVEMENT"), 0);
                    LuP.ATX += LuP.gm.get("MOVEMENT");
                } else {
                    LuP.x -= LuP.gm.get("MOVEMENT");
                }
            } catch (Exception e) {
                e.getMessage();
            }

    }

    public void activateLeft() {
            try {
                if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE")))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 0))] == null) &&
                        (LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE")))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 1))] == null)) {
                    LuP.gc.translate(LuP.gm.get("MOVEMENT"), 0);
                    LuP.ATX -= LuP.gm.get("MOVEMENT");
                } else {
                    LuP.x += LuP.gm.get("MOVEMENT");
                }
            } catch (Exception e) {
                e.getMessage();
            }

    }

    public void activateUp() {
            try {
                if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 1))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE")))] == null) &&
                        (LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 0))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE")))] == null)) {
                    LuP.gc.translate(0, LuP.gm.get("MOVEMENT"));
                    LuP.ATY -= LuP.gm.get("MOVEMENT");
                } else {
                    LuP.y += LuP.gm.get("MOVEMENT");
                }
            } catch (Exception e) {
                e.getMessage();
            }

    }

    public void activateDown() {
            try {
                if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 1))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 1))] == null) &&
                        (LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 0))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 1))] == null)) {
                    LuP.gc.translate(0, -LuP.gm.get("MOVEMENT"));
                    LuP.ATY += LuP.gm.get("MOVEMENT");
                } else {
                    LuP.y -= LuP.gm.get("MOVEMENT");
                }
            } catch (Exception e) {
                e.getMessage();
            }

    }
}