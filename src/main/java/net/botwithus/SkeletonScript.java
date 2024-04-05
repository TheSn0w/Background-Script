package net.botwithus;

import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.api.game.hud.inventories.Equipment;
import net.botwithus.rs3.events.EventBus;
import net.botwithus.rs3.events.impl.InventoryUpdateEvent;
import net.botwithus.rs3.game.Distance;
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
import net.botwithus.rs3.game.minimenu.actions.SelectableAction;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.botwithus.rs3.game.Client.getLocalPlayer;

public class SkeletonScript extends LoopingScript {
    LocalPlayer Self = Client.getLocalPlayer();
    boolean Notepaper;
    boolean HopWorlds;
    boolean useThievingDummy;
    private List<String> itemNamesToUseOnNotepaper = new ArrayList<>();
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
    boolean useLootInterface;
    boolean InteractWithLootAll;
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
    public static int prayerPointsThreshold = 5000;
    public static int healthThreshold = 50;
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

    static int[] membersWorlds = new int[]{
            1, 2, 4, 5, 6, 9, 10, 12, 14, 15,
            16, 21, 22, 23, 24, 25, 26, 27, 28, 31,
            32, 35, 36, 37, 39, 40, 42, 44, 45, 46,
            47, 49, 50, 51, 53, 54, 56, 58, 59, 60,
            62, 63, 64, 65, 66, 67, 68, 69, 70, 71,
            72, 73, 74, 75, 76, 77, 78, 79, 82, 83,
            85, 87, 88, 89, 91, 92, 97, 98, 99, 100,
            102, 103, 104, 105, 106, 116, 117, 118, 119, 121,
            123, 124, 134, 138, 139, 140, 252, 257, 258};





