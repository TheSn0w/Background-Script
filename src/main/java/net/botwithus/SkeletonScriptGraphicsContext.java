package net.botwithus;

import net.botwithus.rs3.imgui.ImGui;
import net.botwithus.rs3.imgui.ImGuiWindowFlag;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.script.ScriptGraphicsContext;


import java.time.Instant;
import java.util.ArrayList;


public class SkeletonScriptGraphicsContext extends ScriptGraphicsContext {
    public SkeletonScript script;

    private String targetName = "";
    private String selectedItemToUseOnNotepaper = "";
    private String selectedItem = "";
    private String saveSettingsFeedbackMessage = "";
    private long scriptStartTime;
    boolean isScriptRunning = false;
    private String logoutHoursStr = "0";
    private String logoutMinutesStr = "0";
    private Instant startTime;
    private String logoutFeedbackMessage = "";

    private static float RGBToFloat(int rgbValue) {
        return rgbValue / 255.0f;
    }

    public SkeletonScriptGraphicsContext(ScriptConsole scriptConsole, SkeletonScript script) {
        super(scriptConsole);
        this.script = script;
        this.startTime = Instant.now();
        this.scriptStartTime = System.currentTimeMillis();
    }


    @Override
    public void drawSettings() {
        ImGui.PushStyleColor(0, RGBToFloat(173), RGBToFloat(216), RGBToFloat(230), 1.0f); // Button color
        ImGui.PushStyleColor(21, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); // Button color
        ImGui.PushStyleColor(18, RGBToFloat(173), RGBToFloat(216), RGBToFloat(230), 0.5f); // Checkbox Tick color
        ImGui.PushStyleColor(5, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); // Border Colour
        ImGui.PushStyleColor(2, RGBToFloat(0), RGBToFloat(0), RGBToFloat(0), 0.9f); // Background color
        ImGui.PushStyleColor(7, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); // Checkbox Background color
        ImGui.PushStyleColor(11, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); // Header Colour
        ImGui.PushStyleColor(22, RGBToFloat(173), RGBToFloat(216), RGBToFloat(230), 0.5f); // Highlighted button color
        ImGui.PushStyleColor(13, RGBToFloat(255), RGBToFloat(255), RGBToFloat(255), 0.5f); // Highlighted button color
        ImGui.PushStyleColor(27, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); //ImGUI separator Colour
        ImGui.PushStyleColor(30, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); //Corner Extender colour
        ImGui.PushStyleColor(31, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); //Corner Extender colour
        ImGui.PushStyleColor(32, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); //Corner Extender colour
        ImGui.PushStyleColor(33, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); //Corner Extender colour
        ImGui.PushStyleColor(3, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 0.5f); //ChildBackground


        ImGui.SetWindowSize(200.f, 200.f);
        if (ImGui.Begin("Snows Utility Suite", ImGuiWindowFlag.None.getValue())) {
            ImGui.PushStyleVar(1, 10.f, 5f);
            ImGui.PushStyleVar(2, 10.f, 5f); //spacing between side of window and checkbox
            ImGui.PushStyleVar(3, 10.f, 5f);
            ImGui.PushStyleVar(4, 10.f, 10f);
            ImGui.PushStyleVar(5, 10.f, 5f);
            ImGui.PushStyleVar(6, 10.f, 5f);
            ImGui.PushStyleVar(7, 10.f, 5f);
            ImGui.PushStyleVar(8, 10.f, 5f); //spacing between seperator and text
            ImGui.PushStyleVar(9, 10.f, 5f);
            ImGui.PushStyleVar(10, 10.f, 5f);
            ImGui.PushStyleVar(11, 10.f, 5f); // button sizes
            ImGui.PushStyleVar(12, 10.f, 5f);
            ImGui.PushStyleVar(13, 10.f, 5f);
            ImGui.PushStyleVar(14, 10.f, 5f); // spaces between options ontop such as overlays, debug etc
            ImGui.PushStyleVar(15, 10.f, 5f); // spacing between Text/tabs and checkboxes
            ImGui.PushStyleVar(16, 10.f, 5f);
            ImGui.PushStyleVar(17, 10.f, 5f);
            if (ImGui.BeginTabBar("Options", ImGuiWindowFlag.None.getValue())) {
                if (ImGui.BeginTabItem("Item Toggles", ImGuiWindowFlag.None.getValue())) {
                    if (isScriptRunning) {
                        if (ImGui.Button("Stop Script")) {
                            script.stopScript();
                            isScriptRunning = false;
                        }
                    } else {
                        if (ImGui.Button("Start Script")) {
                            script.startScript();
                            isScriptRunning = true;
                        }
                    }
                    ImGui.SameLine();
                    if (ImGui.Button("Save Settings")) {
                        try {
                            script.saveConfiguration();
                            saveSettingsFeedbackMessage = "Settings saved successfully.";
                        } catch (Exception e) {
                            saveSettingsFeedbackMessage = "Failed to save settings: " + e.getMessage();
                        }
                    }

                    if (!saveSettingsFeedbackMessage.isEmpty()) {
                        ImGui.Text(saveSettingsFeedbackMessage);
                    }

                    ImGui.SeparatorText("Food/Prayer Options");
                    script.useSaraBrew = ImGui.Checkbox("Drink Saradomin Brew", script.useSaraBrew);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have in Backpack");
                    }
                    script.useSaraBrewandBlubber = ImGui.Checkbox("Drink Saradomin Brew and Blubber", script.useSaraBrewandBlubber);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have in Backpack");
                    }
                    script.eatFood = ImGui.Checkbox("Eat Food", script.eatFood);
                    ImGui.SetItemWidth(150.0F);
                    ImGui.SameLine();
                    SkeletonScript.healthThreshold = ImGui.InputInt("Health % Threshold", SkeletonScript.healthThreshold);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Health % to eat at");
                    }
                    if (SkeletonScript.healthThreshold < 0) {
                        SkeletonScript.healthThreshold = 0;
                    } else if (SkeletonScript.healthThreshold > 100) {
                        SkeletonScript.healthThreshold = 100;
                    }
                    script.useprayer = ImGui.Checkbox("Use Prayer/Restore Pots/Flasks", script.useprayer);
                    ImGui.SetItemWidth(150.0F);
                    ImGui.SameLine();
                    SkeletonScript.prayerPointsThreshold = ImGui.InputInt("Prayer Point Threshold", SkeletonScript.prayerPointsThreshold);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Prayer points to drink at. `5000 = 500`");
                    }
                    if (SkeletonScript.prayerPointsThreshold < 0) {
                        SkeletonScript.prayerPointsThreshold = 0;
                    } else if (SkeletonScript.prayerPointsThreshold > 9900) {
                        SkeletonScript.prayerPointsThreshold = 9900;
                    }
                    ImGui.SeparatorText("Offensive Options");

