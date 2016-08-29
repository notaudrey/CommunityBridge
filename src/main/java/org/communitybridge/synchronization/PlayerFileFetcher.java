package org.communitybridge.synchronization;

import org.bukkit.entity.Player;

import java.io.File;

public class PlayerFileFetcher {
    private File folder;
    private File playerFile;

    public File getPlayerFile(File dataFolder, Player player, boolean allowOldFile) {
        folder = makeFile(dataFolder, "Players");
        playerFile = makeFile(folder, player.getUniqueId().toString() + ".yml");
        if(!playerFile.exists() && allowOldFile) {
            return new File(folder, player.getName() + ".yml");
        }
        return playerFile;
    }

    File makeFile(File folder, String filename) {
        return new File(folder, filename);
    }
}
