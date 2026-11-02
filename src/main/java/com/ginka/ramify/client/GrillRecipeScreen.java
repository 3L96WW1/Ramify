package com.ginka.ramify.client;

import com.ginka.ramify.GrillRecipes;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

/** 烧烤架配方列表，滚轮翻页喵 */
public class GrillRecipeScreen extends Screen {
    private final Screen parent;
    private static final int ROW_HEIGHT = 20;
    private static final int VISIBLE_ROWS = 9;
    private int scroll;

    public GrillRecipeScreen(Screen parent) {
        super(Text.literal("烧烤架配方"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("返回"),
                button -> this.close()).dimensions(this.width / 2 - 30, this.height - 28, 60, 18).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        int left = this.width / 2 - 180;
        int top = this.height / 2 - 120;
        context.fill(left, top, left + 360, top + 250, 0xF01B1B1B);
        context.drawBorder(left, top, 360, 250, 0xFF777777);
        context.drawText(this.textRenderer, this.title, left + 12, top + 8, 0xFFFFFF, false);

        List<GrillRecipes.Display> entries = GrillRecipes.displayEntries();
        int start = Math.max(0, Math.min(scroll, entries.size() - VISIBLE_ROWS));
        for (int i = 0; i < VISIBLE_ROWS && start + i < entries.size(); i++) {
            GrillRecipes.Display entry = entries.get(start + i);
            int y = top + 30 + i * ROW_HEIGHT;
            ItemStack stack = entry.output();
            context.drawItem(stack, left + 14, y + 2);
            context.drawText(this.textRenderer, stack.getName(), left + 38, y + 2, 0xFFFFFF, false);
            context.drawText(this.textRenderer, entry.ingredients(), left + 150, y + 2, 0xAAAAAA, false);
        }
        // 滚动条
        int total = entries.size();
        int barHeight = 220;
        int thumb = Math.max(20, barHeight * VISIBLE_ROWS / total);
        int thumbY = top + 24 + (barHeight - thumb) * start / Math.max(1, total - VISIBLE_ROWS);
        context.fill(left + 350, top + 24, left + 354, top + 24 + barHeight, 0xFF333333);
        context.fill(left + 350, thumbY, left + 354, thumbY + thumb, 0xFFAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
        List<GrillRecipes.Display> entries = GrillRecipes.displayEntries();
        int max = Math.max(0, entries.size() - VISIBLE_ROWS);
        scroll = Math.max(0, Math.min(max, scroll - (int) Math.signum(vertical)));
        return true;
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