                    script.useEssenceOfFinality = ImGui.Checkbox("Use Essence of Finality", script.useEssenceOfFinality);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Do not have Finger of Death in Revo bar.");
                    }
                    ImGui.SetItemWidth(110.0F);
                    ImGui.SameLine();
                    SkeletonScript.NecrosisStacksThreshold = ImGui.InputInt("Necrosis Stacks Threshold (0-12)", SkeletonScript.NecrosisStacksThreshold);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Stacks to cast at");
                    }
                    if (SkeletonScript.NecrosisStacksThreshold < 0) {
                        SkeletonScript.NecrosisStacksThreshold = 0;
                    } else if (SkeletonScript.NecrosisStacksThreshold > 12) {
                        SkeletonScript.NecrosisStacksThreshold = 12;
                    }
                    script.useVolleyofSouls = ImGui.Checkbox("Use Volley of Souls", script.useVolleyofSouls);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Do not have Volley on Revo bar.");
                    }
                    ImGui.SetItemWidth(110.0F);
                    ImGui.SameLine();
                    SkeletonScript.VolleyOfSoulsThreshold = ImGui.InputInt("Volley of Souls Threshold (0-5)", SkeletonScript.VolleyOfSoulsThreshold);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Stacks to cast at");
                    }
                    if (SkeletonScript.VolleyOfSoulsThreshold < 0) {
                        SkeletonScript.VolleyOfSoulsThreshold = 0;
                    } else if (SkeletonScript.VolleyOfSoulsThreshold > 5) {
                        SkeletonScript.VolleyOfSoulsThreshold = 5;
                    }
                    script.enableGhost = ImGui.Checkbox("Enable Constant Undead Army", script.enableGhost);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("will summon undead army if not preset");
                    }
                    script.Essence = ImGui.Checkbox("OmniGuard Special Attack", script.Essence);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }

                    script.UseVulnBomb = ImGui.Checkbox("Use Vulnerability Bomb", script.UseVulnBomb);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    script.UseSmokeBomb = ImGui.Checkbox("Use Smoke Cloud", script.UseSmokeBomb);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    script.InvokeDeath = ImGui.Checkbox("Use Invoke Death", script.InvokeDeath);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    ImGui.SeparatorText("Defensive Options");
                    script.useoverload = ImGui.Checkbox("Use Overloads", script.useoverload);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have in Backpack");
                    }
                    script.usedarkness = ImGui.Checkbox("Use Darkness", script.usedarkness);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    script.UseSoulSplit = ImGui.Checkbox("Use Soul Split in Combat", script.UseSoulSplit);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    script.quickprayer = ImGui.Checkbox("Use Quick Prayer 1 in Combat", script.quickprayer);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    script.useExcalibur = ImGui.Checkbox("Use Excalibur", script.useExcalibur);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have in Backpack");
                    }
                    script.useAncientElven = ImGui.Checkbox("Use Ancient Elven Ritual Shard", script.useAncientElven);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have in Backpack");
                    }
                    script.useWeaponPoison = ImGui.Checkbox("Use Weapon Poison", script.useWeaponPoison);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have in Backpack");
                    }
                    ImGui.SeparatorText("Teleport Options");
                    script.teleportToWarOnHealth = ImGui.Checkbox("Teleport to War's Retreat on Low Health", script.teleportToWarOnHealth);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Will teleport to War's Retreat if health falls below Threshold.");
                    }
                    ImGui.SeparatorText("Scriptures");
                    boolean tempUseScriptureOfWen = script.UseScriptureOfWen;
                    if (ImGui.Checkbox("Use Scripture of Wen in Combat", tempUseScriptureOfWen)) {
                        if (ImGui.IsItemHovered()) {
                            ImGui.SetTooltip("in Combat only.");
                        }
                        script.UseScriptureOfWen = true;
                        script.UseScriptureOfJas = false;
                        script.UseScriptureOfFul = false;
                    } else if (script.UseScriptureOfWen) {
                        script.UseScriptureOfWen = false;
                    }

