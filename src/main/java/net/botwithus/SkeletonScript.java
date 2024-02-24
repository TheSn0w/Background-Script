package net.botwithus;

import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.actionbar.ActionBar;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.GroundItemQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.queries.results.EntityResultSet;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.characters.player.Player;
import net.botwithus.rs3.game.scene.entities.item.GroundItem;
import net.botwithus.rs3.game.scene.entities.object.SceneObject;
import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.game.vars.VarManager;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.util.RandomGenerator;

import java.util.Random;

import static net.botwithus.rs3.game.Client.getLocalPlayer;
import static net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer.LOCAL_PLAYER;

public class SkeletonScript extends LoopingScript {
    private int prayerPointsThreshold = 1000;
    private int healthThreshold = 50;
    boolean useoverload;
    boolean useprayer;
    boolean usedarkness;
    boolean useaggression;
    boolean usedivination;
    boolean usecooking;
    boolean UseSoulSplit;
    boolean useHunter;
    boolean quickprayer;
    boolean eatFood;
    private int startingAttackLevel;
    private int startingStrengthLevel;
    private int startingDefenseLevel;
    private int startingRangedLevel;
    private int startingPrayerLevel;
    private int startingMagicLevel;
    private int startingRunecraftingLevel;
    private int startingConstructionLevel;
    private int startingDungeoneeringLevel;
    private int startingArchaeologyLevel;
    private int startingConstitutionLevel;
    private int startingAgilityLevel;
    private int startingHerbloreLevel;
    private int startingThievingLevel;
    private int startingCraftingLevel;
    private int startingFletchingLevel;
    private int startingSlayerLevel;
    private int startingHunterLevel;
    private int startingDivinationLevel;
    private int startingNecromancyLevel;
    private int startingMiningLevel;
    private int startingSmithingLevel;
    private int startingFishingLevel;
    private int startingCookingLevel;
    private int startingFiremakingLevel;
    private int startingWoodcuttingLevel;
    private int startingFarmingLevel;
    private int startingSummoningLevel;
    private int startingInventionLevel;
    public int getStartingAttackLevel() {
        return startingAttackLevel;
    }

    public int getStartingStrengthLevel() {
        return startingStrengthLevel;
    }

    public int getStartingDefenseLevel() {
        return startingDefenseLevel;
    }

    public int getStartingRangedLevel() {
        return startingRangedLevel;
    }

    public int getStartingPrayerLevel() {
        return startingPrayerLevel;
    }

    public int getStartingMagicLevel() {
        return startingMagicLevel;
    }

    public int getStartingRunecraftingLevel() {
        return startingRunecraftingLevel;
    }

    public int getStartingConstructionLevel() {
        return startingConstructionLevel;
    }

    public int getStartingDungeoneeringLevel() {
        return startingDungeoneeringLevel;
    }

    public int getStartingArchaeologyLevel() {
        return startingArchaeologyLevel;
    }

    public int getStartingConstitutionLevel() {
        return startingConstitutionLevel;
    }

    public int getStartingAgilityLevel() {
        return startingAgilityLevel;
    }

    public int getStartingHerbloreLevel() {
        return startingHerbloreLevel;
    }

    public int getStartingThievingLevel() {
        return startingThievingLevel;
    }

    public int getStartingCraftingLevel() {
        return startingCraftingLevel;
    }

    public int getStartingFletchingLevel() {
        return startingFletchingLevel;
    }

    public int getStartingSlayerLevel() {
        return startingSlayerLevel;
    }

    public int getStartingHunterLevel() {
        return startingHunterLevel;
    }

    public int getStartingDivinationLevel() {
        return startingDivinationLevel;
    }

    public int getStartingNecromancyLevel() {
        return startingNecromancyLevel;
    }

    public int getStartingMiningLevel() {
        return startingMiningLevel;
    }

    public int getStartingSmithingLevel() {
        return startingSmithingLevel;
    }

    public int getStartingFishingLevel() {
        return startingFishingLevel;
    }

    public int getStartingCookingLevel() {
        return startingCookingLevel;
    }

    public int getStartingFiremakingLevel() {
        return startingFiremakingLevel;
    }

    public int getStartingWoodcuttingLevel() {
        return startingWoodcuttingLevel;
    }

    public int getStartingFarmingLevel() {
        return startingFarmingLevel;
    }

    public int getStartingSummoningLevel() {
        return startingSummoningLevel;
    }

    public int getStartingInventionLevel() {
        return startingInventionLevel;
    }


