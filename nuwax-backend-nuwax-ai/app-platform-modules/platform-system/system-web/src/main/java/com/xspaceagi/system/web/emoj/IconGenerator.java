package com.xspaceagi.system.web.emoj;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class IconGenerator {

    private static final Color[] rgbs = new Color[]{
            new Color(117, 189, 108),
            new Color(77, 87, 224),
            new Color(63, 118, 247),
            new Color(238, 193, 79),
            new Color(160, 109, 237)
    };

    public enum IconStyle {
        CLASSIC, MODERN, MINIMAL, COLORFUL
    }

    public enum IconFormat {
        PNG, JPG, SVG, ICO
    }

    private Map<String, Color> colorSchemes;

    public IconGenerator() {
        initColorSchemes();
    }

    private void initColorSchemes() {
        colorSchemes = new HashMap<>();
        colorSchemes.put("java_blue", new Color(0, 115, 150));
        colorSchemes.put("java_orange", new Color(255, 140, 0));
        colorSchemes.put("dark", new Color(45, 45, 45));
        colorSchemes.put("light", new Color(245, 245, 245));
    }

    public BufferedImage generateIcon(String text, int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        // 设置渲染质量
        setupGraphics(g2d);
        drawClassicIcon(g2d, text, size);
        g2d.dispose();
        return image;
    }

    public static String getFirstNonSpecialChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                return String.valueOf(c).toUpperCase(); // 将字符转为字符串返回
            }
        }
        return "A"; // 如果没有找到符合条件的字符，返回空字符串
    }

    private void setupGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private void drawClassicIcon(Graphics2D g2d, String text, int size) {
        try {
            String decode = URLDecoder.decode(text, "UTF-8");
            text = getFirstNonSpecialChar(decode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        int index = Math.abs(text.hashCode()) % rgbs.length;
        Color bgColor = rgbs[index];
        RadialGradientPaint radialGradient = new RadialGradientPaint(
                new Point2D.Double(size * 0.3, size * 0.3),  // 中心点偏左上
                size * 0.6f,  // 半径
                new float[]{0.0f, 0.6f, 1.0f},
                new Color[]{
                        new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 250),  // 中心较亮
                        new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 200),   // 中等亮度
                        new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 150)    // 边缘透明
                },
                MultipleGradientPaint.CycleMethod.NO_CYCLE
        );
        g2d.setPaint(radialGradient);
        g2d.fillRoundRect(0, 0, size, size, size / 8, size / 8);

        // 绘制文字
        g2d.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, size * 2 / 5);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int x = (size - fm.stringWidth(text)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();

        g2d.drawString(text, x, y);
    }
}