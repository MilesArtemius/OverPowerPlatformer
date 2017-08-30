package greensun.platformer_engine;

public class interActivator {
    PlatformerUploader LuP;

    int offStep;

    public interActivator(PlatformerUploader luP) {
        this.LuP = luP;
        offStep = (int) (LuP.gm.get("BLOCK_SIZE") / 10);
    }

    public void activateRight() {
            try {
                if ((LuP.level.getBlock(((int) ((LuP.x - offStep) / LuP.gm.get("BLOCK_SIZE") + 1)), ((int) ((LuP.y - offStep) / LuP.gm.get("BLOCK_SIZE") + 1))) == null) &&
                        (LuP.level.getBlock(((int) ((LuP.x - offStep) / LuP.gm.get("BLOCK_SIZE") + 1)), ((int) ((LuP.y + offStep) / LuP.gm.get("BLOCK_SIZE") + 0))) == null)) {
                    if ((LuP.source.getWidth() + LuP.ATX <= LuP.level.Width * LuP.gm.get("BLOCK_SIZE")) && ((LuP.x - LuP.ATX) >= (LuP.source.getWidth() / 2) - (LuP.gm.get("BLOCK_SIZE") / 2))) {
                        LuP.structureGC.translate(-LuP.gm.get("MOVEMENT"), 0);
                        LuP.sourceGC.translate(-LuP.gm.get("MOVEMENT"), 0);
                        LuP.ATX += LuP.gm.get("MOVEMENT");
                    } else {
                        if (LuP.x > (LuP.level.Width - 1) * LuP.gm.get("BLOCK_SIZE")) {
                            LuP.x = (LuP.level.Width - 1) * LuP.gm.get("BLOCK_SIZE");
                        }
                    }
                } else {
                    System.out.println("RIGHT");
                    LuP.x = Math.round(LuP.x / LuP.gm.get("BLOCK_SIZE")) * LuP.gm.get("BLOCK_SIZE") - 1;
                    LuP.x += offStep;
                }
            } catch (Exception e) {
                if (LuP.x > (LuP.level.Width - 1) * LuP.gm.get("BLOCK_SIZE")) {
                    LuP.x = (LuP.level.Width - 1) * LuP.gm.get("BLOCK_SIZE");
                }
                e.getMessage();
            }

    }

    public void activateLeft() {
            try {
                if ((LuP.level.getBlock(((int) ((LuP.x + offStep) / LuP.gm.get("BLOCK_SIZE"))), ((int) ((LuP.y + offStep) / LuP.gm.get("BLOCK_SIZE") + 0))) == null) &&
                        (LuP.level.getBlock(((int) ((LuP.x + offStep) / LuP.gm.get("BLOCK_SIZE"))), ((int) ((LuP.y - offStep) / LuP.gm.get("BLOCK_SIZE") + 1))) == null)) {
                    if ((LuP.ATX >= 0) && ((LuP.x - LuP.ATX) <= (LuP.source.getWidth() / 2) - (LuP.gm.get("BLOCK_SIZE") / 2))) {
                        LuP.structureGC.translate(LuP.gm.get("MOVEMENT"), 0);
                        LuP.sourceGC.translate(LuP.gm.get("MOVEMENT"), 0);
                        LuP.ATX -= LuP.gm.get("MOVEMENT");
                    } else {
                        if (LuP.x < 0) {
                            LuP.x = 0;
                        }
                    }
                } else {
                    System.out.println("LEFT");
                    LuP.x = Math.round(LuP.x / LuP.gm.get("BLOCK_SIZE")) * LuP.gm.get("BLOCK_SIZE") + 1;
                    LuP.x -= offStep;
                }
            } catch (Exception e) {
                e.getMessage();
            }

    }

