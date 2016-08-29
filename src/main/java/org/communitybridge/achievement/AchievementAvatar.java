package org.communitybridge.achievement;

import org.bukkit.entity.Player;
import org.communitybridge.main.Environment;

public class AchievementAvatar extends Achievement {
    public AchievementAvatar(Environment environment) {
        super(environment);
    }

    @Override
    public boolean playerQualifies(Player player, PlayerAchievementState state) {
        return environment.getConfiguration().avatarEnabled
                && environment.getWebApplication().playerHasAvatar(environment.getUserPlayerLinker().getUserID(player))
                && state.getAvatarAchievements() < limit
                && canRewardAllItemRewards(player);
    }

    @Override
    public void rewardPlayer(Player player, PlayerAchievementState state) {
        super.rewardPlayer(player, state);
        state.avatarIncrement();
    }
}
