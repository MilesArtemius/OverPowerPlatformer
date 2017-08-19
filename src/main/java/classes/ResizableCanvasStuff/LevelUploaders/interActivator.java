package classes.ResizableCanvasStuff.LevelUploaders;

public class interActivator {
    LevelUploader LuP;

    public interActivator(LevelUploader luP) {
        this.LuP = luP;
    }

    public void activate() {
        for (int i = 0; i < 2; i++) {
            try {
                if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + i))][((int) (LuP.y /LuP. gm.get("BLOCK_SIZE") + 1))] != null) && (LuP.prev_y < LuP.y)) {
                    LuP.MOVEMENTER = 0;
                    LuP.t = 1;
                    LuP.y = ((int) (LuP.y / LuP.gm.get("BLOCK_SIZE"))) * LuP.gm.get("BLOCK_SIZE") - 1;
                    if (LuP.gm.get("PLATFORMER") != 1) {
                        LuP.gc.translate(0, LuP.gm.get("MOVEMENT"));
                        LuP.ATY -= LuP.gm.get("MOVEMENT");
                    }

                    System.out.println("Y DOWN");
                    return;
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        for (int i = 0; i < 2; i++) {
            try {
                if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE")))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + i))] != null) && (Math.abs(LuP.x - LuP.prev_x) > Math.abs(LuP.y - LuP.prev_y))) {
                    if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 1))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + i))] == null)) {
                        LuP.MOVEMENTER2 = 0;
                        LuP.x = ((int) (LuP.x / LuP.gm.get("BLOCK_SIZE")) + 1) * LuP.gm.get("BLOCK_SIZE") + 1;
                        LuP.gc.translate(-LuP.gm.get("MOVEMENT") - ((LuP.gm.get("PLATFORMER") == 1)?(1):(0)), 0);
                        LuP.ATX += LuP.gm.get("MOVEMENT");

                        System.out.println("X LEFT");
                        return;
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        for (int i = 0; i < 2; i++) {
            try { //
                if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 1))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + i))] != null) && (Math.abs(LuP.x - LuP.prev_x) > Math.abs(LuP.y - LuP.prev_y))) {
                    if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE")))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + i))] == null)) {
                        LuP.MOVEMENTER2 = 0;
                        LuP.x = ((int) (LuP.x / LuP.gm.get("BLOCK_SIZE"))) * LuP.gm.get("BLOCK_SIZE") - 1;
                        LuP.gc.translate(LuP.gm.get("MOVEMENT") + ((LuP.gm.get("PLATFORMER") == 1)?(1):(0)), 0);
                        LuP.ATX -= LuP.gm.get("MOVEMENT");

                        System.out.println("X RIGHT");
                        return;
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        for (int i = 0; i < 2; i++) {
            try {
                if ((LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + i))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE")))] != null) && (LuP.prev_y >= LuP.y)) {
                    LuP.MOVEMENTER = ((LuP.gm.get("PLATFORMER") != 1)?(0):(LuP.MOVEMENTER));
                    LuP.y = ((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 1)) * LuP.gm.get("BLOCK_SIZE") + 1;
                    LuP.t = 2 * LuP.gm.get("SPEED") / LuP.gm.get("GRAVITY");
                    if (LuP.gm.get("PLATFORMER") != 1) {
                        LuP.gc.translate(0, -LuP.gm.get("MOVEMENT"));
                        LuP.ATY += LuP.gm.get("MOVEMENT");
                    }

                    System.out.println("Y UP");
                    return;
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        LuP.prev_x = LuP.x;
        LuP.prev_y = LuP.y;
    }
}