    @Override
    public boolean initialize() {
        startingAttackLevel = Skills.ATTACK.getSkill().getLevel();
        startingStrengthLevel = Skills.STRENGTH.getSkill().getLevel();
        startingDefenseLevel = Skills.DEFENSE.getSkill().getLevel();
        startingRangedLevel = Skills.RANGED.getSkill().getLevel();
        startingPrayerLevel = Skills.PRAYER.getSkill().getLevel();
        startingMagicLevel = Skills.MAGIC.getSkill().getLevel();
        startingRunecraftingLevel = Skills.RUNECRAFTING.getSkill().getLevel();
        startingConstructionLevel = Skills.CONSTRUCTION.getSkill().getLevel();
        startingDungeoneeringLevel = Skills.DUNGEONEERING.getSkill().getLevel();
        startingArchaeologyLevel = Skills.ARCHAEOLOGY.getSkill().getLevel();
        startingConstitutionLevel = Skills.CONSTITUTION.getSkill().getLevel();
        startingAgilityLevel = Skills.AGILITY.getSkill().getLevel();
        startingHerbloreLevel = Skills.HERBLORE.getSkill().getLevel();
        startingThievingLevel = Skills.THIEVING.getSkill().getLevel();
        startingCraftingLevel = Skills.CRAFTING.getSkill().getLevel();
        startingFletchingLevel = Skills.FLETCHING.getSkill().getLevel();
        startingSlayerLevel = Skills.SLAYER.getSkill().getLevel();
        startingHunterLevel = Skills.HUNTER.getSkill().getLevel();
        startingDivinationLevel = Skills.DIVINATION.getSkill().getLevel();
        startingNecromancyLevel = Skills.NECROMANCY.getSkill().getLevel();
        startingMiningLevel = Skills.MINING.getSkill().getLevel();
        startingSmithingLevel = Skills.SMITHING.getSkill().getLevel();
        startingFishingLevel = Skills.FISHING.getSkill().getLevel();
        startingCookingLevel = Skills.COOKING.getSkill().getLevel();
        startingFiremakingLevel = Skills.FIREMAKING.getSkill().getLevel();
        startingWoodcuttingLevel = Skills.WOODCUTTING.getSkill().getLevel();
        startingFarmingLevel = Skills.FARMING.getSkill().getLevel();
        startingSummoningLevel = Skills.SUMMONING.getSkill().getLevel();
        startingInventionLevel = Skills.INVENTION.getSkill().getLevel();

        super.initialize();
        this.isBackgroundScript = true;
        this.loopDelay = 600;
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
        return true;
    }

