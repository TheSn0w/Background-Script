package net.botwithus;

import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.api.game.hud.inventories.Equipment;
import net.botwithus.rs3.game.js5.types.vars.VarDomainType;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.Client;
import net.botwithus.rs3.game.Coordinate;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.actionbar.ActionBar;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.js5.types.ItemType;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.queries.builders.animations.SpotAnimationQuery;
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.GroundItemQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.results.EntityResultSet;
import net.botwithus.rs3.game.queries.results.ResultSet;
import net.botwithus.rs3.game.scene.entities.animation.SpotAnimation;
import net.botwithus.rs3.game.scene.entities.characters.npc.Npc;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.characters.player.Player;
import net.botwithus.rs3.game.scene.entities.item.GroundItem;
import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.game.vars.VarManager;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.script.config.ScriptConfig;
import net.botwithus.rs3.util.RandomGenerator;
import net.botwithus.rs3.util.Regex;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.botwithus.rs3.game.Client.getLocalPlayer;
import static net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer.LOCAL_PLAYER;

public class SkeletonScript extends LoopingScript {
    boolean useThievingDummy;
    boolean AttackaTarget;
    boolean Essence;
    boolean UseScriptureOfJas;
    boolean UseScriptureOfFul;
    boolean UseScriptureOfWen;
    boolean useExcalibur;
    boolean useAntifire;
    boolean useNecromancyPotion;
    private long targetLogoutTimeMillis = 0;
    boolean Logout;
    boolean useLightForm;
    boolean useSuperheatForm;
    boolean useCrystalMask;
    boolean useAncientElven;
    boolean useWeaponPoison;
    boolean useEssenceOfFinality;


    boolean usePenance;
    boolean useProtection;
    boolean KwuarmIncence;
    boolean TorstolIncence;
    boolean LantadymeIncence;
    boolean overloadEnabled;
    boolean useAgilityDummy;
    boolean useMagicDummy;
    boolean useRangedDummy;
    boolean useMeleeDummy;
    boolean useLoot;
    private boolean scriptRunning = false;
    long runStartTime;
    private int prayerPointsThreshold = 5000;
    private int healthThreshold = 50;
    private Instant scriptStartTime;
    boolean teleportToWarOnHealth;
    boolean useSaraBrew;
    boolean useSaraBrewandBlubber;
    boolean useVolleyofSouls;
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
    boolean UseSmokeBomb;
    boolean UseVulnBomb;
    boolean InvokeDeath;
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


