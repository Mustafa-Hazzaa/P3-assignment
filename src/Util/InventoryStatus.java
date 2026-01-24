package Util;

import java.awt.*;

public enum InventoryStatus {
    AVAILABLE("Available",new Color(60,150,55)),
    LOW ("Low",new Color(255,164,75)),
    OUT_OF_STOCK ("Out of stock",new Color(255,75,101));

    InventoryStatus(String text, Color color ) {
        this.text = text;
        this.color = color;
    }

    private final String text;
    private final Color color;

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }
    }

