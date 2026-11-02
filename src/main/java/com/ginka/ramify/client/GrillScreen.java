package com.ginka.ramify.client;

import com.ginka.ramify.GrillScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class GrillScreen extends HandledScreen<GrillScreenHandler> {
    private static final int BG = 0xFF1B1B1B;
    private static final int PANEL = 0xFF242424;
    private static final int SLOT = 0xFF3A3A3A;
    private static final int BORDER = 0xFF6E6E6E;
    private static final int ORANGE = 0xFFE07830;

    public GrillScreen(GrillScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 256;
        this.backgroundHeight = 276;
        this.playerInventoryTitleY = 174;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;
        context.fill(x, y, x + this.backgroundWidth, y + this.backgroundHeight, BG);
        // 顶栏
        context.fill(x + 1, y + 1, x + this.backgroundWidth - 1, y + 20, 0xFF2A2A2A);
        context.drawBorder(x, y, this.backgroundWidth, this.backgroundHeight, 0xFF555555);

        // 左：原料/燃料区
        context.fill(x + 6, y + 24, x + 142, y + 160, PANEL);
        context.drawBorder(x + 6, y + 24, 136, 136, BORDER);
        // 右：成品区
        context.fill(x + 146, y + 24, x + 250, y + 168, PANEL);
        context.drawBorder(x + 146, y + 24, 104, 144, BORDER);
        // 底部物品栏
        context.fill(x + 6, y + 174, x + 250, y + 270, PANEL);
        context.drawBorder(x + 6, y + 174, 244, 96, BORDER);

        // 十字原料槽
        drawSlot(context, x + 80, y + 38, 20);
        drawSlot(context, x + 58, y + 60, 20);
        drawSlot(context, x + 102, y + 60, 20);
        drawSlot(context, x + 80, y + 82, 20);

        // 燃料槽
        drawSlot(context, x + 58, y + 112, 20);
        drawSlot(context, x + 102, y + 112, 20);

        // 8 行成品槽
        for (int i = 0; i < 8; i++) {
            int sy = y + 38 + i * 16;
            context.fill(x + 153, sy - 1, x + 247, sy + 15, 0xFF303030);
            context.drawBorder(x + 153, sy - 1, 94, 16, BORDER);
        }

        // 燃料条 / 进度条
        int b = handler.getBurnTime() * 56 / handler.getFuelTotal();
        context.fill(x + 58, y + 132, x + 114, y + 136, 0xFF444444);
        context.fill(x + 58, y + 132, x + 58 + b, y + 136, 0xFFFFB020);
        int p = handler.getProgress() * 56 / handler.getMaxProgress();
        context.fill(x + 58, y + 144, x + 114, y + 150, 0xFF444444);
        context.fill(x + 58, y + 144, x + 58 + p, y + 150, ORANGE);

        // 玩家背包
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                drawSlot(context, x + 8 + col * 18, y + 186 + row * 18);
            }
        }
        for (int col = 0; col < 9; col++) {
            drawSlot(context, x + 8 + col * 18, y + 244);
        }
    }

    private void drawSlot(DrawContext context, int x, int y) {
        drawSlot(context, x, y, 18);
    }

    private void drawSlot(DrawContext context, int x, int y, int width) {
        int left = x - (width - 16) / 2;
        context.fill(left, y - 1, left + width, y + 15, SLOT);
        context.drawBorder(left, y - 1, width, 16, BORDER);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, 10, 6, 0xFFFFFF, false);
        context.drawText(this.textRenderer, "原料", 16, 30, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "燃料", 16, 108, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "进度", 16, 144, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "成品列表", 154, 30, 0xAAAAAA, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, 10, 174, 0xAAAAAA, false);
    }

    public static void register() {
        HandledScreens.register(GrillScreenHandler.TYPE, GrillScreen::new);
    }
}