    public SkeletonScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        loadConfiguration();
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
        if (Client.getGameState() == Client.GameState.LOGGED_IN) {
            setupStartingSkillLevels();
        }
        super.initialize();
        this.isBackgroundScript = true;
        this.loopDelay = RandomGenerator.nextInt(1200, 1800);
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
        this.runStartTime = System.currentTimeMillis();
    }

    public void startScript() {
        println("Attempting to start script...");
        if (!scriptRunning) {
            scriptRunning = true;
            scriptStartTime = Instant.now();
            println("Script started at: " + scriptStartTime);
        } else {
            println("Attempted to start script, but it is already running.");
        }
    }

    public void stopScript() {
        if (scriptRunning) {
            scriptRunning = false;
            Instant stopTime = Instant.now();
            println("Script stopped at: " + stopTime);
            long duration = Duration.between(scriptStartTime, stopTime).toMillis();
            println("Script ran for: " + duration + " milliseconds.");
        } else {
            println("Attempted to stop script, but it is not running.");
        }
    }


    @Override
    public void onLoop() {
        if (getLocalPlayer() != null && Client.getGameState() == Client.GameState.LOGGED_IN && !scriptRunning) {
            return;
        }
        if (Essence)
            DeathEssence();

        if (useVolleyofSouls)
            volleyOfSouls();

        if (useLoot)
            loot();

        if (Logout)
            checkAndPerformLogout();

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

        if (UseSmokeBomb)
            UseSmokeCloud();

        if (UseVulnBomb)
            useVulnBomb();

        if (InvokeDeath)
            Deathmark();

        if (useSaraBrew)
            UseSaraBrew();

        if (useSaraBrewandBlubber)
            UseSaraandBlubber();

        if (teleportToWarOnHealth)
            TeleportToWarOnHealth();

        if (useThievingDummy)
            UseThievingDummy();

        if (useAgilityDummy)
            UseAgilityDummy();

        if (useMagicDummy)
            UseMagicDummy();

        if (useRangedDummy)
            UseRangedDummy();

        if (useMeleeDummy)
            UseMeleeDummy();

        if (KwuarmIncence)
            KwuarmincenceSticks();

        if (TorstolIncence)
            TorstolIncenseSticks();

        if (LantadymeIncence)
            LantadymeIncenseSticks();

        if (usePenance)
            Penance();

        if (useProtection)
            Protection();

        if (useLightForm)
            LightFormActivation();

        if (!useLightForm)
            deactivateLightForm();

        if (useCrystalMask)
            CystalMask();

        if (useSuperheatForm)
            SuperheatFormActivation();

        if (!useSuperheatForm)
            deactivateSuperheatForm();

        if (useNecromancyPotion)
            NecromancyPotion();

        if (useAntifire)
            antifirePotion();

        if (useExcalibur)
            activateExcalibur();

        if (UseScriptureOfWen)
            manageScriptureOfWen();

        if (UseScriptureOfJas)
            manageScriptureOfJas();

        if (UseScriptureOfFul)
            manageScriptureOfFul();

        if (useWeaponPoison)
            useWeaponPoison();

        if (useAncientElven)
            manageAncientElven();

        if (useEssenceOfFinality)
            essenceOfFinality();
        if (AttackaTarget)
            attackTarget();
    }
    private List<String> targetNames = new ArrayList<>();

    public void addTargetName(String targetName) {
        println("Adding target name: " + targetName);
        String lowerCaseName = targetName.toLowerCase();
        if (!targetNames.contains(lowerCaseName)) {
            targetNames.add(lowerCaseName);
        }
    }

    public void removeTargetName(String targetName) {
        targetNames.remove(targetName.toLowerCase());
    }

    public List<String> getTargetNames() {
        return new ArrayList<>(targetNames); // Return a copy to avoid modification
    }
    private String targetName = "";


    public void setTargetName(String targetName) {
        println("Setting target name to: " + targetName);
        this.targetName = targetName;
    }

    public void clearTargetName() {
        this.targetName = "";
    }
    private void attackTarget() {
        if (getLocalPlayer() == null) {
            return;
        }

        List<String> excludedNameFragments = Arrays.asList("vengeful", "putrid", "skeleton", "ripper");
        Npc targetNpc = null;

        for (String target : targetNames) {
            String targetLower = target.toLowerCase();
            targetNpc = NpcQuery.newQuery().results().stream()
                    .filter(npc -> npc.getName().toLowerCase().contains(targetLower))
                    .filter(npc -> excludedNameFragments.stream().noneMatch(excluded -> npc.getName().toLowerCase().contains(excluded)))
                    .findFirst()
                    .orElse(null);

            if (targetNpc != null) {
                break;
            }
        }

        if (targetNpc == null) {
            targetNpc = NpcQuery.newQuery().results().stream()
                    .filter(npc -> excludedNameFragments.stream().noneMatch(excluded -> npc.getName().toLowerCase().contains(excluded)))
                    .min(Comparator.comparingInt(npc -> (int) npc.distanceTo(getLocalPlayer())))
                    .orElse(null);
        }

        if (waitingForAttackCompletion) {
            if (System.currentTimeMillis() - lastAttackTime > 60000 || getLocalPlayer().getFollowing() == null) {
                println("Attack completed or 20 seconds have passed.");
                waitingForAttackCompletion = false; // Reset the flag
            } else {
                return; // Still waiting, exit early
            }
        }

        if (targetNpc != null) {
            println("Attacking " + targetNpc.getName());
            targetNpc.interact("Attack");
            lastAttackTime = System.currentTimeMillis();
            waitingForAttackCompletion = true; // Start waiting for completion
        }
    }
    private long lastAttackTime = -1; // -1 indicates no attack has been initiated
    private boolean waitingForAttackCompletion = false;



    private List<String> targetItemNames = new ArrayList<>();

    public void addItemName(String itemName) {
        if (!targetItemNames.contains(itemName)) {
            targetItemNames.add(itemName);
        }
    }

    public void removeItemName(String itemName) {
        targetItemNames.remove(itemName);
    }

    public List<String> getTargetItemNames() {
        return new ArrayList<>(targetItemNames);
    }

    private void loot() {
        if (getLocalPlayer() == null) {
            return;
        }

        SpotAnimationQuery.newQuery().ids(4419).results().stream()
                .findFirst()
                .ifPresent(this::attemptToPickUpItemOnSpotAnimation);

        collectAllTargetItems(targetItemNames);
    }

    private void collectAllTargetItems(List<String> targetKeywords) {
        List<String> availableItems = getAvailableItems();
        List<String> itemsToCollect = new ArrayList<>();

        for (String item : availableItems) {
            for (String keyword : targetKeywords) {
                if (item.toLowerCase().contains(keyword)) {
                    itemsToCollect.add(item);
                    break;
                }
            }
        }

        collect(itemsToCollect);
    }

    private List<String> getAvailableItems() {
        List<String> items = new ArrayList<>();
        ResultSet<GroundItem> groundItems = GroundItemQuery.newQuery().results();

        for (net.botwithus.rs3.game.scene.entities.item.GroundItem item : groundItems) {
            items.add(item.getName());
        }
        return items;
    }

    private void collect(List<String> itemNames) {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null && ((double) localPlayer.getCurrentHealth() / localPlayer.getMaximumHealth()) < 0.4) {
            return;
        } else if (Backpack.isFull()) {
            manageFullBackpack();
            collect(itemNames);
            return;
        }
        ResultSet<GroundItem> groundItems = GroundItemQuery.newQuery().results();

        boolean foundItem = false;
        for (GroundItem groundItem : groundItems) {
            if (itemNames.contains(groundItem.getName())) {
                foundItem = true;
                ScriptConsole.println("Attempting to pick up: " + groundItem.getName(), new Object[0]);
                groundItem.interact("Take");
                Execution.delayUntil(RandomGenerator.nextInt(5000, 8000), () -> GroundItemQuery.newQuery().name(groundItem.getName()).results().isEmpty());
                ScriptConsole.println("Picked up: " + groundItem.getName(), new Object[0]);
            }
        }
        if (foundItem) {
            collect(itemNames);
        }
    }


    private void attemptToPickUpGroundItem(GroundItem groundItem) {
        if (Backpack.isFull()) {
            manageFullBackpack();
        } else if (!Backpack.isFull() && groundItem != null) {
            println("Attempting to take ground item: " + groundItem.getName());
            boolean interactionSuccess = groundItem.interact("Take");
            Execution.delay(RandomGenerator.nextInt(500, 900));
            if (interactionSuccess) {
                Execution.delayUntil(RandomGenerator.nextInt(4500, 5000), () -> GroundItemQuery.newQuery().name(groundItem.getName()).results().isEmpty());
            }
        }
    }

    private void manageFullBackpack() {
        println("Backpack is full, attempting to eat food.");
        ResultSet<Item> foodItems = InventoryItemQuery.newQuery().option("Eat").results();
        if (!foodItems.isEmpty()) {
            Item food = foodItems.first();
            if (food != null) {
                eatFood(food);
            }
        } else {
            println("No food to eat, retreating.");
        }
    }

    private void eatFood(Item food) {
        println("Attempting to eat " + food.getName());
        boolean success = Backpack.interact(food.getName(), 1);
        if (success) {
            println("Eating " + food.getName());
            Execution.delayUntil(RandomGenerator.nextInt(500, 1000), () -> !Backpack.isFull());
        } else {
            println("Failed to eat " + food.getName());
            Execution.delayUntil(RandomGenerator.nextInt(500, 1000), () -> !Backpack.isFull());
        }
    }

    private void attemptToPickUpItemOnSpotAnimation(SpotAnimation spotAnimation) {
        GroundItem groundItem = GroundItemQuery.newQuery()
                .onTile(spotAnimation.getCoordinate())
                .results()
                .nearest();
        if (groundItem != null) {
            attemptToPickUpGroundItem(groundItem);
        }
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
            }
        }
    }


    Pattern overloads = Pattern.compile(Regex.getPatternForContainsString("overload").pattern(), Pattern.CASE_INSENSITIVE);

    public void drinkOverloads() {
        if (getLocalPlayer() != null && VarManager.getVarbitValue(26037) == 0) {

            ResultSet<Item> items = InventoryItemQuery.newQuery().results();

            Item overloadPot = items.stream()
                    .filter(item -> item.getName() != null && overloads.matcher(item.getName()).find())
                    .findFirst()
                    .orElse(null);

            if (overloadPot != null) {
                println("Drinking " + overloadPot.getName());
                Backpack.interact(overloadPot.getName(), "Drink");
                Execution.delay(RandomGenerator.nextInt(1180, 1220));
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

    public void manageSoulSplit() {
        if (getLocalPlayer() != null && getLocalPlayer().inCombat()) {
            ActivateSoulSplit();
        } else
            DeactivateSoulSplit();
    }

    private void ActivateSoulSplit() {
        if (VarManager.getVarbitValue(16779) == 0) {
            println("Activating Soul Split:  " + ActionBar.useAbility("Soul Split"));
        }
    }
    private void DeactivateSoulSplit() {
        if (VarManager.getVarbitValue(16779) == 1) {
            println("Deactivating Soul Split:  " + ActionBar.useAbility("Soul Split"));
        }
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
        if (getLocalPlayer() == null) {
            return;
        }
        if (getLocalPlayer().inCombat() && !quickPrayersActive) {
            updateQuickPrayersActivation();
        } else if (!getLocalPlayer().inCombat() && quickPrayersActive) {
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
        return getLocalPlayer().inCombat();
    }

    public void eatFood() {
        if (getLocalPlayer() != null) {
            if (getLocalPlayer().getCurrentHealth() * 100 / getLocalPlayer().getMaximumHealth() < healthThreshold) {
                ResultSet<Item> food = InventoryItemQuery.newQuery(93).option("Eat").results();
                if (!food.isEmpty()) {
                    Item eat = food.first();
                    assert eat != null;
                    Backpack.interact(eat.getName(), 1);
                    println("Eating " + eat.getName());
                    Execution.delayUntil(RandomGenerator.nextInt(300, 500), () -> getLocalPlayer().getCurrentHealth() > 8000);
                } else {
                    println("No food found!");
                }
            }
        }
    }

    private void useVulnBomb() {
        if (UseVulnBomb) {
            if (getLocalPlayer() != null && getLocalPlayer().inCombat()) {
                if (getLocalPlayer().getTarget() != null) {
                    int vulnDebuffVarbit = VarManager.getVarbitValue(1939);
                    if (vulnDebuffVarbit == 0) { // 0 means debuff is not active
                        boolean success = ActionBar.useItem("Vulnerability bomb", "Throw");
                        if (success) {
                            println("Throwing Vulnerability bomb at " + getLocalPlayer().getTarget().getName());
                            Execution.delayUntil(RandomGenerator.nextInt(300, 500), () -> !getLocalPlayer().inCombat());
                        } else {
                            println("Failed to use Vulnerability bomb!");
                        }
                    } else {
                        println("Target already has the vulnerability debuff.");
                    }
                } else {
                    println("No target NPC found.");
                }
            }
        }
    }

    private void UseSmokeCloud() {
        if (UseSmokeBomb) {
            if (getLocalPlayer() != null && getLocalPlayer().inCombat()) {
                if (getLocalPlayer().getTarget() != null) {
                    int debuffVarbit = VarManager.getVarbitValue(49448);
                    if (debuffVarbit == 0) {
                        boolean abilityUsed = ActionBar.useAbility("Smoke Cloud");
                        if (abilityUsed) {
                            println("Used 'Smoke Cloud' on " + getLocalPlayer().getTarget().getName());
                        } else {
                            println("Failed to use 'Smoke Cloud'");
                        }
                    } else {
                        println(getLocalPlayer().getTarget().getName() + " already has the debuff.");
                    }
                } else {
                    println("No target NPC found.");
                }
            }
        }
    }
    public static int NecrosisStacksThreshold = 12;

    private void essenceOfFinality() {
        if (getLocalPlayer().getAdrenaline() >= 300
                && ComponentQuery.newQuery(291).spriteId(55524).results().isEmpty()
                && ActionBar.getCooldownPrecise("Essence of Finality") == 0 && getLocalPlayer().inCombat() && getLocalPlayer().getFollowing() != null && getLocalPlayer().hasTarget()){
            if (VarManager.getVarValue(VarDomainType.PLAYER, 10986) >= NecrosisStacksThreshold) {
                println("Used Death Grasp: " + ActionBar.useAbility("Essence of Finality"));
                Execution.delayUntil(RandomGenerator.nextInt(1820, 1850), () -> ComponentQuery.newQuery(291).spriteId(55524).results().isEmpty());
            }
        }
    }
    private void DeathEssence() { //55480 sprite iD
        if (Essence) {
            if (getLocalPlayer() != null) {

                if (getLocalPlayer().getAdrenaline() >= 350 && getLocalPlayer().getFollowing() != null && getLocalPlayer().getFollowing().getCurrentHealth() >= 500 && ComponentQuery.newQuery(291).spriteId(55480).results().isEmpty() && getLocalPlayer().hasTarget()) {
                    println("Used Death Essence: " + ActionBar.useAbility("Weapon Special Attack"));
                    Execution.delayUntil(RandomGenerator.nextInt(1820, 1850), () -> ComponentQuery.newQuery(291).spriteId(55480).results().isEmpty());
                }
            }
        }
    }
    int RisidualSouls = VarManager.getVarValue(VarDomainType.PLAYER, 11035);
    public static int VolleyOfSoulsThreshold = 5;

    private void volleyOfSouls() {
        if (VarManager.getVarValue(VarDomainType.PLAYER, 11035) >= VolleyOfSoulsThreshold && getLocalPlayer().inCombat() && getLocalPlayer().getFollowing() != null && getLocalPlayer().hasTarget()) {
            println("Using Volley of Souls: " + ActionBar.useAbility("Volley of Souls"));
            Execution.delayUntil(RandomGenerator.nextInt(1820, 1850), () -> RisidualSouls >= VolleyOfSoulsThreshold);
        }
    }

    private void Deathmark() {
        if (InvokeDeath) {
            if (getLocalPlayer() != null) {

                if (VarManager.getVarbitValue(53247) == 0 && getLocalPlayer().getFollowing() != null && getLocalPlayer().getFollowing().getCurrentHealth() >= 500 && ActionBar.getCooldownPrecise("Invoke Death") == 0 && getLocalPlayer().hasTarget()) {
                    println("Used Invoke: " + ActionBar.useAbility("Invoke Death"));
                    Execution.delayUntil(RandomGenerator.nextInt(1820, 1850), () -> VarManager.getVarbitValue(53247) == 0);
                }
            }
        }
    }

    private void UseSaraBrew() {
        if (useSaraBrew) {
            if (Client.getLocalPlayer() != null) {
                if (Client.getLocalPlayer().getCurrentHealth() * 100 / Client.getLocalPlayer().getMaximumHealth() < healthThreshold) {
                    ResultSet<Item> items = InventoryItemQuery.newQuery().results();

                    Item saraBrew = items.stream()
                            .filter(item -> item.getName() != null && item.getName().toLowerCase().contains("saradomin"))
                            .findFirst()
                            .orElse(null);

                    if (saraBrew != null) {
                        Backpack.interact(saraBrew.getName(), "Drink");
                        println("Drinking " + saraBrew.getName());
                        Execution.delayUntil(RandomGenerator.nextInt(1800, 2000), () -> {
                            LocalPlayer player = Client.getLocalPlayer();
                            if (player != null) {
                                double healthPercentage = (double) player.getCurrentHealth() / player.getMaximumHealth() * 100;
                                return healthPercentage > 90;
                            }
                            return false;
                        });
                    } else {
                        println("No Saradomin brews found!");
                    }
                }
            }
        }
    }

    private void UseSaraandBlubber() {
        if (useSaraBrewandBlubber) {
            LocalPlayer player = Client.getLocalPlayer();
            if (player != null) {
                double healthPercentage = (double) player.getCurrentHealth() / player.getMaximumHealth() * 100;
                if (healthPercentage < healthThreshold) {
                    ResultSet<Item> items = InventoryItemQuery.newQuery().results();

                    Item saraBrew = items.stream()
                            .filter(item -> item.getName() != null && item.getName().toLowerCase().contains("saradomin"))
                            .findFirst()
                            .orElse(null);

                    if (saraBrew != null) {
                        Backpack.interact(saraBrew.getName(), "Drink");
                        println("Drinking " + saraBrew.getName());
                    } else {
                        println("No Saradomin brews found!");
                    }

                    Item blubberItem = items.stream()
                            .filter(item -> item.getName() != null && item.getName().toLowerCase().contains("blubber"))
                            .findFirst()
                            .orElse(null);

                    if (blubberItem != null) {
                        Backpack.interact(blubberItem.getName(), "Eat");
                        println("Eating " + blubberItem.getName());
                    } else {
                        println("No blubber items found!");
                    }

                    Execution.delayUntil(RandomGenerator.nextInt(1800, 2000), () -> {
                        LocalPlayer currentPlayer = Client.getLocalPlayer();
                        if (currentPlayer != null) {
                            double currentHealthPercentage = (double) currentPlayer.getCurrentHealth() / currentPlayer.getMaximumHealth() * 100;
                            return currentHealthPercentage > healthThreshold;
                        }
                        return false;
                    });
                }
            }
        }
    }

    Coordinate coords = new Coordinate(3295, 10131, 0);
    int warsRetreatRegionId = coords.getRegionId();

    private void TeleportToWarOnHealth() {
        if (teleportToWarOnHealth) {
            LocalPlayer player = Client.getLocalPlayer();
            if (player != null) {
                double healthPercentage = (double) player.getCurrentHealth() / player.getMaximumHealth() * 100;
                if (healthPercentage < healthThreshold) {
                    ResultSet<Item> items = InventoryItemQuery.newQuery().results();

                    boolean hasHealingItem = items.stream().anyMatch(item -> {
                        if (item.getName() != null) {
                            if (item.getName().toLowerCase().contains("saradomin")) return true;
                            ItemType itemType = item.getConfigType();
                            if (itemType != null) {
                                return itemType.getBackpackOptions().contains("Eat");
                            }
                        }
                        return false;
                    });

                    if (!hasHealingItem) {
                        println("No food or Saradomin potions found in backpack. Attempting to teleport to War's Retreat due to low health.");
                        ActionBar.useAbility("War's Retreat Teleport");

                        Execution.delay(5000);

                        Coordinate currentPosition = Client.getLocalPlayer().getCoordinate();
                        int currentRegionId = currentPosition.getRegionId();
                        if (currentRegionId == warsRetreatRegionId) {
                            println("Teleport successful, player is now in War's Retreat region.");
                            stopScript();
                        }
                    }
                }
            }
        }
    }

    private void UseThievingDummy() {
        if (useThievingDummy) {
            EntityResultSet<Npc> results = NpcQuery.newQuery().name("Thieving skill training dummy").option("Pickpocket").results();

            if (results.isEmpty()) {
                ActionBar.useItem("Thieving skill training dummy", "Deploy");
                println("No available dummies. Attempting to deploy a new Thieving skill training dummy.");
                Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !NpcQuery.newQuery().name("Thieving skill training dummy").option("Pickpocket").results().isEmpty());
                results = NpcQuery.newQuery().name("Thieving skill training dummy").option("Pickpocket").results();
            }

            for (Npc dummy : results) {
                if (dummy.interact("Pickpocket")) {
                    println("Successfully interacted with Thieving skill training dummy.");
                    Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !dummy.validate());
                    break;
                } else {
                    println("Failed to interact with the Thieving skill training dummy.");
                }
            }

            if (results.isEmpty()) {
                println("The dummy is no longer available or could not be deployed.");
            }
        }
    }

    private void UseAgilityDummy() {
        EntityResultSet<Npc> results = NpcQuery.newQuery().name("Agility skill training dummy").option("Practice").results();

        if (results.isEmpty()) {
            ActionBar.useItem("Agility skill training dummy", "Deploy");
            println("No available dummies. Attempting to deploy a new Agility skill training dummy.");
            Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !NpcQuery.newQuery().name("Agility skill training dummy").option("Practice").results().isEmpty());
            results = NpcQuery.newQuery().name("Agility skill training dummy").option("Practice").results();
        }

        for (Npc dummy : results) {
            if (dummy.interact("Practice")) {
                println("Successfully interacted with Agility skill training dummy.");
                Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !dummy.validate());
                break;
            } else {
                println("Failed to interact with Agility skill training dummy.");
            }
        }

        if (results.isEmpty()) {
            println("The dummy is no longer available or could not be deployed.");
        }
    }

    private void UseMagicDummy() {
        EntityResultSet<Npc> results = NpcQuery.newQuery().name("Magic training dummy").option("Attack").results();

        if (results.isEmpty()) {
            ActionBar.useItem("Combat training dummy", "Deploy");
            println("No available dummies. Attempting to deploy a new Magic training dummy.");
            Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !NpcQuery.newQuery().name("Magic training dummy").option("Attack").results().isEmpty());
            results = NpcQuery.newQuery().name("Magic training dummy").option("Attack").results();
        }

        for (Npc dummy : results) {
            if (dummy.interact("Attack")) {
                println("Successfully initiated attack on the Magic training dummy.");
                Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !dummy.validate());
                break;
            } else {
                println("Failed to initiate attack on the Magic training dummy.");
            }
        }

        if (results.isEmpty()) {
            println("The dummy is no longer available or could not be deployed.");
        }
    }

    private void UseRangedDummy() {
        EntityResultSet<Npc> results = NpcQuery.newQuery().name("Ranged training dummy").option("Attack").results();

        if (results.isEmpty()) {
            ActionBar.useItem("Combat training dummy", "Deploy");
            println("No available dummies. Attempting to deploy a new Ranged training dummy.");
            Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !NpcQuery.newQuery().name("Ranged training dummy").option("Attack").results().isEmpty());
            results = NpcQuery.newQuery().name("Ranged training dummy").option("Attack").results();
        }

        for (Npc dummy : results) {
            if (dummy.interact("Attack")) {
                println("Successfully initiated attack on the Ranged training dummy.");
                Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !dummy.validate());
                break;
            } else {
                println("Failed to initiate attack on the Ranged training dummy.");
            }
        }

        if (results.isEmpty()) {
            println("The dummy is no longer available or could not be deployed.");
        }
    }

    private void UseMeleeDummy() {
        EntityResultSet<Npc> results = NpcQuery.newQuery().name("Melee training dummy").option("Attack").results();

        if (results.isEmpty()) {
            ActionBar.useItem("Combat training dummy", "Deploy");
            println("No available dummies. Attempting to deploy a new Melee training dummy.");
            Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !NpcQuery.newQuery().name("Melee training dummy").option("Attack").results().isEmpty());
            results = NpcQuery.newQuery().name("Melee training dummy").option("Attack").results();
        }

        for (Npc dummy : results) {
            if (dummy.interact("Attack")) {
                println("Successfully initiated attack on the Melee training dummy.");
                Execution.delayUntil(RandomGenerator.nextInt(20000, 30000), () -> !dummy.validate());
                break;
            } else {
                println("Failed to initiate attack on the Melee training dummy.");
            }
        }

        if (results.isEmpty()) {
            println("The dummy is no longer available or could not be deployed.");
        }
    }

    private void setupStartingSkillLevels() {
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
    }


    public void checkAndPerformLogout() {
        if (Logout) {
            if (System.currentTimeMillis() >= targetLogoutTimeMillis && targetLogoutTimeMillis != 0) {
                println("Logout time reached.");
                while (Client.getLocalPlayer() != null && Client.getLocalPlayer().inCombat()) {
                    println("Player is in combat, waiting until combat ends to logout.");
                    Execution.delay(1000);
                }
                println("Player is no longer in combat, attempting to logout.");
                if (performLogout()) {
                    println("Logout successful.");
                    targetLogoutTimeMillis = 0;
                } else {
                    println("Failed to logout.");
                }
            }
        }
    }

    public boolean performLogout() {
        MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, 7, 93782016);
        println("Opening Settings menu.");
        Execution.delay(RandomGenerator.nextInt(800, 1000));
        boolean logoutMenuOpened = Execution.delayUntil(1000L, () -> Interfaces.isOpen(1433));
        if (logoutMenuOpened) {
            Execution.delay(RandomGenerator.nextInt(1000, 2000));
            MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 93913159);
            println("Attempting to interact with logout button.");
            Execution.delay(RandomGenerator.nextInt(1000, 3000));
            this.stopScript();
            return true;
        } else {
            this.println("Could not find or interact with the logout button.");
            return false;
        }
    }

    public void setTargetLogoutTimeMillis(long targetLogoutTimeMillis) {
        this.targetLogoutTimeMillis = targetLogoutTimeMillis;
    }

    public long getTargetLogoutTimeMillis() {
        return this.targetLogoutTimeMillis;
    }

    private void KwuarmincenceSticks() {
        if (KwuarmIncence) {
            ResultSet<Item> backpackResults = InventoryItemQuery.newQuery(93)
                    .name("Kwuarm incence sticks")
                    .results();

            if (!backpackResults.isEmpty()) {
                ResultSet<Component> componentResults = ComponentQuery.newQuery(284)
                        .spriteId(47709)
                        .results();

                if (componentResults.isEmpty()) {
                    Item itemToInteract = backpackResults.first();

                    String option = "Light";
                    if (overloadEnabled) {
                        option = "Overload";
                    }

                    if (Backpack.interact(option)) {
                        println("Interaction successful with option: " + option);
                    } else {
                        println("Failed to interact with the item using option: " + option);
                    }
                }
            }
        }
    }

    private void LantadymeIncenseSticks() {
        if (LantadymeIncence) {
            ResultSet<Item> backpackResults = InventoryItemQuery.newQuery(93)
                    .name("Lantadyme incence sticks")
                    .results();

            if (!backpackResults.isEmpty()) {
                ResultSet<Component> componentResults = ComponentQuery.newQuery(284)
                        .spriteId(47713)
                        .results();

                if (componentResults.isEmpty()) {
                    Item itemToInteract = backpackResults.first();

                    String option = "Light";
                    if (overloadEnabled) {
                        option = "Overload";
                    }

                    if (Backpack.interact(option)) {
                        println("Interaction successful with option: " + option);
                    } else {
                        println("Failed to interact with the item using option: " + option);
                    }
                }
            }
        }
    }

    private void TorstolIncenseSticks() {
        if (TorstolIncence) {
            ResultSet<Item> backpackResults = InventoryItemQuery.newQuery(93)
                    .name("Torstol incence sticks")
                    .results();

            if (!backpackResults.isEmpty()) {
                ResultSet<Component> componentResults = ComponentQuery.newQuery(284)
                        .spriteId(47715)
                        .results();

                if (componentResults.isEmpty()) {
                    Item itemToInteract = backpackResults.first();

                    String option = "Light";
                    if (overloadEnabled) {
                        option = "Overload";
                    }

                    if (Backpack.interact(option)) {
                        println("Interaction successful with option: " + option);
                    } else {
                        println("Failed to interact with the item using option: " + option);
                    }
                }
            }
        }
    }

    private void Penance() {
        if (usePenance) {
            ResultSet<Item> results = InventoryItemQuery.newQuery(93).ids(52806).option("Scatter").results();
            boolean varbitNotSet = VarManager.getVarbitValue(50841) == 0;

            if (!results.isEmpty() && varbitNotSet) {
                Execution.delay(RandomGenerator.nextInt(1000, 2000));
                Item itemToInteract = results.first();
                if (itemToInteract != null) {
                    boolean interacted = Backpack.interact(itemToInteract.getName(), "Scatter");
                    if (interacted) {
                        println("Interacted with Powder of Penance.");
                    } else {
                        println("Failed to interact with Powder of Penance.");
                    }
                }
            }
        }
    }

    private void Protection() {
        if (useProtection) {
            ResultSet<Item> results = InventoryItemQuery.newQuery(93).ids(52802).option("Scatter").results();
            boolean varbitNotSet = VarManager.getVarbitValue(50837) == 0;

            if (!results.isEmpty() && varbitNotSet) {
                Execution.delay(RandomGenerator.nextInt(1000, 2000));
                Item itemToInteract = results.first();
                if (itemToInteract != null) {
                    boolean interacted = Backpack.interact(itemToInteract.getName(), "Scatter");
                    if (interacted) {
                        println("Interacted with Powder of Protection.");
                    } else {
                        println("Failed to interact with Powder of Protection.");
                    }
                }
            }
        }
    }

    private boolean isCrystalMaskActive() {
        ComponentQuery query = ComponentQuery.newQuery(284).spriteId(25938);
        ResultSet<Component> results = query.results();

        return !results.isEmpty();
    }


    private void CystalMask() {
        if (useCrystalMask && !isCrystalMaskActive()) {
            println("Activating Crystal Mask.");
            if (ActionBar.useAbility("Crystal Mask")) {
                println("Crystal Mask activated successfully.");
                Execution.delay(RandomGenerator.nextInt(1000, 2000));
            } else {
                println("Failed to activate Crystal Mask.");
            }
        }
    }

    private boolean lightFormActive = false;


    private void LightFormActivation() {
        if (useLightForm) {
            int lightFormEnabled = VarManager.getVarbitValue(29066);
            if (!lightFormActive && lightFormEnabled != 1) {
                activateLightForm();
            } else if (lightFormActive && lightFormEnabled == 1 && !useLightForm) {
                deactivateLightForm();
            }
        }
    }

    private void activateLightForm() {
        ActionBar.useAbility("Light Form");
        println("Light Form activated.");
        Execution.delay(RandomGenerator.nextInt(1000, 2000));
    }

    private void deactivateLightForm() {
        int lightFormEnabled = VarManager.getVarbitValue(29066);
        if (lightFormEnabled == 1) {
            ActionBar.useAbility("Light Form");
            println("Light Form deactivated.");
            Execution.delay(RandomGenerator.nextInt(1000, 2000));
        }
    }

    private boolean superheatFormActive = false;

    private void SuperheatFormActivation() {
        if (useSuperheatForm) {
            int superheatFormEnabled = VarManager.getVarbitValue(29071);
            if (!superheatFormActive && superheatFormEnabled != 1) {
                activateSuperheatForm();
            } else if (superheatFormActive && superheatFormEnabled == 1 && !useSuperheatForm) {
                deactivateSuperheatForm();
            }
        }
    }

    private void activateSuperheatForm() {
        ActionBar.useAbility("Superheat Form");
        println("Superheat Form activated.");
        Execution.delay(RandomGenerator.nextInt(1000, 2000));
        superheatFormActive = true; // Update the state to reflect activation
    }

    private void deactivateSuperheatForm() {
        int superheatFormEnabled = VarManager.getVarbitValue(29071);
        if (superheatFormEnabled == 1) {
            ActionBar.useAbility("Superheat Form");
            println("Superheat Form deactivated.");
            Execution.delay(RandomGenerator.nextInt(1000, 2000));
            superheatFormActive = false; // Update the state to reflect deactivation
        }
    }

    private boolean isNecromancyActive() {
        Component necromancy = ComponentQuery.newQuery(284) // Assuming this interface ID is correct; adjust if necessary
                .spriteId(30125) // Updated sprite ID for Necromancy
                .results().first();
        return necromancy != null;
    }

    private void NecromancyPotion() {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null) {
            if (!isNecromancyActive()) {
                ResultSet<Item> results = InventoryItemQuery.newQuery(93)
                        .name("necromancy", String::contains)
                        .option("Drink")
                        .results();
                if (!results.isEmpty()) {
                    Item necromancyItem = results.first();
                    if (necromancyItem != null) {
                        boolean success = Backpack.interact(necromancyItem.getName(), "Drink");
                        if (success) {
                            println("Using necromancy item: " + necromancyItem.getName());
                        } else {
                            println("Failed to use necromancy item: " + necromancyItem.getName());
                        }
                        Execution.delay(RandomGenerator.nextInt(2000, 3000));
                    }
                } else {
                    println("No necromancy items found.");
                }
            } else {
                println("Necromancy boost is already active.");
            }
        }
    }

    private boolean isantifireActive() {
        Component necromancy = ComponentQuery.newQuery(284) // Assuming this interface ID is correct; adjust if necessary
                .spriteId(30093) // Updated sprite ID for Necromancy
                .results().first();
        return necromancy != null;
    }

    private void antifirePotion() {
        if (useAntifire) {
            Player localPlayer = Client.getLocalPlayer();
            if (localPlayer != null) {
                if (!isantifireActive()) {
                    ResultSet<Item> items = InventoryItemQuery.newQuery(93).results();
                    Item antifireItem = items.stream()
                            .filter(item -> item.getName() != null && item.getName().toLowerCase().contains("antifire"))
                            .findFirst()
                            .orElse(null);

                    if (antifireItem != null) {
                        Backpack.interact(antifireItem.getName(), "Drink");
                        println("Drinking " + antifireItem.getName());
                    } else {
                        println("No Antifire potions found!");
                    }
                } else {
                    println("Antifire boost is already active.");
                }
            }
        }
    }

    private boolean isExcaliburActive() {
        Component excaliburComponent = ComponentQuery.newQuery(291)
                .spriteId(14632)
                .results().first();
        return excaliburComponent != null;
    }

    private void activateExcalibur() {
        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null) {
            if (localPlayer.getCurrentHealth() * 100 / localPlayer.getMaximumHealth() < healthThreshold) {
                if (!isExcaliburActive()) {
                    ResultSet<Item> items = InventoryItemQuery.newQuery(93).results();
                    Item excaliburItem = items.stream()
                            .filter(item -> item.getName() != null && item.getName().toLowerCase().contains("excalibur"))
                            .findFirst()
                            .orElse(null);

                    if (excaliburItem != null) {
                        Backpack.interact(excaliburItem.getName(), "Activate");
                        println("Activating " + excaliburItem.getName());
                    } else {
                        println("No Excalibur found!");
                    }
                } else {
                    println("Excalibur is on CD.");
                }
            } else {
                println("Health is above threshold, no need to activate Excalibur.");
            }
        }
    }
    private void manageScriptureOfJas() {
        if (getLocalPlayer() != null) {
            if (getLocalPlayer().inCombat()) {
                activateScriptureOfJas();
            } else {
                deactivateScriptureOfJas();
            }
        }
    }
    private void activateScriptureOfJas() {
        if (VarManager.getVarbitValue(30605) == 0 && VarManager.getVarbitValue(30604) >= 60) {
            println("Activated Scripture of Jas:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }


    private void deactivateScriptureOfJas() {
        if (VarManager.getVarbitValue(30605) == 1) {
            println("Deactivated Scripture of Jas:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }
    private void manageScriptureOfWen() {
        if (getLocalPlayer() != null) {
            if (getLocalPlayer().inCombat()) {
                activateScriptureOfWen();
            } else {
                deactivateScriptureOfWen();
            }
        }
    }
    private void activateScriptureOfWen() {
        if (VarManager.getVarbitValue(30605) == 0 && VarManager.getVarbitValue(30604) >= 60) {
            println("Activated Scripture of Wen:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }

    private void deactivateScriptureOfWen() {
        if (VarManager.getVarbitValue(30605) == 1) {
            println("Deactivated Scripture of Wen:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }

    private void manageScriptureOfFul() {
        if (getLocalPlayer() != null) {
            if (getLocalPlayer().inCombat()) {
                activateScriptureOfFul();
            } else {
                deactivateScriptureOfFul();
            }
        }
    }
    private void activateScriptureOfFul() {
        if (VarManager.getVarbitValue(30605) == 0 && VarManager.getVarbitValue(30604) >= 60) {
            println("Activated Scripture of Ful:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }
    private void deactivateScriptureOfFul() {
        if (VarManager.getVarbitValue(30605) == 1) {
            println("Deactivated Scripture of Jas:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }

    public void useWeaponPoison() {
        Player localPlayer = getLocalPlayer();
        if (localPlayer != null) {
            if (VarManager.getVarbitValue(2102) <= 3 && getLocalPlayer().getAnimationId() != 18068) { // 2102 = time remaining 18068, animation ID for drinking / 45317 = 4 on weapon poison+++
                ResultSet<Item> items = InventoryItemQuery.newQuery().results();
                Pattern poisonPattern = Pattern.compile("weapon poison\\+*?", Pattern.CASE_INSENSITIVE);

                Item weaponPoisonItem = items.stream()
                        .filter(item -> {
                            if (item.getName() == null) return false;
                            Matcher matcher = poisonPattern.matcher(item.getName());
                            return matcher.find();
                        })
                        .findFirst()
                        .orElse(null);

                if (weaponPoisonItem != null) {
                    println("Applying " + weaponPoisonItem.getName() + " ID: " + weaponPoisonItem.getId());
                    Backpack.interact(weaponPoisonItem.getName(), "Apply");
                    println(weaponPoisonItem.getName() + "Has been applied");
                    Execution.delay(RandomGenerator.nextInt(600, 700));

                }
            }
        }
    }

    private boolean hasComponentWithSpriteId(int interfaceId, int spriteId) {
        ResultSet<Component> components = ComponentQuery.newQuery(interfaceId)
                .spriteId(spriteId)
                .results();
        return !components.isEmpty();
    }

    private void manageAncientElven() {
        if (getLocalPlayer() != null) {

            int currentPrayerPoints = LocalPlayer.LOCAL_PLAYER.getPrayerPoints();

            if (currentPrayerPoints < prayerPointsThreshold && !hasComponentWithSpriteId(291, 43358)) {
                InventoryItemQuery.newQuery(93).name("Ancient elven ritual shard").results();
                Backpack.interact("Ancient elven ritual shard", "Activate");
                println("Activated Ancient Elven Ritual Shard at: " + currentPrayerPoints + " prayer points.");
                Execution.delayUntil(10000, () -> hasComponentWithSpriteId(284, 43358));
            }
        }
    }

    public void saveConfiguration() { //CIPHER PLEASE HELP ME WITH GETTING THE ADJUSTABLE SETTINGS TO SAVE, I DMED YOU BUT NO REPLY
        // Saving boolean settings
        this.configuration.addProperty("UseScriptureOfWen", String.valueOf(this.UseScriptureOfWen));
        this.configuration.addProperty("UseScriptureOfJas", String.valueOf(this.UseScriptureOfJas));
        this.configuration.addProperty("UseScriptureOfFul", String.valueOf(this.UseScriptureOfFul));
        this.configuration.addProperty("useWeaponPoison", String.valueOf(this.useWeaponPoison));
        this.configuration.addProperty("useAncientElven", String.valueOf(this.useAncientElven));
        this.configuration.addProperty("useAntifire", String.valueOf(this.useAntifire));
        this.configuration.addProperty("useExcalibur", String.valueOf(this.useExcalibur));
        this.configuration.addProperty("UseNecromancyPotion", String.valueOf(this.useNecromancyPotion));
        this.configuration.addProperty("UseSuperheatForm", String.valueOf(this.useSuperheatForm));
        this.configuration.addProperty("UseCrystalMask", String.valueOf(this.useCrystalMask));
        this.configuration.addProperty("UseLightForm", String.valueOf(this.useLightForm));
        this.configuration.addProperty("useProtection", String.valueOf(this.useProtection));
        this.configuration.addProperty("usePenance", String.valueOf(this.usePenance));
        this.configuration.addProperty("TorstolIncence", String.valueOf(this.TorstolIncence));
        this.configuration.addProperty("KwuarmIncence", String.valueOf(this.KwuarmIncence));
        this.configuration.addProperty("LantadymeIncence", String.valueOf(this.LantadymeIncence));
        this.configuration.addProperty("teleportToWarOnHealth", String.valueOf(this.teleportToWarOnHealth));
        this.configuration.addProperty("InvokeDeath", String.valueOf(this.InvokeDeath));
        this.configuration.addProperty("UseVulnBomb", String.valueOf(this.UseVulnBomb));
        this.configuration.addProperty("UseSmokeBomb", String.valueOf(this.UseSmokeBomb));
        this.configuration.addProperty("quickprayer", String.valueOf(this.quickprayer));
        this.configuration.addProperty("usedarkness", String.valueOf(this.usedarkness));
        this.configuration.addProperty("useHunter", String.valueOf(this.useHunter));
        this.configuration.addProperty("usedivination", String.valueOf(this.usedivination));
        this.configuration.addProperty("usecooking", String.valueOf(this.usecooking));
        this.configuration.addProperty("useaggression", String.valueOf(this.useaggression));
        this.configuration.addProperty("UseSoulSplit", String.valueOf(this.UseSoulSplit));
        this.configuration.addProperty("useoverload", String.valueOf(this.useoverload));
        this.configuration.addProperty("useprayer", String.valueOf(this.useprayer));
        this.configuration.addProperty("eatFood", String.valueOf(this.eatFood));
        this.configuration.addProperty("useSaraBrewandBlubber", String.valueOf(this.useSaraBrewandBlubber));
        this.configuration.addProperty("useSaraBrew", String.valueOf(this.useSaraBrew));
        this.configuration.addProperty("useLoot", String.valueOf(this.useLoot));
        this.configuration.addProperty("useEssenceOfFinality", String.valueOf(this.useEssenceOfFinality));
        this.configuration.addProperty("useVolleyofSouls", String.valueOf(this.useVolleyofSouls));
        this.configuration.addProperty("Essence", String.valueOf(this.Essence));
        this.configuration.addProperty("AttackaTarget", String.valueOf(this.AttackaTarget));



        this.configuration.save();
    }


    public void loadConfiguration() {
        try {
            this.useSaraBrew = Boolean.parseBoolean(this.configuration.getProperty("useSaraBrew"));
            this.useSaraBrewandBlubber = Boolean.parseBoolean(this.configuration.getProperty("useSaraBrewandBlubber"));
            this.eatFood = Boolean.parseBoolean(this.configuration.getProperty("eatFood"));
            this.useprayer = Boolean.parseBoolean(this.configuration.getProperty("useprayer"));
            this.useoverload = Boolean.parseBoolean(this.configuration.getProperty("useoverload"));
            this.UseSoulSplit = Boolean.parseBoolean(this.configuration.getProperty("UseSoulSplit"));
            this.useaggression = Boolean.parseBoolean(this.configuration.getProperty("useaggression"));
            this.usecooking = Boolean.parseBoolean(this.configuration.getProperty("useCooking"));
            this.usedivination = Boolean.parseBoolean(this.configuration.getProperty("useDivination"));
            this.useHunter = Boolean.parseBoolean(this.configuration.getProperty("useHunter"));
            this.usedarkness = Boolean.parseBoolean(this.configuration.getProperty("usedarkness"));
            this.quickprayer = Boolean.parseBoolean(this.configuration.getProperty("quickprayer"));
            this.UseSmokeBomb = Boolean.parseBoolean(this.configuration.getProperty("UseSmokeBomb"));
            this.UseVulnBomb = Boolean.parseBoolean(this.configuration.getProperty("UseVulnBomb"));
            this.InvokeDeath = Boolean.parseBoolean(this.configuration.getProperty("InvokeDeath"));
            this.teleportToWarOnHealth = Boolean.parseBoolean(this.configuration.getProperty("teleportToWarOnHealth"));
            this.TorstolIncence = Boolean.parseBoolean(this.configuration.getProperty("TorstolIncence"));
            this.KwuarmIncence = Boolean.parseBoolean(this.configuration.getProperty("KwuarmIncence"));
            this.LantadymeIncence = Boolean.parseBoolean(this.configuration.getProperty("LantadymeIncence"));
            this.usePenance = Boolean.parseBoolean(this.configuration.getProperty("usePenance"));
            this.useProtection = Boolean.parseBoolean(this.configuration.getProperty("useProtection"));
            this.useLightForm = Boolean.parseBoolean(this.configuration.getProperty("useLightForm"));
            this.useCrystalMask = Boolean.parseBoolean(this.configuration.getProperty("useCrystalMask"));
            this.useSuperheatForm = Boolean.parseBoolean(this.configuration.getProperty("useSuperheatForm"));
            this.useNecromancyPotion = Boolean.parseBoolean(this.configuration.getProperty("useNecromancyPotion"));
            this.useAntifire = Boolean.parseBoolean(this.configuration.getProperty("useAntifire"));
            this.useExcalibur = Boolean.parseBoolean(this.configuration.getProperty("useExcalibur"));
            this.UseScriptureOfWen = Boolean.parseBoolean(this.configuration.getProperty("UseScriptureOfWen"));
            this.UseScriptureOfJas = Boolean.parseBoolean(this.configuration.getProperty("UseScriptureOfJas"));
            this.UseScriptureOfFul = Boolean.parseBoolean(this.configuration.getProperty("UseScriptureOfFul"));
            this.useWeaponPoison = Boolean.parseBoolean(this.configuration.getProperty("useWeaponPoison"));
            this.useAncientElven = Boolean.parseBoolean(this.configuration.getProperty("useAncientElven"));
            this.useLoot = Boolean.parseBoolean(this.configuration.getProperty("useLoot"));
            this.useEssenceOfFinality = Boolean.parseBoolean(this.configuration.getProperty("useEssenceOfFinality"));
            this.useVolleyofSouls = Boolean.parseBoolean(this.configuration.getProperty("useVolleyofSouls"));
            this.Essence = Boolean.parseBoolean(this.configuration.getProperty("Essence"));
            this.AttackaTarget = Boolean.parseBoolean(this.configuration.getProperty("AttackaTarget"));


            println("Configuration loaded successfully.");
        } catch (Exception e) {
            println("Failed to load configuration. Using defaults.");
        }
    }
}
