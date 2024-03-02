package net.botwithus;

import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.imgui.ImGui;
import net.botwithus.rs3.imgui.ImGuiWindowFlag;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.script.ScriptGraphicsContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class SkeletonScriptGraphicsContext extends ScriptGraphicsContext {
    private SkeletonScript script;
    private String logoutTimeStr = ""; // HH:MM format


    private long scriptStartTime;
    boolean isScriptRunning = false;
    private String logoutHoursStr = "0"; // Default to 0 hours
    private String logoutMinutesStr = "0"; // Default to 0 minutes
    private long targetLogoutTimeMillis = 0;

    private Instant startTime;
    private int startingXP;
    int startingAttackXP = Skills.ATTACK.getSkill().getExperience();
    int startingDefenseXP = Skills.DEFENSE.getSkill().getExperience();
    int startingStrengthXP = Skills.STRENGTH.getSkill().getExperience();
    int startingConstitutionXP = Skills.CONSTITUTION.getSkill().getExperience();
    int startingRangedXP = Skills.RANGED.getSkill().getExperience();
    int startingPrayerXP = Skills.PRAYER.getSkill().getExperience();
    int startingMagicXP = Skills.MAGIC.getSkill().getExperience();
    int startingCookingXP = Skills.COOKING.getSkill().getExperience();
    int startingWoodcuttingXP = Skills.WOODCUTTING.getSkill().getExperience();
    int startingFletchingXP = Skills.FLETCHING.getSkill().getExperience();
    int startingFishingXP = Skills.FISHING.getSkill().getExperience();
    int startingFiremakingXP = Skills.FIREMAKING.getSkill().getExperience();
    int startingCraftingXP = Skills.CRAFTING.getSkill().getExperience();
    int startingSmithingXP = Skills.SMITHING.getSkill().getExperience();
    int startingMiningXP = Skills.MINING.getSkill().getExperience();
    int startingHerbloreXP = Skills.HERBLORE.getSkill().getExperience();
    int startingAgilityXP = Skills.AGILITY.getSkill().getExperience();
    int startingThievingXP = Skills.THIEVING.getSkill().getExperience();
    int startingSlayerXP = Skills.SLAYER.getSkill().getExperience();
    int startingFarmingXP = Skills.FARMING.getSkill().getExperience();
    int startingRunecraftingXP = Skills.RUNECRAFTING.getSkill().getExperience();
    int startingHunterXP = Skills.HUNTER.getSkill().getExperience();
    int startingConstructionXP = Skills.CONSTRUCTION.getSkill().getExperience();
    int startingSummoningXP = Skills.SUMMONING.getSkill().getExperience();
    int startingDungeoneeringXP = Skills.DUNGEONEERING.getSkill().getExperience();
    int startingDivinationXP = Skills.DIVINATION.getSkill().getExperience();
    int startingInventionXP = Skills.INVENTION.getSkill().getExperience();
    int startingArchaeologyXP = Skills.ARCHAEOLOGY.getSkill().getExperience();
    int startingNecromancyXP = Skills.NECROMANCY.getSkill().getExperience();
    private final int startingAttackLevel;
    private final int startingStrengthLevel;
    private final int startingDefenseLevel;
    private final int startingRangedLevel;
    private final int startingPrayerLevel;
    private final int startingMagicLevel;
    private final int startingRunecraftingLevel;
    private final int startingConstructionLevel;
    private final int startingDungeoneeringLevel;
    private final int startingArchaeologyLevel;
    private final int startingConstitutionLevel;
    private final int startingAgilityLevel;
    private final int startingHerbloreLevel;
    private final int startingThievingLevel;
    private final int startingCraftingLevel;
    private final int startingFletchingLevel;
    private final int startingSlayerLevel;
    private final int startingHunterLevel;
    private final int startingDivinationLevel;
    private final int startingNecromancyLevel;
    private final int startingMiningLevel;
    private final int startingSmithingLevel;
    private final int startingFishingLevel;
    private final int startingCookingLevel;
    private final int startingFiremakingLevel;
    private final int startingWoodcuttingLevel;
    private final int startingFarmingLevel;
    private final int startingSummoningLevel;
    private final int startingInventionLevel;
    private boolean showAttackStats = false;
    private boolean showStrengthStats = false;
    private boolean showDefenseStats = false;
    private boolean showRangedStats = false;
    private boolean showPrayerStats = false;
    private boolean showMagicStats = false;
    private boolean showRunecraftingStats = false;
    private boolean showConstructionStats = false;
    private boolean showDungeoneeringStats = false;
    private boolean showArchaeologyStats = false;
    private boolean showConstitutionStats = false;
    private boolean showAgilityStats = false;
    private boolean showHerbloreStats = false;
    private boolean showThievingStats = false;
    private boolean showCraftingStats = false;
    private boolean showFletchingStats = false;
    private boolean showSlayerStats = false;
    private boolean showHunterStats = false;
    private boolean showDivinationStats = false;
    private boolean showNecromancyStats = false;
    private boolean showMiningStats = false;
    private boolean showSmithingStats = false;
    private boolean showFishingStats = false;
    private boolean showCookingStats = false;
    private boolean showFiremakingStats = false;
    private boolean showWoodcuttingStats = false;
    private boolean showFarmingStats = false;
    private boolean showSummoningStats = false;
    private boolean showInventionStats = false;


    private String healthFeedbackMessage = "";
    private String prayerFeedbackMessage = "";
    private String logoutFeedbackMessage = "";
    private String prayerPointsThresholdStr = "1000";
    private String healthThresholdStr = "50";
    private static float RGBToFloat(int rgbValue) {
        return rgbValue / 255.0f;
    }

    public SkeletonScriptGraphicsContext(ScriptConsole scriptConsole, SkeletonScript script) {
        super(scriptConsole);
        this.script = script;
        this.startTime = Instant.now();
        this.scriptStartTime = System.currentTimeMillis();
        this.startingRunecraftingLevel = script.getStartingRunecraftingLevel();
        this.startingAttackLevel = script.getStartingAttackLevel();
        this.startingStrengthLevel = script.getStartingStrengthLevel();
        this.startingDefenseLevel = script.getStartingDefenseLevel();
        this.startingRangedLevel = script.getStartingRangedLevel();
        this.startingPrayerLevel = script.getStartingPrayerLevel();
        this.startingMagicLevel = script.getStartingMagicLevel();
        this.startingConstructionLevel = script.getStartingConstructionLevel();
        this.startingDungeoneeringLevel = script.getStartingDungeoneeringLevel();
        this.startingArchaeologyLevel = script.getStartingArchaeologyLevel();
        this.startingConstitutionLevel = script.getStartingConstitutionLevel();
        this.startingAgilityLevel = script.getStartingAgilityLevel();
        this.startingHerbloreLevel = script.getStartingHerbloreLevel();
        this.startingThievingLevel = script.getStartingThievingLevel();
        this.startingCraftingLevel = script.getStartingCraftingLevel();
        this.startingFletchingLevel = script.getStartingFletchingLevel();
        this.startingSlayerLevel = script.getStartingSlayerLevel();
        this.startingHunterLevel = script.getStartingHunterLevel();
        this.startingDivinationLevel = script.getStartingDivinationLevel();
        this.startingNecromancyLevel = script.getStartingNecromancyLevel();
        this.startingMiningLevel = script.getStartingMiningLevel();
        this.startingSmithingLevel = script.getStartingSmithingLevel();
        this.startingFishingLevel = script.getStartingFishingLevel();
        this.startingCookingLevel = script.getStartingCookingLevel();
        this.startingFiremakingLevel = script.getStartingFiremakingLevel();
        this.startingWoodcuttingLevel = script.getStartingWoodcuttingLevel();
        this.startingFarmingLevel = script.getStartingFarmingLevel();
        this.startingSummoningLevel = script.getStartingSummoningLevel();
        this.startingInventionLevel = script.getStartingInventionLevel();
    }


    @Override
    public void drawSettings() {
        ImGui.PushStyleColor(21, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); // Button color
        ImGui.PushStyleColor(18, RGBToFloat(255), RGBToFloat(255), RGBToFloat(255), 1.0f); // Checkbox Tick color
        ImGui.PushStyleColor(5, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); // Border Colour
        ImGui.PushStyleColor(2, RGBToFloat(0), RGBToFloat(0), RGBToFloat(0), 0.9f); // Background color
        ImGui.PushStyleColor(7, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); // Checkbox Background color
        ImGui.PushStyleColor(11, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); // Header Colour
        ImGui.PushStyleColor(22, RGBToFloat(64), RGBToFloat(67), RGBToFloat(67), 1.0f); // Highlighted button color
        ImGui.PushStyleColor(27, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); //ImGUI separator Colour
        ImGui.PushStyleColor(30, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); //Corner Extender colour
        ImGui.PushStyleColor(31, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); //Corner Extender colour
        ImGui.PushStyleColor(32, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); //Corner Extender colour
        ImGui.PushStyleColor(33, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); //Corner Extender colour
        ImGui.PushStyleColor(34, RGBToFloat(47), RGBToFloat(79), RGBToFloat(79), 1.0f); //Corner Extender colour


        ImGui.SetWindowSize(200.f, 200.f);
        if (ImGui.Begin("Snows Utility Suite", ImGuiWindowFlag.None.getValue())) {
            ImGui.PushStyleVar(1, 10.f, 5f);
            ImGui.PushStyleVar(2, 10.f, 5f); //spacing between side of window and checkbox
            ImGui.PushStyleVar(3, 10.f, 5f);
            ImGui.PushStyleVar(4, 10.f, 5f);
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
                    ImGui.SeparatorText("Food/Prayer Options");
                    script.useSaraBrew = ImGui.Checkbox("Drink Saradomin Brew", script.useSaraBrew);
                    script.useSaraBrewandBlubber = ImGui.Checkbox("Drink Saradomin Brew and Blubber", script.useSaraBrewandBlubber);
                    script.eatFood = ImGui.Checkbox("Eat Food", script.eatFood);
                    ImGui.SetItemWidth(40);
                    healthThresholdStr = ImGui.InputText("Health Threshold (%)", healthThresholdStr);
                    ImGui.SameLine();
                    if (ImGui.Button("Set Health Threshold")) {
                        try {
                            int newHealthThreshold = Integer.parseInt(healthThresholdStr.trim());
                            if (newHealthThreshold >= 0 && newHealthThreshold <= 100) {
                                script.setHealthThreshold(newHealthThreshold);
                                healthFeedbackMessage = "Health Threshold updated successfully to: " + newHealthThreshold;
                            } else {
                                healthFeedbackMessage = "Entered value must be within 0-100.";
                            }
                        } catch (NumberFormatException e) {
                            healthFeedbackMessage = "Invalid number format for Health Threshold.";
                        }
                    }
                    if (!healthFeedbackMessage.isEmpty()) {
                        ImGui.Text(healthFeedbackMessage);
                    }
                    script.useprayer = ImGui.Checkbox("Use Prayer/Restore Pots/Flasks", script.useprayer);
                    ImGui.SetItemWidth(60);
                    prayerPointsThresholdStr = ImGui.InputText("Prayer Points Threshold", prayerPointsThresholdStr);
                    ImGui.SameLine();

                    if (ImGui.Button("Set Prayer Threshold")) {
                        try {
                            int newThreshold = Integer.parseInt(prayerPointsThresholdStr.trim());
                            if (newThreshold >= 0) {
                                script.setPrayerPointsThreshold(newThreshold);
                                prayerFeedbackMessage = "Threshold updated successfully to: " + newThreshold;
                            } else {
                                prayerFeedbackMessage = "Entered value must be non-negative.";
                            }
                        } catch (NumberFormatException e) {
                            prayerFeedbackMessage = "Invalid number format.";
                        }
                    }
                    if (!prayerFeedbackMessage.isEmpty()) {
                        ImGui.Text(prayerFeedbackMessage);
                    }
                    ImGui.SeparatorText("Combat Options");
                    ImGui.PushStyleColor(0, RGBToFloat(134), RGBToFloat(136), RGBToFloat(138), 1.0f); //text colour
                    ImGui.Text("Have on action bar");
                    ImGui.PopStyleColor(1);
                    script.UseSoulSplit = ImGui.Checkbox("Use Soul Split in Combat", script.UseSoulSplit);
                    script.UseVulnBomb = ImGui.Checkbox("Use Vulnerability Bomb", script.UseVulnBomb);
                    script.UseSmokeBomb = ImGui.Checkbox("Use Smoke Cloud", script.UseSmokeBomb);
                    script.InvokeDeath = ImGui.Checkbox("Use Invoke Death", script.InvokeDeath);
                    script.useoverload = ImGui.Checkbox("Use Overloads", script.useoverload);
                    script.useaggression = ImGui.Checkbox("Use Aggression Flask", script.useaggression);
                    script.usedarkness = ImGui.Checkbox("Use Darkness", script.usedarkness);
                    script.quickprayer = ImGui.Checkbox("Use Quick Prayer 1 in Combat", script.quickprayer);
                    script.useExcalibur = ImGui.Checkbox("Use Excalibur", script.useExcalibur);
                    script.UseDeathGrasp = ImGui.Checkbox("Use Death's Grasp in EOF", script.UseDeathGrasp);
                    ImGui.SeparatorText("Teleport Options");
                    script.teleportToWarOnHealth = ImGui.Checkbox("Teleport to War's Retreat on Low Health", script.teleportToWarOnHealth);
                    ImGui.PushStyleColor(0, RGBToFloat(134), RGBToFloat(136), RGBToFloat(138), 1.0f); //text colour
                    ImGui.Text("Will teleport to War's Retreat if health falls below Threshold");
                    ImGui.PopStyleColor(1);
                    ImGui.SeparatorText("Miscellaneous Options");
                    script.KwuarmIncence = ImGui.Checkbox("Use Kwuarm Incense Sticks", script.KwuarmIncence);
                    if (script.KwuarmIncence) {
                        ImGui.SameLine();
                        script.overloadEnabled = ImGui.Checkbox("Overload?", script.overloadEnabled);
                    }

// For Torstol Incense
                    script.TorstolIncence = ImGui.Checkbox("Use Torstol Incense Sticks", script.TorstolIncence);
                    if (script.TorstolIncence) {
                        ImGui.SameLine();
                        script.overloadEnabled = ImGui.Checkbox("Overload?", script.overloadEnabled);
                    }

// For Lantadyme Incense
                    script.LantadymeIncence = ImGui.Checkbox("Use Lantadyme Incense Sticks", script.LantadymeIncence);
                    if (script.LantadymeIncence) {
                        ImGui.SameLine();
                        script.overloadEnabled = ImGui.Checkbox("Overload?", script.overloadEnabled);
                    }
                    script.usePenance = ImGui.Checkbox("Use Powder of Penance", script.usePenance);
                    script.useProtection = ImGui.Checkbox("Use Powder of Protection", script.useProtection);
                    script.useAntifire = ImGui.Checkbox("Use Antifire variant", script.useAntifire);
                    boolean tempUseScriptureOfWen = script.UseScriptureOfWen;
                    if (ImGui.Checkbox("Use Scripture of Wen in Combat", tempUseScriptureOfWen)) {
                        script.UseScriptureOfWen = true;
                        script.UseScriptureOfJas = false;
                        script.UseScriptureOfFul = false;
                    } else if (script.UseScriptureOfWen) {
                        script.UseScriptureOfWen = false;
                    }

// Use Scripture of Jas
                    boolean tempUseScriptureOfJas = script.UseScriptureOfJas;
                    if (ImGui.Checkbox("Use Scripture of Jas in Combat", tempUseScriptureOfJas)) {
                        script.UseScriptureOfWen = false;
                        script.UseScriptureOfJas = true;
                        script.UseScriptureOfFul = false;
                    } else if (script.UseScriptureOfJas) {
                        script.UseScriptureOfJas = false;
                    }

// Use Scripture of Ful
                    boolean tempUseScriptureOfFul = script.UseScriptureOfFul;
                    if (ImGui.Checkbox("Use Scripture of Ful in Combat", tempUseScriptureOfFul)) {
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
                                long calculatedTargetLogoutTimeMillis = currentTimeMillis + hours * 3600000 + minutes * 60000;
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
                if (ImGui.BeginTabItem("Skilling Options", ImGuiWindowFlag.None.getValue())) {
                    ImGui.SeparatorText("Skilling Potions");

                    script.useLightForm = ImGui.Checkbox("Use Light Form", script.useLightForm);
                    ImGui.PushStyleColor(0, RGBToFloat(134), RGBToFloat(136), RGBToFloat(138), 1.0f); //text colour
                    ImGui.SameLine();
                    ImGui.Text("Have on action bar");
                    ImGui.PopStyleColor(1);
                    script.useCrystalMask = ImGui.Checkbox("Use Crystal Mask", script.useCrystalMask);
                    ImGui.PushStyleColor(0, RGBToFloat(134), RGBToFloat(136), RGBToFloat(138), 1.0f); //text colour
                    ImGui.SameLine();
                    ImGui.Text("Have on action bar");
                    ImGui.PopStyleColor(1);
                    script.useSuperheatForm = ImGui.Checkbox("Use Superheat Form", script.useSuperheatForm);
                    ImGui.PushStyleColor(0, RGBToFloat(134), RGBToFloat(136), RGBToFloat(138), 1.0f); //text colour
                    ImGui.SameLine();
                    ImGui.Text("Have on action bar");
                    ImGui.PopStyleColor(1);
                    script.useNecromancyPotion = ImGui.Checkbox("Use Necromancy Potion", script.useNecromancyPotion);
                    ImGui.PushStyleColor(0, RGBToFloat(134), RGBToFloat(136), RGBToFloat(138), 1.0f); //text colour
                    ImGui.SameLine();
                    ImGui.Text("Use any variant of >necromancy< potions/flasks etc");
                    ImGui.PopStyleColor(1);
                    script.useHunter = ImGui.Checkbox("Use Extreme Hunter Potion", script.useHunter);
                    script.usedivination = ImGui.Checkbox("Use Extreme Divination Potion", script.usedivination);
                    script.usecooking = ImGui.Checkbox("Use Extreme Cooking Potion", script.usecooking);
                    ImGui.SeparatorText("Dummy Options");
                    ImGui.PushStyleColor(0, RGBToFloat(134), RGBToFloat(136), RGBToFloat(138), 1.0f); //text colour
                    ImGui.Text("Go to a Remote Location where nobody else has a chance to deploy a dummy + Place on action bar");
                    ImGui.PopStyleColor(1);
                    script.useMeleeDummy = ImGui.Checkbox("Use Melee Dummy", script.useMeleeDummy);
                    script.useRangedDummy = ImGui.Checkbox("Use Ranged Dummy", script.useRangedDummy);
                    script.useMagicDummy = ImGui.Checkbox("Use Magic Dummy", script.useMagicDummy);
                    script.useAgilityDummy = ImGui.Checkbox("Use Agility Dummy", script.useAgilityDummy);
                    script.useThievingDummy = ImGui.Checkbox("Use Thieving Dummy", script.useThievingDummy);
                    ImGui.EndTabItem();
                }


                if (ImGui.BeginTabItem("Combat Statistics", ImGuiWindowFlag.None.getValue())) {
                    showAttackStats = ImGui.Checkbox("Show Attack Stats", showAttackStats);
                    if (showAttackStats) {
                        // Display Attack Stats right underneath the checkbox
                        displayStatsForSkill(Skills.ATTACK, "Attack", startingAttackLevel, startingAttackXP);
                    }

                    showStrengthStats = ImGui.Checkbox("Show Strength Stats", showStrengthStats);
                    if (showStrengthStats) {
                        // Display Strength Stats right underneath the checkbox
                        displayStatsForSkill(Skills.STRENGTH, "Strength", startingStrengthLevel, startingStrengthXP);
                    }

                    showDefenseStats = ImGui.Checkbox("Show Defence Stats", showDefenseStats);
                    if (showDefenseStats) {
                        // Display Defence Stats right underneath the checkbox
                        displayStatsForSkill(Skills.DEFENSE, "Defence", startingDefenseLevel, startingDefenseXP);
                    }

                    showRangedStats = ImGui.Checkbox("Show Ranged Stats", showRangedStats);
                    if (showRangedStats) {
                        // Display Ranged Stats right underneath the checkbox
                        displayStatsForSkill(Skills.RANGED, "Ranged", startingRangedLevel, startingRangedXP);
                    }

                    showPrayerStats = ImGui.Checkbox("Show Prayer Stats", showPrayerStats);
                    if (showPrayerStats) {
                        // Display Prayer Stats right underneath the checkbox
                        displayStatsForSkill(Skills.PRAYER, "Prayer", startingPrayerLevel, startingPrayerXP);
                    }

                    showMagicStats = ImGui.Checkbox("Show Magic Stats", showMagicStats);
                    if (showMagicStats) {
                        // Display Magic Stats right underneath the checkbox
                        displayStatsForSkill(Skills.MAGIC, "Magic", startingMagicLevel, startingMagicXP);
                    }

                    showNecromancyStats = ImGui.Checkbox("Show Necromancy Stats", showNecromancyStats);
                    if (showNecromancyStats) {
                        // Display Necromancy Stats right underneath the checkbox
                        displayStatsForSkill(Skills.NECROMANCY, "Necromancy", startingNecromancyLevel, startingNecromancyXP);
                    }
                    showSummoningStats = ImGui.Checkbox("Show Summoning Stats", showSummoningStats);
                    if (showSummoningStats) {
                        // Display Summoning Stats right underneath the checkbox
                        displayStatsForSkill(Skills.SUMMONING, "Summoning", startingSummoningLevel, startingSummoningXP);
                    }
                    showConstitutionStats = ImGui.Checkbox("Show Constitution Stats", showConstitutionStats);
                    if (showConstitutionStats) {
                        // Display Constitution Stats right underneath the checkbox
                        displayStatsForSkill(Skills.CONSTITUTION, "Constitution", startingConstitutionLevel, startingConstitutionXP);
                    }

                    ImGui.EndTabItem();
                }
                if (ImGui.BeginTabItem("Gathering Statistics", ImGuiWindowFlag.None.getValue())) {
                    showMiningStats = ImGui.Checkbox("Show Mining Stats", showMiningStats);
                    if (showMiningStats) {
                        // Display Mining Stats right underneath the checkbox
                        displayStatsForSkill(Skills.MINING, "Mining", startingMiningLevel, startingMiningXP);
                    }
                    showFishingStats = ImGui.Checkbox("Show Fishing Stats", showFishingStats);
                    if (showFishingStats) {
                        // Display Fishing Stats right underneath the checkbox
                        displayStatsForSkill(Skills.FISHING, "Fishing", startingFishingLevel, startingFishingXP);
                    }
                    showWoodcuttingStats = ImGui.Checkbox("Show Woodcutting Stats", showWoodcuttingStats);
                    if (showWoodcuttingStats) {
                        // Display Woodcutting Stats right underneath the checkbox
                        displayStatsForSkill(Skills.WOODCUTTING, "Woodcutting", startingWoodcuttingLevel, startingWoodcuttingXP);
                    }
                    showFarmingStats = ImGui.Checkbox("Show Farming Stats", showFarmingStats);
                    if (showFarmingStats) {
                        // Display Farming Stats right underneath the checkbox
                        displayStatsForSkill(Skills.FARMING, "Farming", startingFarmingLevel, startingFarmingXP);
                    }
                    showDivinationStats = ImGui.Checkbox("Show Divination Stats", showDivinationStats);
                    if (showDivinationStats) {
                        // Display Divination Stats right underneath the checkbox
                        displayStatsForSkill(Skills.DIVINATION, "Divination", startingDivinationLevel, startingDivinationXP);
                    }
                    showHunterStats = ImGui.Checkbox("Show Hunter Stats", showHunterStats);
                    if (showHunterStats) {
                        // Display Hunter Stats right underneath the checkbox
                        displayStatsForSkill(Skills.HUNTER, "Hunter", startingHunterLevel, startingHunterXP);
                    }
                    ImGui.EndTabItem();
                }

                if (ImGui.BeginTabItem("Artisan Statistics", ImGuiWindowFlag.None.getValue())) {
                    showRunecraftingStats = ImGui.Checkbox("Show Runecrafting Stats", showRunecraftingStats);
                    if (showRunecraftingStats) {
                        // Display Runecrafting Stats right underneath the checkbox
                        displayStatsForSkill(Skills.RUNECRAFTING, "Runecrafting", startingRunecraftingLevel, startingRunecraftingXP);
                    }
                    showConstructionStats = ImGui.Checkbox("Show Construction Stats", showConstructionStats);
                    if (showConstructionStats) {
                        // Display Construction Stats right underneath the checkbox
                        displayStatsForSkill(Skills.CONSTRUCTION, "Construction", startingConstructionLevel, startingConstructionXP);
                    }
                    showCraftingStats = ImGui.Checkbox("Show Crafting Stats", showCraftingStats);
                    if (showCraftingStats) {
                        // Display Crafting Stats right underneath the checkbox
                        displayStatsForSkill(Skills.CRAFTING, "Crafting", startingCraftingLevel, startingCraftingXP);
                    }
                    showFletchingStats = ImGui.Checkbox("Show Fletching Stats", showFletchingStats);
                    if (showFletchingStats) {
                        // Display Fletching Stats right underneath the checkbox
                        displayStatsForSkill(Skills.FLETCHING, "Fletching", startingFletchingLevel, startingFletchingXP);
                    }
                    showSmithingStats = ImGui.Checkbox("Show Smithing Stats", showSmithingStats);
                    if (showSmithingStats) {
                        // Display Smithing Stats right underneath the checkbox
                        displayStatsForSkill(Skills.SMITHING, "Smithing", startingSmithingLevel, startingSmithingXP);
                    }
                    showHerbloreStats = ImGui.Checkbox("Show Herblore Stats", showHerbloreStats);
                    if (showHerbloreStats) {
                        // Display Herblore Stats right underneath the checkbox
                        displayStatsForSkill(Skills.HERBLORE, "Herblore", startingHerbloreLevel, startingHerbloreXP);
                    }
                    showFiremakingStats = ImGui.Checkbox("Show Firemaking Stats", showFiremakingStats);
                    if (showFiremakingStats) {
                        // Display Firemaking Stats right underneath the checkbox
                        displayStatsForSkill(Skills.FIREMAKING, "Firemaking", startingFiremakingLevel, startingFiremakingXP);
                    }
                    showCookingStats = ImGui.Checkbox("Show Cooking Stats", showCookingStats);
                    if (showCookingStats) {
                        // Display Cooking Stats right underneath the checkbox
                        displayStatsForSkill(Skills.COOKING, "Cooking", startingCookingLevel, startingCookingXP);
                    }
                    ImGui.EndTabItem();
                }
                if (ImGui.BeginTabItem("Support Statistics", ImGuiWindowFlag.None.getValue())) {
                    showSlayerStats = ImGui.Checkbox("Show Slayer Stats", showSlayerStats);
                    if (showSlayerStats) {
                        // Display Slayer Stats right underneath the checkbox
                        displayStatsForSkill(Skills.SLAYER, "Slayer", startingSlayerLevel, startingSlayerXP);
                    }
                    showThievingStats = ImGui.Checkbox("Show Thieving Stats", showThievingStats);
                    if (showThievingStats) {
                        // Display Thieving Stats right underneath the checkbox
                        displayStatsForSkill(Skills.THIEVING, "Thieving", startingThievingLevel, startingThievingXP);
                    }
                    showAgilityStats = ImGui.Checkbox("Show Agility Stats", showAgilityStats);
                    if (showAgilityStats) {
                        // Display Agility Stats right underneath the checkbox
                        displayStatsForSkill(Skills.AGILITY, "Agility", startingAgilityLevel, startingAgilityXP);
                    }
                    showDungeoneeringStats = ImGui.Checkbox("Show Dungeoneering Stats", showDungeoneeringStats);
                    if (showDungeoneeringStats) {
                        // Display Dungeoneering Stats right underneath the checkbox
                        displayStatsForSkill(Skills.DUNGEONEERING, "Dungeoneering", startingDungeoneeringLevel, startingDungeoneeringXP);
                    }
                    showArchaeologyStats = ImGui.Checkbox("Show Archaeology Stats", showArchaeologyStats);
                    if (showArchaeologyStats) {
                        // Display Archaeology Stats right underneath the checkbox
                        displayStatsForSkill(Skills.ARCHAEOLOGY, "Archaeology", startingArchaeologyLevel, startingArchaeologyXP);
                    }
                    showInventionStats = ImGui.Checkbox("Show Invention Stats", showInventionStats);
                    if (showInventionStats) {
                        // Display Invention Stats right underneath the checkbox
                        displayStatsForSkill(Skills.INVENTION, "Invention", startingInventionLevel, startingInventionXP);
                    }
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
    private String formatNumberForDisplay(double number) {
        if (number < 1000) {
            return String.format("%.0f", number); // No suffix
        } else if (number < 1000000) {
            return String.format("%.1fk", number / 1000); // Thousands
        } else if (number < 1000000000) {
            return String.format("%.1fM", number / 1000000); // Millions
        } else {
            return String.format("%.1fB", number / 1000000000); // Billions
        }
    }
    private void displayStatsForSkill(Skills skill, String skillName, int startingLevel, int startingXP) {
        int currentLevel = skill.getSkill().getLevel();
        int levelsGained = currentLevel - startingLevel;
        ImGui.Text("Current " + skillName + " Level: " + currentLevel + "  (" + levelsGained + " Gained)");

        int currentXP = skill.getSkill().getExperience();
        int xpForNextLevel = skill.getExperienceAt(currentLevel + 1);
        int xpTillNextLevel = xpForNextLevel - currentXP;
        ImGui.Text(skillName + " XP remaining: " + xpTillNextLevel);

        displayXPGained(skill, startingXP);
        displayXpPerHour(skill, startingXP);
        String timeToLevelStr = calculateTimeTillNextLevel(skill, startingXP);
        ImGui.Text(timeToLevelStr);
    }

    private void displayXPGained(Skills skill, int startingXP) {
        int currentXP = skill.getSkill().getExperience();
        int xpGained = currentXP - startingXP;
        ImGui.Text("XP Gained: " + xpGained);
    }

    private void displayXpPerHour(Skills skill, int startingXP) {
        long elapsedTime = System.currentTimeMillis() - scriptStartTime;
        double hoursElapsed = elapsedTime / (1000.0 * 60 * 60);
        int currentXP = skill.getSkill().getExperience();
        int xpGained = currentXP - startingXP;
        double xpPerHour = hoursElapsed > 0 ? xpGained / hoursElapsed : 0;
        String formattedXpPerHour = formatNumberForDisplay(xpPerHour);
        ImGui.Text("XP Per Hour: " + formattedXpPerHour);
    }

    private String calculateTimeTillNextLevel(Skills skill, int startingXP) {
        int currentXP = skill.getSkill().getExperience();
        int currentLevel = skill.getSkill().getLevel();
        int xpForNextLevel = skill.getExperienceAt(currentLevel + 1);
        int xpGained = currentXP - startingXP;
        long timeElapsed = System.currentTimeMillis() - scriptStartTime;

        if (xpGained > 0 && timeElapsed > 0) {
            double xpPerMillisecond = xpGained / (double) timeElapsed;
            long timeToLevelMillis = (long) ((xpForNextLevel - currentXP) / xpPerMillisecond);
            long timeToLevelSecs = timeToLevelMillis / 1000;
            long hours = timeToLevelSecs / 3600;
            long minutes = (timeToLevelSecs % 3600) / 60;
            long seconds = timeToLevelSecs % 60;

            return String.format("Time remaining to next level: " + "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return "Calculating time to next level...";
        }
    }
}