    public SkeletonScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
    }

    @Override
    public void onLoop() {
        if (useprayer)
            usePrayerOrRestorePots();
        if (useoverload)
            drinkOverloads();
        if (UseSoulSplit)
            manageSoulSplit();
        if (useaggression)
            useAggression();
        if (usecooking)
            useCooking();
        if (usedivination)
            useDivination();
        if (useHunter)
            useHunter();
        if (usedarkness)
            useDarkness();
        if (quickprayer)
            manageQuickPrayers();
        if (eatFood)
            eatFood();
    }
    public void setPrayerPointsThreshold(int threshold) {
        this.prayerPointsThreshold = threshold;
    }

    public void setHealthThreshold(int healthThreshold) {
        this.healthThreshold = healthThreshold;
    }

    public void usePrayerOrRestorePots() {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null) {
            int currentPrayerPoints = LocalPlayer.LOCAL_PLAYER.getPrayerPoints();
            println("Current Prayer Points: " + currentPrayerPoints);
            if (currentPrayerPoints < prayerPointsThreshold) {
                ResultSet<Item> items = InventoryItemQuery.newQuery(93).results();

                Item prayerOrRestorePot = items.stream()
                        .filter(item -> item.getName() != null &&
                                (item.getName().toLowerCase().contains("prayer") ||
                                        item.getName().toLowerCase().contains("restore")))
                        .findFirst()
                        .orElse(null);

                if (prayerOrRestorePot != null) {
                    println("Drinking " + prayerOrRestorePot.getName());
                    boolean success = Backpack.interact(prayerOrRestorePot.getName(), "Drink");
                    Execution.delay(RandomGenerator.nextInt(1600, 2100));

                    if (!success) {
                        println("Failed to use " + prayerOrRestorePot.getName());
                    }
                } else {
                    println("No Prayer or Restore pots found.");
                }
            } else {
                println("Current Prayer points are above " + prayerPointsThreshold + " No need to use Prayer or Restore pot.");
            }
        }
    }



    public void drinkOverloads() {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null && !localPlayer.isMoving()) {
            if (VarManager.getVarbitValue(26037) == 0) {
                if (localPlayer.getAnimationId() == 18000) {
                    return;
                }

                ResultSet<Item> overload = InventoryItemQuery.newQuery()
                        .name("overload",  String::contains)
                        .results();
                if (!overload.isEmpty()) {
                    Item overloadItem = overload.first();
                    Backpack.interact(overloadItem.getName(), "Drink");
                    println("Drinking overload " + overloadItem.getName() + " ID: " + overloadItem.getId());
                    Execution.delay(RandomGenerator.nextInt(10, 20));
                }
            }
        }
    }

    private boolean isAggressionActive() {
        Component aggressionIndicator = ComponentQuery.newQuery(284).spriteId(37969).results().first();
        return aggressionIndicator != null;
    }

    private void useAggression() {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null) {
            if (!isAggressionActive()) {
                ResultSet<Item> results = InventoryItemQuery.newQuery(93)
                        .name("Aggression", String::contains)
                        .option("Drink")
                        .results();
                if (!results.isEmpty()) {
                    Item aggressionFlask = results.first();
                    if (aggressionFlask != null) {
                        boolean success = Backpack.interact(aggressionFlask.getName(), "Drink");
                        if (success) {
                            println("Using aggression potion: " + aggressionFlask.getName());
                        } else {
                            println("Failed to use aggression potion: " + aggressionFlask.getName());
                        }
                        Execution.delay(RandomGenerator.nextInt(2000, 3000));
                    }
                } else {
                    println("No aggression flasks found.");
                }
            } else {
                println("Aggression is already active.");
            }
        }
    }

    private boolean soulSplitActive = false;

    public void manageSoulSplit() {
        if (LOCAL_PLAYER.inCombat() && !soulSplitActive) {
            updateSoulSplitActivation();
        } else if (!LOCAL_PLAYER.inCombat() && soulSplitActive) {
            updateSoulSplitActivation();
        }
    }

    private void updateSoulSplitActivation() {
        int soulSplitEnabled = VarManager.getVarbitValue(16779);
        boolean shouldBeActive = shouldActivateSoulSplit();

        if (shouldBeActive && soulSplitEnabled != 1) {
            activateSoulSplit();
        } else if (!shouldBeActive && soulSplitEnabled == 1) {
            deactivateSoulSplit();
        }
    }

    private void activateSoulSplit() {
        if (!soulSplitActive) {
            println("Activating Soul Split.");
            if (ActionBar.useAbility("Soul Split")) {
                println("Soul Split activated successfully.");
                soulSplitActive = true;
            } else {
                println("Failed to activate Soul Split.");
            }
        }
    }

    private void deactivateSoulSplit() {
        if (soulSplitActive) {
            println("Deactivating Soul Split.");
            if (ActionBar.useAbility("Soul Split")) {
                println("Soul Split deactivated.");
                soulSplitActive = false;
            } else {
                println("Failed to deactivate Soul Split.");
            }
        }
    }

    private boolean shouldActivateSoulSplit() {
        return LOCAL_PLAYER.inCombat() && (UseSoulSplit);
    }

    private boolean isCookingActive() {
        Component cooking = ComponentQuery.newQuery(284).spriteId(49010).results().first();
        return cooking != null;
    }

    private void useCooking() {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null) {
            if (!isCookingActive()) {
                ResultSet<Item> results = InventoryItemQuery.newQuery(93)
                        .name("cooking", String::contains)
                        .option("Drink")
                        .results();
                if (!results.isEmpty()) {
                    Item cookingItem = results.first();
                    if (cookingItem != null) {
                        boolean success = Backpack.interact(cookingItem.getName(), "Drink");
                        if (success) {
                            println("Using cooking potion: " + cookingItem.getName());
                        } else {
                            println("Failed to use cooking potion: " + cookingItem.getName());
                        }
                        Execution.delay(RandomGenerator.nextInt(2000, 3000));
                    }
                } else {
                    println("No Extreme cooking potions found.");
                }
            } else {
                println("Cooking boost is already active.");
            }
        }
    }

    private boolean isDivinationActive() {
        Component divination = ComponentQuery.newQuery(284).spriteId(44103).results().first();
        return divination != null;
    }

    private void useDivination() {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null) {
            if (!isDivinationActive()) {
                ResultSet<Item> results = InventoryItemQuery.newQuery(93)
                        .name("divination", String::contains)
                        .option("Drink")
                        .results();
                if (!results.isEmpty()) {
                    Item divinationItem = results.first();
                    if (divinationItem != null) {
                        boolean success = Backpack.interact(divinationItem.getName(), "Drink");
                        if (success) {
                            println("Using divination potion: " + divinationItem.getName());
                        } else {
                            println("Failed to use divination potion: " + divinationItem.getName());
                        }
                        Execution.delay(RandomGenerator.nextInt(2000, 3000));
                    }
                } else {
                    println("No divination potions found.");
                }
            } else {
                println("Divination boost is already active.");
            }
        }
    }

    private boolean isHunterActive() {
        Component hunter = ComponentQuery.newQuery(284).spriteId(44127).results().first();
        return hunter != null;
    }

    private void useHunter() {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null) {
            if (!isHunterActive()) {
                ResultSet<Item> results = InventoryItemQuery.newQuery(93)
                        .name("hunter", String::contains)
                        .option("Drink")
                        .results();
                if (!results.isEmpty()) {
                    Item hunterItem = results.first();
                    if (hunterItem != null) {
                        boolean success = Backpack.interact(hunterItem.getName(), "Drink");
                        if (success) {
                            println("Using hunter potion: " + hunterItem.getName());
                        } else {
                            println("Failed to use hunter potion: " + hunterItem.getName());
                        }
                        Execution.delay(RandomGenerator.nextInt(2000, 3000));
                    }
                } else {
                    println("No hunter potions found.");
                }
            } else {
                println("Hunter boost is already active.");
            }
        }
    }

    private boolean isDarknessActive() {
        Component darkness = ComponentQuery.newQuery(284).spriteId(30122).results().first();
        return darkness != null;
    }

    private void useDarkness() {
        if (getLocalPlayer() != null) {
            if (!isDarknessActive()) {
                ActionBar.useAbility("Darkness");
                println("Using darkness!");
                Execution.delay(RandomGenerator.nextInt(2000, 3000));
            }
        }
    }

    private boolean isQuickPrayersActive() {
        int[] varbitIds = {
                // Curses
                16761, 16762, 16763, 16786, 16764, 16765, 16787, 16788, 16765, 16766,
                16767, 16768, 16769, 16770, 16771, 16772, 16781, 16773, 16782, 16774,
                16775, 16776, 16777, 16778, 16779, 16780, 16784, 16783, 29065, 29066,
                29067, 29068, 29069, 49330, 29071, 34866, 34867, 34868, 53275, 53276,
                53277, 53278, 53279, 53280, 53281,
                // Normal
                16739, 16740, 16741, 16742, 16743, 16744, 16745, 16746, 16747, 16748,
                16749, 16750, 16751, 16752, 16753, 16754, 16755, 16756, 16757, 16758,
                16759, 16760, 53271, 53272, 53273, 53274
        };

        for (int varbitId : varbitIds) {
            if (VarManager.getVarbitValue(varbitId) == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean quickPrayersActive = false;

    public void manageQuickPrayers() {
        if (LOCAL_PLAYER.inCombat() && !quickPrayersActive) {
            updateQuickPrayersActivation();
        } else if (!LOCAL_PLAYER.inCombat() && quickPrayersActive) {
            updateQuickPrayersActivation();
        }
    }

    private void updateQuickPrayersActivation() {
        boolean isCurrentlyActive = isQuickPrayersActive();
        boolean shouldBeActive = shouldActivateQuickPrayers();

        if (shouldBeActive && !isCurrentlyActive) {
            activateQuickPrayers();
        } else if (!shouldBeActive && isCurrentlyActive) {
            deactivateQuickPrayers();
        }
    }

    private void activateQuickPrayers() {
        if (!quickPrayersActive) {
            println("Activating Quick Prayers.");
            if (ActionBar.useAbility("Quick-prayers 1")) {
                println("Quick Prayers activated successfully.");
                quickPrayersActive = true;
            } else {
                println("Failed to activate Quick Prayers.");
            }
        }
    }

    private void deactivateQuickPrayers() {
        if (quickPrayersActive) {
            println("Deactivating Quick Prayers.");
            if (ActionBar.useAbility("Quick-prayers 1")) {
                println("Quick Prayers deactivated.");
                quickPrayersActive = false;
            } else {
                println("Failed to deactivate Quick Prayers.");
            }
        }
    }

    private boolean shouldActivateQuickPrayers() {
        return LOCAL_PLAYER.inCombat();
    }

    public void eatFood() {
        if (getLocalPlayer() != null) {
            if (getLocalPlayer().getCurrentHealth() * 100 / getLocalPlayer().getMaximumHealth() < healthThreshold) {
                ResultSet<Item> food = InventoryItemQuery.newQuery(93).option("Eat").results();
                if (!food.isEmpty()) {
                    Item eat = food.first();
                    Backpack.interact(eat.getName(), 1);
                    println("Eating " + eat.getName());
                    Execution.delayUntil(RandomGenerator.nextInt(300, 500), () -> getLocalPlayer().getCurrentHealth() > 8000);
                } else {
                    println("No food found!");
                }
            }
        }
    }
}

