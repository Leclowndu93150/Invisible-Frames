package com.leclowndu93150.invisible_frames;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("invisible_frames")
@Mod.EventBusSubscriber(modid = "invisible_frames")
public class InvisibleFrames {
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof ItemFrame frame)) {
            return;
        }

        Player player = event.getEntity();

        if (!player.isShiftKeyDown()) {
            return;
        }

        if (!player.getItemInHand(event.getHand()).is(Items.HONEYCOMB)) {
            return;
        }

        frame.setInvisible(!frame.isInvisible());
        frame.gameEvent(GameEvent.BLOCK_CHANGE);
        player.getItemInHand(event.getHand()).shrink(1);

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    @SubscribeEvent
    public static void onItemFrameAttackEvent(AttackEntityEvent event) {
        if (!(event.getTarget() instanceof ItemFrame frame) || frame.level().isClientSide()) {
            return;
        }

        if (!frame.getItem().isEmpty()) {
            frame.setInvisible(false);
            frame.gameEvent(GameEvent.BLOCK_CHANGE);
        }
    }
}