    public void activateUp() {
            try {
                if ((LuP.level.getBlock(((int) ((LuP.x - offStep) / LuP.gm.get("BLOCK_SIZE") + 1)), ((int) ((LuP.y - offStep) / LuP.gm.get("BLOCK_SIZE")))) == null) &&
                        (LuP.level.getBlock(((int) ((LuP.x + offStep) / LuP.gm.get("BLOCK_SIZE") + 0)), ((int) ((LuP.y - offStep) / LuP.gm.get("BLOCK_SIZE")))) == null)) {
                    if ((LuP.ATY >= 0) && ((LuP.y - LuP.ATY) <= (LuP.source.getHeight() / 2) - (LuP.gm.get("BLOCK_SIZE") / 2))) {
                        LuP.structureGC.translate(0, LuP.gm.get("MOVEMENT"));
                        LuP.sourceGC.translate(0, LuP.gm.get("MOVEMENT"));
                        LuP.ATY -= LuP.gm.get("MOVEMENT");
                    } else {
                        if (LuP.y < 0) {
                            LuP.y = 0;
                            LuP.t = 2 * LuP.gm.get("SPEED") / LuP.gm.get("GRAVITY");
                        }
                    }
                } else {
                    LuP.t = 2 * LuP.gm.get("SPEED") / LuP.gm.get("GRAVITY");
                    if (LuP.gm.get("PLATFORMER") == 1) {
                        LuP.jumper = 0;
                        LuP.y = Math.ceil(LuP.y / LuP.gm.get("BLOCK_SIZE")) * LuP.gm.get("BLOCK_SIZE") + 1;
                    } else {
                        LuP.y = Math.round(LuP.y / LuP.gm.get("BLOCK_SIZE")) * LuP.gm.get("BLOCK_SIZE") + 1;
                    }
                    System.out.println("UP");
                    LuP.y -= offStep;
                }
            } catch (Exception e) {
                e.getMessage();
            }
    }

    public void activateDown() {
            try {
                if ((LuP.level.getBlock(((int) ((LuP.x - offStep) / LuP.gm.get("BLOCK_SIZE") + 1)), ((int) ((LuP.y - offStep) / LuP.gm.get("BLOCK_SIZE") + 1))) == null) &&
                        (LuP.level.getBlock(((int) ((LuP.x + offStep) / LuP.gm.get("BLOCK_SIZE") + 0)), ((int) ((LuP.y - offStep) / LuP.gm.get("BLOCK_SIZE") + 1))) == null)) {
                    if ((LuP.source.getHeight() + LuP.ATY <= LuP.level.Height * LuP.gm.get("BLOCK_SIZE")) && ((LuP.y - LuP.ATY) >= (LuP.source.getHeight() / 2) - (LuP.gm.get("BLOCK_SIZE") / 2))) {
                        LuP.structureGC.translate(0, -LuP.gm.get("MOVEMENT"));
                        LuP.sourceGC.translate(0, -LuP.gm.get("MOVEMENT"));
                        LuP.ATY += LuP.gm.get("MOVEMENT");
                    } else {
                        if (LuP.y > (LuP.level.Height - 1) * LuP.gm.get("BLOCK_SIZE")) {
                            LuP.y = (LuP.level.Height - 1) * LuP.gm.get("BLOCK_SIZE");
                        }
                }
                } else {
                    LuP.t = 0;
                    if (LuP.gm.get("PLATFORMER") == 1) {
                        LuP.jumper = 0;
                        LuP.y = Math.floor(LuP.y / LuP.gm.get("BLOCK_SIZE")) * LuP.gm.get("BLOCK_SIZE") - 1;
                    } else {
                        LuP.y = Math.round(LuP.y / LuP.gm.get("BLOCK_SIZE")) * LuP.gm.get("BLOCK_SIZE") - 1;
                    }
                    System.out.println("DOWN");
                    LuP.y += offStep;
                }
            } catch (Exception e) {
                if (LuP.y > (LuP.level.Height - 1) * LuP.gm.get("BLOCK_SIZE")) {
                    LuP.y = (LuP.level.Height - 1) * LuP.gm.get("BLOCK_SIZE");
                }
                e.getMessage();
            }
    }
}