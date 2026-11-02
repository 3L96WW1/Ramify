package com.ginka.ramify.client;

import com.ginka.ramify.GrillScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.widget.ButtonWidget;
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
        this.backgroundHeight = 288;
        this.playerInventoryTitleY = 178;
    }

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(ButtonWidget.builder(Text.literal("配方"),
                button -> this.client.setScreen(new GrillRecipeScreen(this)))
                .dimensions(this.x + 204, this.y + 2, 44, 16).build());
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
        context.fill(x + 6, y + 24, x + 142, y + 172, PANEL);
        context.drawBorder(x + 6, y + 24, 136, 148, BORDER);
        // 右：成品区
        context.fill(x + 146, y + 24, x + 250, y + 168, PANEL);
        context.drawBorder(x + 146, y + 24, 104, 144, BORDER);
        // 底部物品栏
        context.fill(x + 6, y + 178, x + 250, y + 282, PANEL);
        context.drawBorder(x + 6, y + 178, 244, 104, BORDER);

        // 十字原料槽
        drawSlot(context, x + 80, y + 36, 22);
        drawSlot(context, x + 56, y + 60, 22);
        drawSlot(context, x + 104, y + 60, 22);
        drawSlot(context, x + 80, y + 84, 22);

        // 燃料槽
        drawSlot(context, x + 56, y + 120, 22);
        drawSlot(context, x + 104, y + 120, 22);

        // 8 行成品槽
        for (int i = 0; i < 8; i++) {
            int sy = y + 38 + i * 16;
            context.fill(x + 153, sy - 1, x + 247, sy + 15, 0xFF303030);
            context.drawBorder(x + 153, sy - 1, 94, 16, BORDER);
        }

        // 燃料条 / 进度条
        int b = handler.getBurnTime() * 56 / handler.getFuelTotal();
        context.fill(x + 56, y + 142, x + 114, y + 146, 0xFF444444);
        context.fill(x + 56, y + 142, x + 56 + b, y + 146, 0xFFFFB020);
        int p = handler.getProgress() * 56 / handler.getMaxProgress();
        context.fill(x + 56, y + 154, x + 114, y + 160, 0xFF444444);
        context.fill(x + 56, y + 154, x + 56 + p, y + 160, ORANGE);

        // 玩家背包
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                drawSlot(context, x + 8 + col * 18, y + 190 + row * 18);
            }
        }
        for (int col = 0; col < 9; col++) {
            drawSlot(context, x + 8 + col * 18, y + 248);
        }

        // 右下角工作台 3×3 + 产出
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                drawSlot(context, x + 182 + col * 18, y + 194 + row * 18);
            }
        }
        drawSlot(context, x + 238, y + 212);
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
        context.drawText(this.textRenderer, "原料", 16, 26, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "燃料", 16, 108, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "进度", 16, 153, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "成品列表", 154, 26, 0xAAAAAA, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, 10, 178, 0xAAAAAA, false);
        context.drawText(this.textRenderer, "工作台", 174, 178, 0xAAAAAA, false);
        // 成品名称
        for (int i = 0; i < 8; i++) {
            var stack = handler.getOutputStack(i);
            if (!stack.isEmpty()) {
                context.drawText(this.textRenderer, stack.getName(), 174, 21 + i * 16, 0xFFFFFF, false);
            }
        }
    }

    public static void register() {
        HandledScreens.register(GrillScreenHandler.TYPE, GrillScreen::new);
    }
}
