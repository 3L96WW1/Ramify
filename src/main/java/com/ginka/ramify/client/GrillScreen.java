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
        this.backgroundHeight = 260;
        this.playerInventoryTitleY = 160;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;
        context.fill(x, y, x + this.backgroundWidth, y + this.backgroundHeight, BG);
        context.fill(x + 1, y + 1, x + this.backgroundWidth - 1, y + 18, 0xFF2A2A2A);

        // 十字原料槽：横向、纵向间距加大，槽框略扁
        drawSlot(context, x + 80, y + 18);
        drawSlot(context, x + 58, y + 40);
        drawSlot(context, x + 102, y + 40);
        drawSlot(context, x + 80, y + 62);

        // 燃料槽下移
        drawSlot(context, x + 58, y + 96);
        drawSlot(context, x + 102, y + 96);

        // 右侧 8 行成品
        for (int i = 0; i < 8; i++) {
            int sy = y + 18 + i * 16;
            context.fill(x + 149, sy - 1, x + 249, sy + 15, 0xFF303030);
            context.drawBorder(x + 149, sy - 1, 100, 16, BORDER);
        }

        // 燃料条 / 进度条下移
        int b = handler.getBurnTime() * 56 / handler.getFuelTotal();
        context.fill(x + 64, y + 114, x + 120, y + 118, 0xFF444444);
        context.fill(x + 64, y + 114, x + 64 + b, y + 118, 0xFFFFB020);
        int p = handler.getProgress() * 56 / handler.getMaxProgress();
        context.fill(x + 64, y + 124, x + 120, y + 130, 0xFF444444);
        context.fill(x + 64, y + 124, x + 64 + p, y + 130, ORANGE);

        // 玩家背包
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                drawSlot(context, x + 8 + col * 18, y + 172 + row * 18);
            }
        }
        for (int col = 0; col < 9; col++) {
            drawSlot(context, x + 8 + col * 18, y + 230);
        }
    }

    private void drawSlot(DrawContext context, int x, int y) {
        // 18×16，比原版槽位略扁
        context.fill(x - 1, y - 1, x + 17, y + 15, SLOT);
        context.drawBorder(x - 1, y - 1, 18, 16, BORDER);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, 8, 6, 0xFFFFFF, false);
        context.drawText(this.textRenderer, "原料", 80, 4, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "燃料", 26, 92, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "成品列表", 150, 4, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "进度", 30, 124, 0xAAAAAA, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, 8, 160, 0xAAAAAA, false);
    }

    public static void register() {
        HandledScreens.register(GrillScreenHandler.TYPE, GrillScreen::new);
    }
}