// Use Scripture of Jas
                    boolean tempUseScriptureOfJas = script.UseScriptureOfJas;
                    if (ImGui.Checkbox("Use Scripture of Jas in Combat", tempUseScriptureOfJas)) {
                        if (ImGui.IsItemHovered()) {
                            ImGui.SetTooltip("in Combat only.");
                        }
                        script.UseScriptureOfWen = false;
                        script.UseScriptureOfJas = true;
                        script.UseScriptureOfFul = false;
                    } else if (script.UseScriptureOfJas) {
                        script.UseScriptureOfJas = false;
                    }

// Use Scripture of Ful
                    boolean tempUseScriptureOfFul = script.UseScriptureOfFul;
                    if (ImGui.Checkbox("Use Scripture of Ful in Combat", tempUseScriptureOfFul)) {
                        if (ImGui.IsItemHovered()) {
                            ImGui.SetTooltip("in Combat only.");
                        }
                        script.UseScriptureOfWen = false;
                        script.UseScriptureOfJas = false;
                        script.UseScriptureOfFul = true;
                    } else if (script.UseScriptureOfFul) {
                        script.UseScriptureOfFul = false;
                    }
                    long elapsedTimeMillis = System.currentTimeMillis() - this.scriptStartTime;
                    long elapsedSeconds = elapsedTimeMillis / 1000L;
                    long hours = elapsedSeconds / 3600L;
                    long minutes = elapsedSeconds % 3600L / 60L;
                    long seconds = elapsedSeconds % 60L;
                    String displayTimeRunning = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    ImGui.SeparatorText("Time Running  " + displayTimeRunning);

                    ImGui.EndTabItem();
                }
                if (ImGui.BeginTabItem("Loot Options", ImGuiWindowFlag.None.getValue())) {
                    ImGui.SeparatorText("Loot Options");
                    boolean tempUseLoot = script.useLoot;
                    script.useLoot = ImGui.Checkbox("Use Single Loot", script.useLoot);
                    if (script.useLoot && script.useLoot != tempUseLoot) {
                        script.useLootInterface = false;
                        script.InteractWithLootAll = false;
                    }
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("All options in area loot settings must be turned off, will pick up items under a loot beam automatically without being on list.");
                    }

                    ImGui.SameLine();

// Use Loot Interface Checkbox
                    boolean tempUseLootInterface = script.useLootInterface;
                    script.useLootInterface = ImGui.Checkbox("Use Loot Interface", script.useLootInterface);
                    if (script.useLootInterface && script.useLootInterface != tempUseLootInterface) {
                        script.useLoot = false;
                        script.InteractWithLootAll = false;
                    }
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Will open loot interface to loot items on the list below.");
                    }

                    ImGui.SameLine();

