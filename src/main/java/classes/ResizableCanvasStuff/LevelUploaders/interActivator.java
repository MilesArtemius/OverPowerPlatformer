package classes.ResizableCanvasStuff.LevelUploaders;

public class interActivator {
    LevelUploader LuP;

    boolean [][] flags;

    public interActivator(LevelUploader luP) {
        this.LuP = luP;
    }

    private void interactYUp(String modifier) {
        LuP.y = ((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 1)) * LuP.gm.get("BLOCK_SIZE") + 1;
        LuP.t = 2 * LuP.gm.get("SPEED") / LuP.gm.get("GRAVITY");
        if (LuP.gm.get("PLATFORMER") != 1) {
            LuP.gc.translate(0, -LuP.gm.get("MOVEMENT"));
            LuP.ATY += LuP.gm.get("MOVEMENT");
        }

        System.out.println(modifier);

        safeReturn();
    }
    private void interactYDown(String modifier) {
        LuP.t = 1;
        LuP.y = ((int) (LuP.y / LuP.gm.get("BLOCK_SIZE"))) * LuP.gm.get("BLOCK_SIZE") - 1;
        if (LuP.gm.get("PLATFORMER") != 1) {
            LuP.gc.translate(0, LuP.gm.get("MOVEMENT"));
            LuP.ATY -= LuP.gm.get("MOVEMENT");
        }

        System.out.println(modifier);

        safeReturn();
    }
    private void interactXLeft(String modifier) {
        LuP.x = ((int) (LuP.x / LuP.gm.get("BLOCK_SIZE")) + 1) * LuP.gm.get("BLOCK_SIZE") + 1;
        LuP.gc.translate(-LuP.gm.get("MOVEMENT") - ((LuP.gm.get("PLATFORMER") == 1)?(1):(0)), 0);
        LuP.ATX += LuP.gm.get("MOVEMENT");

        System.out.println(modifier);

        safeReturn();
    }
    private void interactXRight(String modifier) {
        LuP.x = ((int) (LuP.x / LuP.gm.get("BLOCK_SIZE"))) * LuP.gm.get("BLOCK_SIZE") - 1;
        LuP.gc.translate(LuP.gm.get("MOVEMENT") + ((LuP.gm.get("PLATFORMER") == 1)?(1):(0)), 0);
        LuP.ATX -= LuP.gm.get("MOVEMENT");

        System.out.println(modifier);

        safeReturn();
    }

    public void activate() {

        flags = new boolean [6][2];

        for (int i = 0; i < 2; i++) {
            try {
                if (LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + i))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + 1))] != null) {
                    if (i == 0) {
                        flags[0][0] = true;
                    } else {
                        flags[0][1] = true;
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        if ((flags[0][0]) && (flags[0][1])) {
            interactYDown("Y DOWN");
            return;
        }

        for (int i = 0; i < 2; i++) {
            try {
                if (LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE")))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + i))] != null) {
                    if (i == 0) {
                        flags[1][0] = true;
                    } else {
                        flags[1][1] = true;
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        if ((flags[1][0]) && (flags[1][1])) {
            interactXLeft("X LEFT");
            return;
        }

        for (int i = 0; i < 2; i++) {
            try {
                if (LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + 1))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE") + i))] != null) {
                    if (i == 0) {
                        flags[3][0] = true;
                    } else {
                        flags[3][1] = true;
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        if ((flags[3][0]) && (flags[3][1])) {
            interactXRight("X RIGHT");
            return;
        }

        for (int i = 0; i < 2; i++) {
            try {
                if (LuP.level.level[((int) (LuP.x / LuP.gm.get("BLOCK_SIZE") + i))][((int) (LuP.y / LuP.gm.get("BLOCK_SIZE")))] != null) {
                    if (i == 0) {
                        flags[2][0] = true;
                    } else {
                        flags[2][1] = true;
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        if ((flags[2][0]) && (flags[2][1])) {
            interactYUp("Y UP");
            return;
        }

        // corner interaction;

        System.out.println(LuP.prev_x);
        System.out.println(LuP.prev_y);

        if ((flags[0][1]) && (flags[3][1])) {
            flags[4][0] = true;
            if (Math.abs(LuP.prev_x - LuP.x) > Math.abs(LuP.y - LuP.prev_y)) {
                interactXRight("Upper X RIGHT");
            } else {
                interactYDown("Right Y DOWN");
            }
        } if ((flags[0][0]) && (flags[1][1])) {
            flags[4][1] = true;
            if (Math.abs(LuP.prev_x - LuP.x) > Math.abs(LuP.y - LuP.prev_y)) {
                interactXLeft("Upper X LEFT");
            } else {
                interactYDown("Left Y DOWN");
            }
        } if ((flags[2][1]) && (flags[3][0])) {
            flags[5][0] = true;
            if (Math.abs(LuP.prev_x - LuP.x) >= Math.abs(LuP.y - LuP.prev_y)) {
                interactXRight("Lower X RIGHT");
            } else {
                interactYUp("Right Y UP");
            }
        } if ((flags[2][0]) && (flags[1][0])) {
            flags[5][1] = true;
            if (Math.abs(LuP.prev_x - LuP.x) >= Math.abs(LuP.y - LuP.prev_y)) {
                interactXLeft("Lower X LEFT");
            } else {
                interactYUp("Left Y UP");
            }
        } else {
            safeReturn();
        }
    }

    private void safeReturn() {
        LuP.prev_x = LuP.x;
        LuP.prev_y = LuP.y;
    }
}