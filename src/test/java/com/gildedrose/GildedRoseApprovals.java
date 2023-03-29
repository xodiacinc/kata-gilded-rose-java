package com.gildedrose;

import org.approvaltests.*;
import org.approvaltests.strings.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class GildedRoseApprovals {
    @Test
    void goldenMaster() {
        Printable<GildedRose> printableApp = new Printable<>(getReferenceGildedRose(), gr -> Arrays.toString(gr.items));
        List<String> dailySnapshots = new ArrayList<>();

        int days = 20;

        for (int i = 0; i < days; i++) {
            printableApp.get().updateQuality();
            dailySnapshots.add(printableApp.toString());
        }

        Approvals.verifyAll("Day", dailySnapshots.toArray(new String[0]));
    }

    public static GildedRose getReferenceGildedRose() {
        Item[] items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20), //
                new Item("Aged Brie", 2, 0), //
                new Item("Elixir of the Mongoose", 5, 7), //
                new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                // this conjured item does not work properly yet
                new Item("Conjured Mana Cake", 3, 6) };

        return new GildedRose(items);
    }
}