// Loot All Checkbox
                    boolean tempInteractWithLootAll = script.InteractWithLootAll;
                    script.InteractWithLootAll = ImGui.Checkbox("Loot All", script.InteractWithLootAll);
                    if (script.InteractWithLootAll && script.InteractWithLootAll != tempInteractWithLootAll) {
                        script.useLoot = false;
                        script.useLootInterface = false;
                    }
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Will interact with Loot All button in loot interface for ALL loot, make sure no loot remains as it will keep interacting if loot found.");
                    }

                    ImGui.SeparatorText("Items to Pickup");

                    this.selectedItem = ImGui.InputText("Item name", selectedItem);

                    if (ImGui.Button("Add Item") && !selectedItem.isEmpty()) {
                        if (!script.getTargetItemNames().contains(selectedItem)) {
                            script.println("Adding \"" + selectedItem + "\" to target items.");
                            script.addItemName(selectedItem);
                            this.selectedItem = "";
                        }
                    }
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Can be part of the name, case insensitive. e.g. `char` will match all charms.");
                    }
                    ImGui.SameLine();
                    script.buryBones = ImGui.Checkbox("Bury Bones/Ashes", script.buryBones);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Will bury bones/Scatter ashes.");
                    }


                    if (!script.getTargetItemNames().isEmpty()) {
                        if (ImGui.BeginChild("Items List", 0, 100, true, 0)) {
                            int itemsPerLine = 6;
                            int itemCount = 0; // Counter for items displayed so far

                            for (String itemName : new ArrayList<>(script.getTargetItemNames())) {
                                if (itemCount % itemsPerLine != 0) {
                                    ImGui.SameLine();
                                }

                                if (ImGui.Button(itemName)) {
                                    script.println("Removing \"" + itemName + "\" from target items.");
                                    script.removeItemName(itemName);
                                    // Depending on your logic, you may want to break here,
                                    // but be cautious of concurrent modification issues.
                                }

                                if (ImGui.IsItemHovered()) {
                                    ImGui.SetTooltip("Click to remove");
                                }

                                itemCount++;
                            }
                        }
                        ImGui.EndChild();
                    }
                    ImGui.SeparatorText("Target Options");
                    script.AttackaTarget = ImGui.Checkbox("Attack a Target", script.AttackaTarget);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("If enabled but no target input, will attack all targets nearby");
                    }
                    ImGui.SeparatorText("Attack Options");
                    ImGui.SetItemWidth(200.0F);
                    this.targetName = ImGui.InputText("Target name", this.targetName);


// Button to add the target name to the list
                    if (ImGui.Button("Add Target") && !this.targetName.isEmpty()) {
                        script.addTargetName(this.targetName);
                        this.targetName = ""; // Clear the field after adding
                    }

                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Enter the name of the target to attack. Case-insensitive, partial names allowed.");
                    }

// Display the list of targets with options to remove them
                    if (!script.getTargetNames().isEmpty()) {
                        if (ImGui.BeginChild("Targets List", 0, 100, true, 0)) {
                            boolean firstItem = true;
                            for (String targetName : new ArrayList<>(script.getTargetNames())) {
                                if (!firstItem) {
                                    ImGui.SameLine();
                                }
                                firstItem = false;

                                if (ImGui.Button(targetName)) {
                                    script.removeTargetName(targetName);
                                    break;
                                }
                                if (ImGui.IsItemHovered()) {
                                    ImGui.SetTooltip("Click to remove this target");
                                }
                            }
                        }
                        ImGui.EndChild();
                    }
                    // ImGui Separator for visual distinction
                    ImGui.Separator();
                    ImGui.SeparatorText("Use Items on Magic Notepaper");
                    ImGui.SetItemWidth(250f);
                    script.Notepaper = ImGui.Checkbox("Use Magic Notepaper", script.Notepaper);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Case SENSITIVE, e.g. `Magic Notepaper`");
                    }
                    this.selectedItemToUseOnNotepaper = ImGui.InputText("Item name to use on Notepaper", this.selectedItemToUseOnNotepaper);

// Button to add the item to the list
                    if (ImGui.Button("Add Item to Notepaper List") && !this.selectedItemToUseOnNotepaper.isEmpty()) {
                        if (!script.getItemNamesToUseOnNotepaper().contains(this.selectedItemToUseOnNotepaper)) {
                            script.addItemNameToUseOnNotepaper(this.selectedItemToUseOnNotepaper);
                            this.selectedItemToUseOnNotepaper = ""; // Clear the input field after adding
                        }
                    }

