package org.communitybridge.main;

import org.communitybridge.utility.StringUtilities;

import java.text.SimpleDateFormat;

public class PlayerStatistics {
    private final SimpleDateFormat dateFormat;
    private String userID;
    private String onlineStatus;
    private long lastOnlineTime;
    private long gameTime;
    private int level;
    private int totalXP;
    private float currentXP;
    private double health;
    private int lifeTicks;

    PlayerStatistics(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public int getLastOnlineTimeInSeconds() {
        return (int) (lastOnlineTime / 1000L);
    }

    public String getLastOnlineTimeFormatted() {
        return dateFormat.format(lastOnlineTime);
    }

    public void setLastOnlineTime(long lastonlineTime) {
        this.lastOnlineTime = lastonlineTime;
    }

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public String getGameTimeFormatted() {
        return StringUtilities.timeElapsedToString(gameTime);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(int totalxp) {
        this.totalXP = totalxp;
    }

    public float getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(float currentxp) {
        this.currentXP = currentxp;
    }

    public String getCurrentXPFormatted() {
        return ((int) (getCurrentXP() * 100)) + "%";
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getLifeTicks() {
        return lifeTicks;
    }

    public void setLifeTicks(int lifeticks) {
        this.lifeTicks = lifeticks;
    }

    public String getLifeTicksFormatted() {
        return StringUtilities.timeElapsedToString(lifeTicks / 20);
    }
}
