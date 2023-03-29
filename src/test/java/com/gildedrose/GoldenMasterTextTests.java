package com.gildedrose;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GoldenMasterTextTests {
    public static void main(String[] args) {
        System.out.println(new GoldenMasterTextTests().run());
    }

    public String run() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output);
        GildedRose app = GildedRoseApprovals.getReferenceGildedRose();
        int days = 20;

        for (int i = 0; i < days; i++) {
            out.println("-------- day " + i + " --------");
            out.println("name, sellIn, quality");
            for (Item item : app.items) {
                out.println(item);
            }
            out.println();
            app.updateQuality();
        }
        out.close();

        return output.toString();
    }
}