    public SkeletonScript(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
        loadConfiguration();
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
        super.initialize();
        this.isBackgroundScript = true;
        this.loopDelay = RandomGenerator.nextInt(1200, 1800);
        this.sgc = new SkeletonScriptGraphicsContext(getConsole(), this);
        this.runStartTime = System.currentTimeMillis();
        subscribe(InventoryUpdateEvent.class, this::onInventoryUpdate);
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

            // Unsubscribe from the event
            unsubscribeFromInventoryUpdateEvent(); // This is a placeholder. Replace it with actual API call.

            Instant stopTime = Instant.now();
            println("Script stopped at: " + stopTime);
            long duration = Duration.between(scriptStartTime, stopTime).toMillis();
            println("Script ran for: " + duration + " milliseconds.");
        } else {
            println("Attempted to stop script, but it is not running.");
        }
    }

    private void unsubscribeFromInventoryUpdateEvent() {
        unsubscribeAll();
        // For example: eventManager.removeListener(this::onInventoryUpdate);
    }


    @Override
    public void onLoop() {
        if (getLocalPlayer() != null && Client.getGameState() == Client.GameState.LOGGED_IN && !scriptRunning) {
            return;
        }
        if (HopWorlds) {
            WorldHopper();
        }
        if (InteractWithLootAll)
            LootEverything();

        if (useLootInterface)
            LootFromInterfaces();

        if (Notepaper)
            for (String itemName : getItemNamesToUseOnNotepaper()) {
                useItemOnNotepaper(itemName);
            }

        if (Essence)
            DeathEssence();

        if (useVolleyofSouls)
            volleyOfSouls();

        if (useLoot)
            loot();
        retryCollection();

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

    private Integer pickedUpItemId = null;

    private void onInventoryUpdate(InventoryUpdateEvent event) {
        // Debug message to confirm the event triggers

        Item newItem = event.getNewItem(); // Assuming this method gives you the newly added item
        if (newItem != null) {
            pickedUpItemId = newItem.getId(); // Capture the newly picked-up item's ID
        }
    }

    public List<String> getItemNamesToUseOnNotepaper() {
        return new ArrayList<>(itemNamesToUseOnNotepaper); // Return a copy to avoid external modification
    }

    public void addItemNameToUseOnNotepaper(String itemName) {
        if (!itemNamesToUseOnNotepaper.contains(itemName)) {
            itemNamesToUseOnNotepaper.add(itemName);
        }
    }

    public void removeItemNameToUseOnNotepaper(String itemName) {
        itemNamesToUseOnNotepaper.remove(itemName); // Assumes you're storing item names in lowercase for case-insensitive matching
    }

    public void useItemOnNotepaper(String itemName) {
        if (pickedUpItemId == null) {
            return;
        }


        Item targetItem = InventoryItemQuery.newQuery(93)
                .name(itemName)
                .results()
                .stream()
                .filter(item -> item.getId() == pickedUpItemId && item.getStackSize() == 1)
                .findFirst()
                .orElse(null);

        if (targetItem == null) {
            println(itemName + " matching the recent pickup criteria not found or it's already noted.");
            return;
        }

        // Retrieve Magic Notepaper from the inventory
        Item notepaper = InventoryItemQuery.newQuery(93)
                .name("Magic notepaper")
                .results()
                .stream()
                .findFirst()
                .orElse(null);

        if (notepaper == null) {
            println("Magic Notepaper not found in inventory.");
            return;
        }

        println("Found " + itemName + " and Magic Notepaper. Preparing to use.");

        // Simulate 'Use' action on the target item
        boolean itemSelected = MiniMenu.interact(SelectableAction.SELECTABLE_COMPONENT.getType(), 0, targetItem.getSlot(), 96534533);
        Execution.delay(RandomGenerator.nextInt(500, 750));

        if (itemSelected) {
            println("Using " + itemName + " on Magic Notepaper...");

            // Simulate 'Use' action on Magic Notepaper
            boolean notepaperSelected = MiniMenu.interact(SelectableAction.SELECT_COMPONENT_ITEM.getType(), 0, notepaper.getSlot(), 96534533);
            Execution.delay(RandomGenerator.nextInt(522, 787));

            if (notepaperSelected) {
                println(itemName + " successfully used on Magic Notepaper.");
            } else {
                println("Failed to use " + itemName + " on Magic Notepaper.");
            }
        } else {
            println("Failed to select " + itemName + ".");
        }
    }


    public void removeTargetName(String targetName) {
        targetNames.remove(targetName.toLowerCase());
    }

    public List<String> getTargetNames() {
        return new ArrayList<>(targetNames); // Return a copy to avoid modification
    }

    private void attackTarget() {
        if (getLocalPlayer() == null) {
            return;
        }

        Npc targetNpc = null;

        for (String target : targetNames) {
            String targetLower = target.toLowerCase();
            targetNpc = NpcQuery.newQuery().results().stream()
                    .filter(npc -> npc.getName().toLowerCase().contains(targetLower))
                    .filter(npc -> npc.getOptions().contains("Attack"))
                    .findFirst()
                    .orElse(null);

            if (targetNpc != null) {
                break;
            }
        }

        if (targetNpc == null) {
            targetNpc = NpcQuery.newQuery().results().stream()
                    .filter(npc -> npc.getOptions().contains("Attack"))
                    .min(Comparator.comparingInt(npc -> (int) npc.distanceTo(getLocalPlayer())))
                    .orElse(null);
        }

        if (waitingForAttackCompletion) {
            if (System.currentTimeMillis() - lastAttackTime > 60000 || !getLocalPlayer().hasTarget()) {
                waitingForAttackCompletion = false; // Reset the flag
            } else {
                return; // Still waiting, exit early
            }
        }

        if (targetNpc != null && !getLocalPlayer().hasTarget() && targetNpc.getCurrentHealth() > 100) {
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

    private void lootInterface() {
        if (!scriptRunning) {
            return;
        }
        if (getLocalPlayer() != null) {

            EntityResultSet<GroundItem> groundItems = GroundItemQuery.newQuery().results();
            if (!groundItems.isEmpty() && !Backpack.isFull()) {
                GroundItem groundItem = groundItems.nearest();
                if (groundItem != null) {
                    groundItem.interact("Take");
                    Execution.delayUntil(RandomGenerator.nextInt(5000, 5500), () -> getLocalPlayer().isMoving());

                    if (getLocalPlayer().isMoving() && groundItem.getCoordinate() != null && Distance.between(getLocalPlayer().getCoordinate(), groundItem.getCoordinate()) > 10) {
                        ScriptConsole.println("Used Surge: " + ActionBar.useAbility("Surge"));
                        Execution.delay(RandomGenerator.nextInt(200, 250));
                    }

                    if (groundItem.getCoordinate() != null) {
                        Execution.delayUntil(RandomGenerator.nextInt(100, 200), () -> Distance.between(getLocalPlayer().getCoordinate(), groundItem.getCoordinate()) <= 10);
                    }

                    if (groundItem.interact("Take")) {
                        println("Taking " + groundItem.getName() + "...");
                        Execution.delay(RandomGenerator.nextInt(600, 700));
                    }

                    boolean interfaceOpened = Execution.delayUntil(15000, () -> Interfaces.isOpen(1622));
                    if (!interfaceOpened) {
                        println("Interface 1622 did not open. Attempting to interact with ground item again.");
                        if (groundItem.interact("Take")) {
                            println("Attempting to take " + groundItem.getName() + " again...");
                            Execution.delay(RandomGenerator.nextInt(250, 300));
                        }
                    }
                    LootAll();
                }
            }
        }
    }


    private void LootAll() {
        // First, check if there are any ground items to collect.
        EntityResultSet<GroundItem> groundItems = GroundItemQuery.newQuery().results();
        if (groundItems.isEmpty()) {
            return; // Exit the method early if there are no items to loot.
        }

        // Proceed with the delay before interacting with the Loot All component.
        Execution.delay(RandomGenerator.nextInt(1500, 2000));

        ComponentQuery lootAllQuery = ComponentQuery.newQuery(1622);
        List<Component> components = lootAllQuery.componentIndex(22).results().stream().toList();

        if (!components.isEmpty() && components.get(0).interact(1)) {
            println("Successfully interacted with Loot All.");
            Execution.delay(RandomGenerator.nextInt(806, 1259));
            // Additional logic here, if needed, after successful interaction.
        }
    }

    private void LootEverything() {
        if (InteractWithLootAll) {
            if (Interfaces.isOpen(1622)) {
                LootAll();
            } else {
                lootInterface();
                Execution.delayUntil(10000, () -> Interfaces.isOpen(1622));
            }
        }
    }

    private void LootFromInterfaces() {
        if (!scriptRunning) {
            return;
        }
        if (Interfaces.isOpen(1622)) {
            interactWithInventoryItems();
        } else {
            // If interface 1622 is not open, attempt to interact with ground items first.
            List<String> availableItems = getAvailableItems();
            for (String itemName : availableItems) {
                if (targetItemNames.stream().anyMatch(targetItemName -> itemName.toLowerCase().contains(targetItemName.toLowerCase()))) {
                    GroundItem groundItem = GroundItemQuery.newQuery().name(itemName).results().first();
                    if (groundItem != null && groundItem.interact("Take")) {
                        println("Interacted with: " + itemName + " on the ground as Loot interface is not open.");
                        Execution.delayUntil(10000, () -> Interfaces.isOpen(1622));
                        interactWithInventoryItems(); // Now, interact with the inventory items.
                        break; // Assuming only one item needs interaction per loot action.
                    }
                }
            }
        }
    }

    private void interactWithInventoryItems() {
        // Assuming 773 is the id for the inventory where items are to be interacted with.
        InventoryItemQuery.newQuery(773).results().stream()
                .filter(item -> targetItemNames.stream()
                        .anyMatch(targetItemName -> item.getName().toLowerCase().contains(targetItemName.toLowerCase())))
                .findFirst()
                .ifPresent(item -> {
                    int itemSlot = item.getSlot(); // Obtain the slot of the item.
                    // Attempt to interact with the item using MiniMenu.
                    if (MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, itemSlot, 106299403)) {
                        println("Interacted with Loot Interface item: " + item.getName());
                        Execution.delayUntil(5000, () -> !Interfaces.isOpen(1622)); // Adjust based on the action's completion.
                        Execution.delay(RandomGenerator.nextInt(750, 1250));
                    }
                });
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

    private Set<String> itemsToRetry = new HashSet<>();
    private long nextRetryTime = 0;

    private void collect(List<String> itemNames) {
        if (System.currentTimeMillis() < nextRetryTime) return;

        Player localPlayer = Client.getLocalPlayer();
        if (localPlayer != null && ((double) localPlayer.getCurrentHealth() / localPlayer.getMaximumHealth()) < 0.4) {
            return; // Check player's health before proceeding
        } else if (Backpack.isFull()) {
            manageFullBackpack();
            nextRetryTime = System.currentTimeMillis() + RandomGenerator.nextInt(1000, 2000);
            itemsToRetry.addAll(itemNames);
            return;
        }

        boolean foundItem = false;

        ResultSet<GroundItem> namedItems = GroundItemQuery.newQuery().results();
        for (String itemName : itemNames) {
            Optional<GroundItem> itemOpt = namedItems.stream()
                    .filter(item -> item.getName().equalsIgnoreCase(itemName) && !itemsToRetry.contains(item.getName()))
                    .findFirst();

            if (itemOpt.isPresent()) {
                GroundItem item = itemOpt.get();
                println("Attempting to pick up: " + item.getName());
                if (item.interact("Take")) {
                    Execution.delayUntil(RandomGenerator.nextInt(1820, 1850), () -> !item.validate());
                    itemsToRetry.remove(item.getName());
                    foundItem = true;
                    break;
                } else {
                    itemsToRetry.add(item.getName());
                    nextRetryTime = System.currentTimeMillis() + RandomGenerator.nextInt(1000, 2000);
                    break;
                }
            }
        }

        if (!foundItem && !itemsToRetry.isEmpty()) {
            nextRetryTime = System.currentTimeMillis() + RandomGenerator.nextInt(1000, 2000);
        }
    }

    public void retryCollection() {
        if (!itemsToRetry.isEmpty() && System.currentTimeMillis() >= nextRetryTime) {
            collect(new ArrayList<>(itemsToRetry));
            itemsToRetry.clear();
        }
    }


    private void attemptToPickUpGroundItem(GroundItem groundItem, long endTime) {
        if (Backpack.isFull()) {
            manageFullBackpack();
            return;
        }

        if (groundItem == null) {
            return;
        }

        println("Attempting to take ground item: " + groundItem.getName());
        boolean interactionSuccess = groundItem.interact("Take");
        Execution.delay(RandomGenerator.nextInt(500, 900));

        if (!interactionSuccess) {
            if (System.currentTimeMillis() < endTime) {
                Execution.delay(RandomGenerator.nextInt(1000, 2000));
                attemptToPickUpGroundItem(groundItem, endTime);
            }
            return;
        }
        boolean itemTaken = Execution.delayUntil(RandomGenerator.nextInt(1000, 2000),
                () -> GroundItemQuery.newQuery().name(groundItem.getName()).results().isEmpty());

        if (!itemTaken && System.currentTimeMillis() < endTime) {
            Execution.delay(RandomGenerator.nextInt(1000, 2000));
            attemptToPickUpGroundItem(groundItem, endTime);
        }
    }

    // Method to start the pickup attempt with a 30-second timeout
    private void startPickUpAttempt(GroundItem groundItem) {
        long endTime = System.currentTimeMillis() + 30000; // 30 seconds from now
        attemptToPickUpGroundItem(groundItem, endTime);
    }

    private void manageFullBackpack() {
        println("Backpack is full, attempting to eat food.");
        ResultSet<Item> foodItems = InventoryItemQuery.newQuery(93).option("Eat").results();
        if (!foodItems.isEmpty()) {
            Item food = foodItems.first();
            if (food != null) {
                eatFood(food);
            }
        } else {
            println("No food to eat, retreating and stopping script." + ActionBar.useAbility("War's Retreat Teleport"));
            stopScript();
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
            startPickUpAttempt(groundItem);
        }
    }

    public void setPrayerPointsThreshold(int threshold) {
        prayerPointsThreshold = threshold;
    }

    public void setHealthThreshold(int healthThreshold) {
        SkeletonScript.healthThreshold = healthThreshold;
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
                    Execution.delay(RandomGenerator.nextInt(600, 1500));
                    boolean success = Backpack.interact(prayerOrRestorePot.getName(), "Drink");
                    Execution.delay(RandomGenerator.nextInt(1180, 1220));

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
                Execution.delay(RandomGenerator.nextInt(600, 1500));
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
                            Execution.delay(RandomGenerator.nextInt(1180, 1220));
                            println("Using aggression potion: " + aggressionFlask.getName());
                        } else {
                            println("Failed to use aggression potion: " + aggressionFlask.getName());
                        }
                        Execution.delay(RandomGenerator.nextInt(1180, 1220));
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
            Execution.delay(RandomGenerator.nextInt(600, 1500));
            println("Activating Soul Split:  " + ActionBar.useAbility("Soul Split"));
        }
    }

    private void DeactivateSoulSplit() {
        if (VarManager.getVarbitValue(16779) == 1) {
            Execution.delay(RandomGenerator.nextInt(600, 1500));
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
                    if (teleportToWarOnHealth) {
                        ActionBar.useAbility("War's Retreat Teleport");
                    }
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
        if (getLocalPlayer() != null) {
            if (getLocalPlayer().getAdrenaline() >= 250
                    && ComponentQuery.newQuery(291).spriteId(55524).results().isEmpty()
                    && ActionBar.getCooldownPrecise("Essence of Finality") == 0 && getLocalPlayer().inCombat() && getLocalPlayer().getFollowing() != null
                    && getLocalPlayer().hasTarget()
                    && ActionBar.getCooldownPrecise("Essence of Finality") == 0) {
                int currentNecrosisStacks = VarManager.getVarValue(VarDomainType.PLAYER, 10986);
                if (currentNecrosisStacks >= NecrosisStacksThreshold) {
                    boolean abilityUsed = ActionBar.useAbility("Essence of Finality");
                    if (abilityUsed) {
                        println("Used Death Grasp with " + currentNecrosisStacks + " Necrosis stacks.");
                        Execution.delayUntil(RandomGenerator.nextInt(5000, 10000), () -> ComponentQuery.newQuery(291).spriteId(55524).results().isEmpty());
                    } else {
                        println("Attempted to use Death Grasp, but ability use failed.");
                    }
                }
            }
        }
    }

    private void DeathEssence() { //55480 sprite iD
        if (Essence) {
            if (getLocalPlayer() != null) {

                if (getLocalPlayer().getAdrenaline() >= 350 && getLocalPlayer().getFollowing() != null && getLocalPlayer().getFollowing().getCurrentHealth() >= 500 && ComponentQuery.newQuery(291).spriteId(55480).results().isEmpty() && getLocalPlayer().hasTarget()) {
                    println("Used Death Essence: " + ActionBar.useAbility("Weapon Special Attack"));
                    Execution.delayUntil(RandomGenerator.nextInt(2400, 3000), () -> ComponentQuery.newQuery(291).spriteId(55480).results().isEmpty());
                }
            }
        }
    }

    int RisidualSouls = VarManager.getVarValue(VarDomainType.PLAYER, 11035);
    public static int VolleyOfSoulsThreshold = 5;

    private void volleyOfSouls() {
        if (getLocalPlayer() != null && VarManager.getVarValue(VarDomainType.PLAYER, 11035) >= VolleyOfSoulsThreshold && getLocalPlayer().inCombat() && getLocalPlayer().getFollowing() != null && getLocalPlayer().hasTarget()) {
            int currentResidualSouls = VarManager.getVarValue(VarDomainType.PLAYER, 11035); // Assuming this var tracks the relevant mechanic
            boolean abilityUsed = ActionBar.useAbility("Volley of Souls");
            if (abilityUsed) {
                println("Used Volley of Souls with " + currentResidualSouls + " residual souls.");
                Execution.delayUntil(RandomGenerator.nextInt(2400, 3000), () -> VarManager.getVarValue(VarDomainType.PLAYER, 11035) >= VolleyOfSoulsThreshold);
            } else {
                println("Attempted to use Volley of Souls, but ability use failed.");
            }
        }
    }


    private void Deathmark() {
        if (Self != null) {
            if (VarManager.getVarbitValue(53247) == 0 && getLocalPlayer().getFollowing() != null && getLocalPlayer().getFollowing().getCurrentHealth() >= 500 && ActionBar.getCooldownPrecise("Invoke Death") == 0 && getLocalPlayer().hasTarget()) {
                println("Used Invoke: " + ActionBar.useAbility("Invoke Death"));
                Execution.delayUntil(RandomGenerator.nextInt(2400, 3000), () -> VarManager.getVarbitValue(53247) == 0);
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
        if (!isCrystalMaskActive()) {
            println("Activating Crystal Mask.");
            if (ActionBar.useAbility("Crystal Mask")) {
                println("Crystal Mask activated successfully.");
                Execution.delay(RandomGenerator.nextInt(1000, 2000));
            } else {
                println("Failed to activate Crystal Mask.");
            }
        }
    }


    private void LightFormActivation() {
        int lightFormEnabled = VarManager.getVarbitValue(29066);
        boolean lightFormActive = false;
        if (!lightFormActive && lightFormEnabled != 1) {
            activateLightForm();
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
            Execution.delay(RandomGenerator.nextInt(600, 1500));
            println("Activated Scripture of Jas:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }


    private void deactivateScriptureOfJas() {
        if (VarManager.getVarbitValue(30605) == 1) {
            Execution.delay(RandomGenerator.nextInt(600, 1500));
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
            Execution.delay(RandomGenerator.nextInt(600, 1500));
            println("Activated Scripture of Wen:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }

    private void deactivateScriptureOfWen() {
        if (VarManager.getVarbitValue(30605) == 1) {
            Execution.delay(RandomGenerator.nextInt(600, 1500));
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
            Execution.delay(RandomGenerator.nextInt(600, 1500));
            println("Activated Scripture of Ful:  " + Equipment.interact(Equipment.Slot.POCKET, "Activate/Deactivate"));
        }
    }

    private void deactivateScriptureOfFul() {
        if (VarManager.getVarbitValue(30605) == 1) {
            Execution.delay(RandomGenerator.nextInt(600, 1500));
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
                    Execution.delay(RandomGenerator.nextInt(600, 1500));
                    Backpack.interact(weaponPoisonItem.getName(), "Apply");
                    println(weaponPoisonItem.getName() + "Has been applied");
                    Execution.delay(RandomGenerator.nextInt(600, 700));

                }
            }
        }
    }

    private boolean hasComponentWithSpriteId(int interfaceId) {
        ResultSet<Component> components = ComponentQuery.newQuery(interfaceId)
                .spriteId(43358)
                .results();
        return !components.isEmpty();
    }

    private void manageAncientElven() {
        if (getLocalPlayer() != null) {

            int currentPrayerPoints = LocalPlayer.LOCAL_PLAYER.getPrayerPoints();

            if (currentPrayerPoints < prayerPointsThreshold && !hasComponentWithSpriteId(291)) {
                InventoryItemQuery.newQuery(93).name("Ancient elven ritual shard").results();
                Backpack.interact("Ancient elven ritual shard", "Activate");
                println("Activated Ancient Elven Ritual Shard at: " + currentPrayerPoints + " prayer points.");
                Execution.delayUntil(10000, () -> hasComponentWithSpriteId(284));
            }
        }
    }
    private void WorldHopper() {
        if (nextWorldHopTime == 0) {
            int waitTimeInMs = RandomGenerator.nextInt(minHopIntervalMinutes * 60 * 1000, maxHopIntervalMinutes * 60 * 1000);
            nextWorldHopTime = System.currentTimeMillis() + waitTimeInMs;
            println("Next hop scheduled in " + (waitTimeInMs / 60000) + " minutes.");

            return;
        }

        if (System.currentTimeMillis() >= nextWorldHopTime && !Self.inCombat()) {
            int randomMembersWorldsIndex = RandomGenerator.nextInt(membersWorlds.length);
            HopWorlds(membersWorlds[randomMembersWorldsIndex]);
            println("Hopped to world: " + membersWorlds[randomMembersWorldsIndex]);

            nextWorldHopTime = 0;
        }
    }

    public static long nextWorldHopTime = 0;
    static int minHopIntervalMinutes = 60;
    static int maxHopIntervalMinutes = 180;


    public static void HopWorlds(int world) {
        MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, 7, 93782016);
        boolean WorldPage = Execution.delayUntil(5000, () -> Interfaces.isOpen(1433));
        Execution.delay(RandomGenerator.nextInt(1500, 2000));

        if (WorldPage) {
            Component HopWorldsMenu = ComponentQuery.newQuery(1433).componentIndex(65).results().first();
            if (HopWorldsMenu != null) {
                MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 93913153);
                ScriptConsole.println("Hop Worlds Button Clicked.");
                boolean worldSelectOpen = Execution.delayUntil(5000, () -> Interfaces.isOpen(1587));
                Execution.delay(RandomGenerator.nextInt(1500, 2000));

                if (worldSelectOpen) {
                    MiniMenu.interact(ComponentAction.COMPONENT.getType(), 2, world, 104005640);
                    Execution.delay(RandomGenerator.nextInt(10000, 20000));
                }
            } else {
                ScriptConsole.println("Hop Worlds Button not found.");
            }
        }
    }

    public void saveConfiguration() {
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
        String serializedItemNames = String.join(",", this.targetItemNames);
        this.configuration.addProperty("ItemsToPickup", serializedItemNames);
        String targetNamesSerialized = String.join(",", this.targetNames);
        this.configuration.addProperty("TargetNames", targetNamesSerialized);
        this.configuration.addProperty("VolleyOfSoulsThreshold", String.valueOf(SkeletonScript.VolleyOfSoulsThreshold));
        this.configuration.addProperty("NecrosisStacksThreshold", String.valueOf(SkeletonScript.NecrosisStacksThreshold));
        this.configuration.addProperty("PrayerPointsThreshold", String.valueOf(prayerPointsThreshold));
        this.configuration.addProperty("HealthThreshold", String.valueOf(healthThreshold));
        this.configuration.addProperty("Notepaper", String.valueOf(this.Notepaper));
        String serializedItemNamesForNotepaper = String.join(",", this.itemNamesToUseOnNotepaper);
        this.configuration.addProperty("ItemNamesToUseOnNotepaper", serializedItemNamesForNotepaper);
        this.configuration.addProperty("useLootInterface", String.valueOf(this.useLootInterface));
        this.configuration.addProperty("InteractWithLootAll", String.valueOf(this.InteractWithLootAll));

        this.configuration.save();
    }

    public void loadConfiguration() {
        try {
            this.useLootInterface = Boolean.parseBoolean(this.configuration.getProperty("useLootInterface"));
            this.InteractWithLootAll = Boolean.parseBoolean(this.configuration.getProperty("InteractWithLootAll"));
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
            this.Notepaper = Boolean.parseBoolean(this.configuration.getProperty("Notepaper"));
            this.AttackaTarget = Boolean.parseBoolean(this.configuration.getProperty("AttackaTarget"));
            String serializedItemNames = this.configuration.getProperty("ItemsToPickup");
            if (serializedItemNames != null && !serializedItemNames.isEmpty()) {
                String[] loadedItemNames = serializedItemNames.split(",");
                this.targetItemNames.clear();
                for (String itemName : loadedItemNames) {
                    this.addItemName(itemName);
                }
            }
            String targetNamesSerialized = this.configuration.getProperty("TargetNames");
            if (targetNamesSerialized != null && !targetNamesSerialized.isEmpty()) {
                String[] loadedTargetNames = targetNamesSerialized.split(",");
                this.targetNames.clear();
                for (String targetName : loadedTargetNames) {
                    this.addTargetName(targetName);
                }
            }
            String necrosisThresholdValue = this.configuration.getProperty("NecrosisStacksThreshold");
            if (necrosisThresholdValue != null && !necrosisThresholdValue.isEmpty()) {
                int necrosisThreshold = Integer.parseInt(necrosisThresholdValue);
                if (necrosisThreshold < 0) necrosisThreshold = 0;
                else if (necrosisThreshold > 12) necrosisThreshold = 12;
                SkeletonScript.NecrosisStacksThreshold = necrosisThreshold;
            }

            String volleyThresholdValue = this.configuration.getProperty("VolleyOfSoulsThreshold");
            if (volleyThresholdValue != null && !volleyThresholdValue.isEmpty()) {
                int volleyThreshold = Integer.parseInt(volleyThresholdValue);
                if (volleyThreshold < 0) volleyThreshold = 0;
                SkeletonScript.VolleyOfSoulsThreshold = volleyThreshold;
            }
            String prayerThresholdValue = this.configuration.getProperty("PrayerPointsThreshold");
            if (prayerThresholdValue != null && !prayerThresholdValue.isEmpty()) {
                int prayerThreshold = Integer.parseInt(prayerThresholdValue);
                if (prayerThreshold < 0) prayerThreshold = 0;
                this.setPrayerPointsThreshold(prayerThreshold);
            }

            String healthThresholdValue = this.configuration.getProperty("HealthThreshold");
            if (healthThresholdValue != null && !healthThresholdValue.isEmpty()) {
                int healthThreshold = Integer.parseInt(healthThresholdValue);
                if (healthThreshold < 0) healthThreshold = 0;
                this.setHealthThreshold(healthThreshold);
            }
            String serializedItemNamesForNotepaper = this.configuration.getProperty("ItemNamesToUseOnNotepaper");
            if (serializedItemNamesForNotepaper != null && !serializedItemNamesForNotepaper.isEmpty()) {
                String[] loadedItemNamesForNotepaper = serializedItemNamesForNotepaper.split(",");
                this.itemNamesToUseOnNotepaper.clear();
                this.itemNamesToUseOnNotepaper.addAll(Arrays.asList(loadedItemNamesForNotepaper));
            }
            println("Configuration loaded successfully.");
        } catch (NumberFormatException e) {
            println("Error parsing threshold values. Using defaults.");
            SkeletonScript.NecrosisStacksThreshold = 12; // Default or a logical fallback
            SkeletonScript.VolleyOfSoulsThreshold = 5; // Default or a logical fallback
            SkeletonScript.healthThreshold = 50;
            SkeletonScript.prayerPointsThreshold = 5000;
        } catch (Exception e) {
            println("Failed to load configuration. Using defaults.");
        }
    }
}