// Display the list of items to use on Magic Notepaper within a scrollable child window
                    if (!script.getItemNamesToUseOnNotepaper().isEmpty()) {
                        if (ImGui.BeginChild("Items to Use on Notepaper List", 0, 100, true, ImGuiWindowFlag.None.getValue())) {
                            int itemCount = 0;
                            for (String itemName : new ArrayList<>(script.getItemNamesToUseOnNotepaper())) {
                                if (itemCount % 6 != 0) {
                                    ImGui.SameLine();
                                }
                                itemCount++;

                                if (ImGui.Button(itemName)) {
                                    script.removeItemNameToUseOnNotepaper(itemName);
                                    break;
                                }

                                if (ImGui.IsItemHovered()) {
                                    ImGui.SetTooltip("Click to remove this item from the list");
                                }
                            }
                        }
                        ImGui.EndChild();
                    }
                    ImGui.EndTabItem();
                }
                if (ImGui.BeginTabItem("Logout Timer Setup", ImGuiWindowFlag.None.getValue())) {
                    script.Logout = ImGui.Checkbox("Use Logout", script.Logout);
                    ImGui.SeparatorText("Make sure the Script is started before setting logout timer");
                    ImGui.PopStyleColor(1);
                    ImGui.SetItemWidth(50);
                    logoutHoursStr = ImGui.InputText("Hours until logout", logoutHoursStr);
                    ImGui.SetItemWidth(50);
                    logoutMinutesStr = ImGui.InputText("Minutes until logout", logoutMinutesStr);
                    if (ImGui.Button("Set Logout Timer")) {
                        try {
                            int hours = Integer.parseInt(logoutHoursStr.trim());
                            int minutes = Integer.parseInt(logoutMinutesStr.trim());
                            if (hours < 0 || minutes < 0) {
                                logoutFeedbackMessage = "Please enter a valid positive number for hours and minutes.";
                            } else {
                                long currentTimeMillis = System.currentTimeMillis();
                                long calculatedTargetLogoutTimeMillis = currentTimeMillis + hours * 3600000L + minutes * 60000L;
                                script.setTargetLogoutTimeMillis(calculatedTargetLogoutTimeMillis);
                                logoutFeedbackMessage = String.format("Logout timer set for %d hours and %d minutes from now.", hours, minutes);
                            }
                        } catch (NumberFormatException e) {
                            logoutFeedbackMessage = "Please enter a valid number for hours and minutes.";
                        }
                    }
                    if (!logoutFeedbackMessage.isEmpty()) {
                        ImGui.Text(logoutFeedbackMessage);
                    }

                    // Display Countdown until Logout
                    long remainingTimeMillis = script.getTargetLogoutTimeMillis() - System.currentTimeMillis();
                    if (remainingTimeMillis > 0) {
                        long hours = remainingTimeMillis / 3600000;
                        long minutes = (remainingTimeMillis % 3600000) / 60000;
                        long seconds = (remainingTimeMillis % 60000) / 1000;
                        String countdownMessage = String.format("Time until logout: %02d:%02d:%02d", hours, minutes, seconds);
                        ImGui.Text(countdownMessage);
                    } else {
                        ImGui.SeparatorText("Logout timer is not set or has expired.");
                    }

                    ImGui.EndTabItem();
                }
                if (ImGui.BeginTabItem("World Hopper", ImGuiWindowFlag.None.getValue())) {
                    script.HopWorlds = ImGui.Checkbox("Use World Hopper", script.HopWorlds);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Will not world hop in certain scenarios, e.g. in combat, in a dungeon, etc.");
                    }
                    long timeRemaining = SkeletonScript.nextWorldHopTime - System.currentTimeMillis();
                    if(timeRemaining > 0) {
                        String remainingTimeFormatted = formatTimeRemaining(timeRemaining);
                        ImGui.Text("Next hop in: " + remainingTimeFormatted);
                    } else {
                        ImGui.Text("Ready to hop worlds...");
                    }
                    // Inside the drawSettings() method
                    ImGui.Text("World Hop Settings:");
                    SkeletonScript.minHopIntervalMinutes = ImGui.InputInt("Min Hop Interval (Minutes)", SkeletonScript.minHopIntervalMinutes);
                    if (SkeletonScript.minHopIntervalMinutes < 1) { // Minimum 1 minute
                        SkeletonScript.minHopIntervalMinutes = 1;
                    } else if (SkeletonScript.minHopIntervalMinutes > SkeletonScript.maxHopIntervalMinutes) { // Ensure min is not more than max
                        SkeletonScript.minHopIntervalMinutes = SkeletonScript.maxHopIntervalMinutes;
                    }

                    SkeletonScript.maxHopIntervalMinutes = ImGui.InputInt("Max Hop Interval (Minutes)", SkeletonScript.maxHopIntervalMinutes);
                    if (SkeletonScript.maxHopIntervalMinutes < SkeletonScript.minHopIntervalMinutes) { // Ensure max is not less than min
                        SkeletonScript.maxHopIntervalMinutes = SkeletonScript.minHopIntervalMinutes;
                    } else if (SkeletonScript.maxHopIntervalMinutes > 300) { // Set an upper limit, for example, 300 minutes
                        SkeletonScript.maxHopIntervalMinutes = 300;
                    }

                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Set the minimum and maximum intervals for hopping worlds. The script will select a random time between these two values for each hop.");
                    }
                    ImGui.EndTabItem();
                }
                if (ImGui.BeginTabItem("Misc Options", ImGuiWindowFlag.None.getValue())) {
                    script.useLightForm = ImGui.Checkbox("Use Light Form", script.useLightForm);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    script.useCrystalMask = ImGui.Checkbox("Use Crystal Mask", script.useCrystalMask);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    script.useSuperheatForm = ImGui.Checkbox("Use Superheat Form", script.useSuperheatForm);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Have on Action Bar");
                    }
                    script.useNecromancyPotion = ImGui.Checkbox("Use Necromancy Potion", script.useNecromancyPotion);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Use any variant of >necromancy< potions/flasks etc, doesnt need to be on action bar");
                    }
                    script.usePenance = ImGui.Checkbox("Use Powder of Penance", script.usePenance);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Doesn't need to be on action bar");
                    }
                    script.useProtection = ImGui.Checkbox("Use Powder of Protection", script.useProtection);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Doesn't need to be on action bar");
                    }
                    script.useAntifire = ImGui.Checkbox("Use Antifire variant", script.useAntifire);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Doesn't need to be on action bar");
                    }
                    script.useaggression = ImGui.Checkbox("Use Aggression Flask", script.useaggression);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Doesn't need to be on action bar");
                    }
                    script.useHunter = ImGui.Checkbox("Use Extreme Hunter Potion", script.useHunter);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Doesn't need to be on action bar");
                    }
                    script.usedivination = ImGui.Checkbox("Use Extreme Divination Potion", script.usedivination);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Doesn't need to be on action bar");
                    }
                    script.usecooking = ImGui.Checkbox("Use Extreme Cooking Potion", script.usecooking);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Doesn't need to be on action bar");
                    }
                    script.useCharming = ImGui.Checkbox("Use Charming Potion", script.useCharming);
                    if (ImGui.IsItemHovered()) {
                        ImGui.SetTooltip("Doesn't need to be on action bar");
                    }
                    ImGui.SeparatorText("Dummy Options");
                    ImGui.PushStyleColor(0, RGBToFloat(134), RGBToFloat(136), RGBToFloat(138), 1.0f); //text colour
                    ImGui.Text("Go to a Remote Location where nobody else has a chance to deploy a dummy + Place on action bar");
                    ImGui.PopStyleColor(1);
                    script.useMeleeDummy = ImGui.Checkbox("Use Melee Dummy", script.useMeleeDummy);
                    script.useRangedDummy = ImGui.Checkbox("Use Ranged Dummy", script.useRangedDummy);
                    script.useMagicDummy = ImGui.Checkbox("Use Magic Dummy", script.useMagicDummy);
                    script.useAgilityDummy = ImGui.Checkbox("Use Agility Dummy", script.useAgilityDummy);
                    script.useThievingDummy = ImGui.Checkbox("Use Thieving Dummy", script.useThievingDummy);
                    ImGui.SeparatorText("Miscellaneous Options");
                    ImGui.EndTabItem();
                }

                ImGui.EndTabBar();
                ImGui.End();
            }

        }
        ImGui.PopStyleVar(100);
        ImGui.PopStyleColor(100);
    }
    @Override
    public void drawOverlay() {
        super.drawOverlay();
    }

    private String formatTimeRemaining(long millis) {
        long minutes = (millis / 1000) / 60;
        long seconds = (millis / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
// ImGui.Text("Use any variant of >necromancy< potions/flasks etc");