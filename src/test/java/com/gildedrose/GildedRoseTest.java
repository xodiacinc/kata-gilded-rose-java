package com.gildedrose;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    static final String STANDARD = "Standard Item";
    static final String LEGENDARY = "Sulfuras, Hand of Ragnaros";
    static final String CHEESE = "Aged Brie";
    static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    static final String CONJURED = "Conjured Mana Cake";

    @Test
    @DisplayName("should return same result as golden master")
    void matchesGoldenMaster() throws Throwable {
        InputStream input = GildedRoseTest.class.getResourceAsStream("/golden-master.original");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String goldenMaster = reader.lines().reduce(null, (output, line) -> output == null ? line : output + '\n' + line);
        reader.close();
        assertEquals(goldenMaster, new GoldenMasterTextTests().run());
    }

    @Test
    @DisplayName("should age all items in the list")
    void agesAllItem() {
        GildedRose rose = create(new Item(STANDARD, 5, 10), new Item(STANDARD, 3, 15));
        rose.updateQuality();
        assertEquals(4, rose.items[0].sellIn);
        assertEquals(2, rose.items[1].sellIn);
    }

    @Nested
    @DisplayName("Standard Item")
    class StandardItem {
        @Test
        @DisplayName("should age")
        void ages() {
            GildedRose rose = create(new Item(STANDARD, 5, 10));
            rose.updateQuality();
            assertEquals(4, rose.items[0].sellIn);
        }

        @Test
        @DisplayName("should reduce quality with aging")
        void reducesQuality() {
            GildedRose rose = create(new Item(STANDARD, 5, 10));
            rose.updateQuality();
            assertEquals(9, rose.items[0].quality);
        }

        @Test
        @DisplayName("should reduce quality to minimum 0")
        void reducesQualityToMinimum_0() {
            GildedRose rose = create(new Item(STANDARD, 5, 0));
            rose.updateQuality();
            assertEquals(0, rose.items[0].quality);
        }

        @Test
        @DisplayName("should reduce quality twice as fast when expired")
        void reducesQualityTwiceAsFastWhenExpired() {
            GildedRose rose = create(new Item(STANDARD, 0, 10));
            rose.updateQuality();
            assertEquals(8, rose.items[0].quality);
        }
    }

    @Nested
    @DisplayName("Legendary Item")
    class LegendaryItem {
        @Test
        @DisplayName("should not age")
        void doesNotAge() {
            GildedRose rose = create(new Item(LEGENDARY, 5, 10));
            rose.updateQuality();
            assertEquals(5, rose.items[0].sellIn);
        }

        @Test
        @DisplayName("should not change quality")
        void doesNotChangeQuality() {
            GildedRose rose = create(new Item(LEGENDARY, 5, 10));
            rose.updateQuality();
            assertEquals(10, rose.items[0].quality);
        }
    }

    @Nested
    @DisplayName("Cheese")
    class Cheese {
        @Test
        @DisplayName("should age")
        void ages() {
            GildedRose rose = create(new Item(CHEESE, 5, 10));
            rose.updateQuality();
            assertEquals(4, rose.items[0].sellIn);
        }

        @Test
        @DisplayName("should increase quality with aging")
        void increasesQuality() {
            GildedRose rose = create(new Item(CHEESE, 5, 10));
            rose.updateQuality();
            assertEquals(11, rose.items[0].quality);
        }

        @Test
        @DisplayName("should increase quality twice as fast when expired")
        void increasesQualityTwiceAsFastWhenExpired() {
            GildedRose rose = create(new Item(CHEESE, 0, 10));
            rose.updateQuality();
            assertEquals(12, rose.items[0].quality);
        }

        @Test
        @DisplayName("should increase quality to maximum 50")
        void increasesQualityToMaximum_50() {
            GildedRose rose = create(new Item(CHEESE, 0, 50));
            rose.updateQuality();
            assertEquals(50, rose.items[0].quality);
        }
    }

    @Nested
    @DisplayName("Backstage Pass")
    class BackstagePass {
        @Test
        @DisplayName("should age")
        void ages() {
            GildedRose rose = create(new Item(BACKSTAGE, 5, 10));
            rose.updateQuality();
            assertEquals(4, rose.items[0].sellIn);
        }

        @Test
        @DisplayName("should increase quality with aging")
        void increasesQuality() {
            GildedRose rose = create(new Item(BACKSTAGE, 11, 10));
            rose.updateQuality();
            assertEquals(11, rose.items[0].quality);
        }

        @Test
        @DisplayName("should increase quality twice as fast when expiring in 10 days or less")
        void increasesQualityTwiceAsFastWhenNearingExpiry() {
            GildedRose rose = create(new Item(BACKSTAGE, 10, 10));
            rose.updateQuality();
            assertEquals(12, rose.items[0].quality);
        }

        @Test
        @DisplayName("should increase quality three times as fast when expiring in 5 days or less")
        void increasesQualityTreeTimesAsFastWhenNearlyExpired() {
            GildedRose rose = create(new Item(BACKSTAGE, 5, 10));
            rose.updateQuality();
            assertEquals(13, rose.items[0].quality);
        }

        @Test
        @DisplayName("should increase quality to maximum 50")
        void increasesQualityToMaximum_50() {
            GildedRose rose = create(new Item(BACKSTAGE, 5, 50));
            rose.updateQuality();
            assertEquals(50, rose.items[0].quality);
        }

        @Test
        @DisplayName("should set quality to 0 when expired")
        void resetsQualityWhenExpired() {
            GildedRose rose = create(new Item(BACKSTAGE, 0, 10));
            rose.updateQuality();
            assertEquals(0, rose.items[0].quality);
        }
    }

    @Disabled
    @Nested
    @DisplayName("Conjured Item")
    class ConjuredItem {
        @Test
        @DisplayName("should age")
        void ages() {
            GildedRose rose = create(new Item(CONJURED, 5, 10));
            rose.updateQuality();
            assertEquals(4, rose.items[0].sellIn);
        }

        @Test
        @DisplayName("should reduce quality twice as fast with aging")
        void reducesQualityTwiceAsFast() {
            GildedRose rose = create(new Item(CONJURED, 5, 10));
            rose.updateQuality();
            assertEquals(8, rose.items[0].quality);
        }

        @Test
        @DisplayName("should reduce quality to minimum 0")
        void reducesQualityToMinimum_0() {
            GildedRose rose = create(new Item(CONJURED, 5, 0));
            rose.updateQuality();
            assertEquals(0, rose.items[0].quality);
        }

        @Test
        @DisplayName("should reduce quality twice as fast when expired")
        void reducesQualityTwiceAsFastWhenExpired() {
            GildedRose rose = create(new Item(CONJURED, 0, 10));
            rose.updateQuality();
            assertEquals(6, rose.items[0].quality);
        }
    }

    private GildedRose create(Item... items) {
        return new GildedRose(items);
    }
}
