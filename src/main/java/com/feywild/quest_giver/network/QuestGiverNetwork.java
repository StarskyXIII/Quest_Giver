package com.feywild.quest_giver.network;

import com.feywild.quest_giver.network.quest.*;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.network.NetworkX;
import mods.thecomputerizer.reputation.common.ModDefinitions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;

public class QuestGiverNetwork extends NetworkX {

    public QuestGiverNetwork(ModX mod) {
        super(mod);
    }

    @Override
    protected Protocol getProtocol() {
        return Protocol.of("1");
    }

    @Override
    protected void registerPackets() {

        this.register(new OpenQuestSelectionSerializer(), () -> OpenQuestSelectionHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new OpenQuestDisplaySerializer(), () -> OpenQuestDisplayHandler::handle, NetworkDirection.PLAY_TO_CLIENT);
        this.register(new SelectQuestSerializer(), () -> SelectQuestHandler::handle, NetworkDirection.PLAY_TO_SERVER);
        this.register(new ConfirmQuestSerializer(), () -> ConfirmQuestHandler::handle, NetworkDirection.PLAY_TO_SERVER);
        this.channel.registerMessage(99,SyncRenders.class, SyncRenders::encode, SyncRenders::new, SyncRenders::handle);
    }

    public void sendTo(Object message,ServerPlayer player) {
        this.channel.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
