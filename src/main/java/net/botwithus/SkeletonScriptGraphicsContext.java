package net.botwithus;

import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.imgui.ImGui;
import net.botwithus.rs3.imgui.ImGuiWindowFlag;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.script.ScriptGraphicsContext;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

public class SkeletonScriptGraphicsContext extends ScriptGraphicsContext {
    private SkeletonScript script;
    private long scriptStartTime;
    private String healthFeedbackMessage = "";
    private String prayerFeedbackMessage = "";
    private String prayerPointsThresholdStr = "1000"; // Initialize with your default or saved value
    private String healthThresholdStr = "50"; // Initialize with your default or saved value

    public SkeletonScriptGraphicsContext(ScriptConsole scriptConsole, SkeletonScript script) {
        super(scriptConsole);
        this.script = script;
        this.scriptStartTime = System.currentTimeMillis();
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

        ImGui.SetWindowSize(200, 200);
        if (ImGui.Begin("Snows Utility", 0)) {
            ImGui.PushStyleVar(11, 10, 5f); // Increase button size - left is width, right is height

            ImGui.SeparatorText("Toggle needed options");
            script.eatFood = ImGui.Checkbox("Eat Food", script.eatFood);
            ImGui.SetItemWidth(40);
            healthThresholdStr = ImGui.InputText("Health Threshold (%)", healthThresholdStr);
            ImGui.SameLine();
            if (ImGui.Button("Set Health Threshold")) {
                try {
                    int newHealthThreshold = Integer.parseInt(healthThresholdStr.trim());
                    if(newHealthThreshold >= 0 && newHealthThreshold <= 100) {
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
            script.UseSoulSplit = ImGui.Checkbox("Use Soul Split in Combat", script.UseSoulSplit);
            script.useoverload = ImGui.Checkbox("Use Overloads", script.useoverload);
            script.useaggression = ImGui.Checkbox("Use Aggression Flask", script.useaggression);
            script.usedarkness = ImGui.Checkbox("Use Darkness", script.usedarkness);
            script.quickprayer = ImGui.Checkbox("Use Quick Prayer 1 in Combat", script.quickprayer);
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
            script.useHunter = ImGui.Checkbox("Use Extreme Hunter Potion", script.useHunter);
            script.usedivination = ImGui.Checkbox("Use Extreme Divination Potion", script.usedivination);
            script.usecooking = ImGui.Checkbox("Use Extreme Cooking Potion", script.usecooking);


            ImGui.PopStyleColor(100);

            ImGui.PushStyleColor(0, RGBToFloat(255), RGBToFloat(255), RGBToFloat(255), 0.7f);

            long elapsedTimeMillis = System.currentTimeMillis() - this.scriptStartTime;
            long elapsedSeconds = elapsedTimeMillis / 1000L;
            long hours = elapsedSeconds / 3600L;
            long minutes = elapsedSeconds % 3600L / 60L;
            long seconds = elapsedSeconds % 60L;
            String displayTimeRunning = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            ImGui.SeparatorText("Time Running  " + displayTimeRunning);

            ImGui.End();
        }
        ImGui.PopStyleColor(100);
    }
    private static float RGBToFloat(int rgbValue) {
        return rgbValue / 255.0f;
    }

    @Override
    public void drawOverlay() {
        super.drawOverlay();
    }
}
