package net.milkev.mealapiaddon.common.mixins;

import dev.emi.trinkets.api.TrinketsApi;
import net.milkev.mealapiaddon.common.MilkevsEssentials;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class FlightCharmMixin {

    @Final
    @Shadow
    protected
    ServerPlayerEntity player;
    @Shadow
    protected
    ServerWorld world;

    @Inject(at = @At("TAIL"), method = "setGameMode")
    public void setGameMode(GameMode gameMode, GameMode previousGameMode, CallbackInfo ci) {
        if(TrinketsApi.getTrinketComponent(this.player).get().isEquipped(MilkevsEssentials.FLIGHT_CHARM)) {
            this.player.getAbilities().allowFlying = true;
            this.player.getAbilities().flying = true;
            this.player.sendAbilitiesUpdate();
            this.player.server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, this.player));
            this.world.updateSleepingPlayers();
        }

    }

}
