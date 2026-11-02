package com.ginka.ramify.client;

import com.ginka.ramify.GrillScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class GrillScreen extends HandledScreen<GrillScreenHandler> {
    private static final int BG = 0xFF1E1E1E;
    private static final int SLOT = 0xFF3A3A3A;
    private static final int BORDER = 0xFF777777;
    private static final int ORANGE = 0xFFE07830;

    public GrillScreen(GrillScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 256;
        this.backgroundHeight = 230;
        this.playerInventoryTitleY = 137;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;
        context.fill(x, y, x + this.backgroundWidth, y + this.backgroundHeight, BG);
        context.fill(x + 1, y + 1, x + this.backgroundWidth - 1, y + 20, 0xFF2A2A2A);
        // 十字原料槽背景
        drawSlot(context, x + 88, y + 16); drawSlot(context, x + 70, y + 34);
        drawSlot(context, x + 106, y + 34); drawSlot(context, x + 88, y + 52);
        // 燃料槽
        drawSlot(context, x + 70, y + 78); drawSlot(context, x + 106, y + 78);
        // 8 个成品槽
        for (int i = 0; i < 8; i++) {
            int sy = y + 16 + i * 17;
            context.fill(x + 149, sy - 1, x + 249, sy + 16, 0xFF303030);
            context.drawBorder(x + 149, sy - 1, 100, 17, BORDER);
        }
        // 进度条 / 燃料条
        int p = handler.getProgress() * 56 / handler.getMaxProgress();
        context.fill(x + 64, y + 98, x + 120, y + 104, 0xFF444444);
        context.fill(x + 64, y + 98, x + 64 + p, y + 104, ORANGE);
        int b = handler.getBurnTime() * 56 / handler.getFuelTotal();
        context.fill(x + 64, y + 88, x + 120, y + 92, 0xFF444444);
        context.fill(x + 64, y + 88, x + 64 + b, y + 92, 0xFFFFB020);
        // 玩家背包背景
        context.fill(x + 7, y + 145, x + 249, y + 225, 0xFF242424);
    }

    private void drawSlot(DrawContext context, int x, int y) {
        context.fill(x - 1, y - 1, x + 17, y + 17, SLOT);
        context.drawBorder(x - 1, y - 1, 18, 18, BORDER);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, 8, 6, 0xFFFFFF, false);
        context.drawText(this.textRenderer, "原料", 60, 70, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "燃料", 60, 84, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "成品列表", 150, 4, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "进度", 25, 97, 0xAAAAAA, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, 8, 137, 0xAAAAAA, false);
        if (handler.getProgress() == 0) {
            context.drawText(this.textRenderer, "配方待完善", 160, 100, 0x777777, false);
        }
    }

    public static void register() {
        HandledScreens.register(GrillScreenHandler.TYPE, GrillScreen::new);
    }
}
