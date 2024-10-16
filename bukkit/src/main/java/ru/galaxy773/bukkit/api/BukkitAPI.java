package ru.galaxy773.bukkit.api;

import lombok.experimental.UtilityClass;
import ru.galaxy773.bukkit.api.chatmessage.ChatMessageAPI;
import ru.galaxy773.bukkit.api.commands.CommandsAPI;
import ru.galaxy773.bukkit.api.configuration.ConfigAPI;
import ru.galaxy773.bukkit.api.core.CoreAPI;
import ru.galaxy773.bukkit.api.effect.ParticleAPI;
import ru.galaxy773.bukkit.api.entity.EntityAPI;
import ru.galaxy773.bukkit.api.gamer.leveling.Leveling;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.api.hologram.HologramAPI;
import ru.galaxy773.bukkit.api.inventory.InventoryAPI;
import ru.galaxy773.bukkit.api.scoreboard.ScoreBoardAPI;
import ru.galaxy773.bukkit.api.usableitem.UsableAPI;
import ru.galaxy773.bukkit.api.worldborder.BorderAPI;
import ru.galaxy773.bukkit.impl.chatmessage.ChatMessageAPIImpl;
import ru.galaxy773.bukkit.impl.commands.CommandsAPIImpl;
import ru.galaxy773.bukkit.impl.configuration.ConfigAPIImpl;
import ru.galaxy773.bukkit.impl.core.CoreAPIImpl;
import ru.galaxy773.bukkit.impl.effect.ParticleAPIImpl;
import ru.galaxy773.bukkit.impl.entity.EntityAPIImpl;
import ru.galaxy773.bukkit.impl.gamer.leveling.LevelingImpl;
import ru.galaxy773.bukkit.impl.gui.manager.CraftGuiManager;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.bukkit.impl.hologram.HologramAPIImpl;
import ru.galaxy773.bukkit.impl.inventory.InventoryAPIImpl;
import ru.galaxy773.bukkit.impl.scoreboard.ScoreBoardAPIImpl;
import ru.galaxy773.bukkit.impl.usableItem.UsableAPIImpl;
import ru.galaxy773.bukkit.impl.worldborder.BorderAPIImpl;
import ru.galaxy773.bukkit.nms.packetreader.PacketReaderListener;
import ru.galaxy773.multiplatform.api.placeholder.PlaceholderAPI;
import ru.galaxy773.multiplatform.api.utils.Lazy;
import ru.galaxy773.multiplatform.impl.placeholder.PlaceholderAPIImpl;

@UtilityClass
public class BukkitAPI {

    private final Lazy<InventoryAPI> INVENTORY_API = Lazy.create(() -> new InventoryAPIImpl(BukkitPlugin.getInstance()));
    private final Lazy<CommandsAPI> COMMANDS_API = Lazy.create(CommandsAPIImpl::new);
    private final Lazy<UsableAPI> USABLE_API = Lazy.create(() -> new UsableAPIImpl(BukkitPlugin.getInstance()));
    private final Lazy<ConfigAPI> CONFIG_API = Lazy.create(ConfigAPIImpl::new);
    private final Lazy<HologramAPI> HOLOGRAM_API = Lazy.create(HologramAPIImpl::new);
    private final Lazy<ScoreBoardAPI> SCORE_BOARD_API = Lazy.create(ScoreBoardAPIImpl::new);
    private final Lazy<EntityAPI> ENTITY_API = Lazy.create(() -> {
        new PacketReaderListener();
        return new EntityAPIImpl();
    });
    private final Lazy<ParticleAPI> PARTICLE_API = Lazy.create(ParticleAPIImpl::new);
    private final Lazy<ChatMessageAPI> CHAT_MESSAGE_API = Lazy.create(ChatMessageAPIImpl::new);
    private final Lazy<BorderAPI> BORDER_API = Lazy.create(BorderAPIImpl::new);
    private final Lazy<GuiManager<AbstractGui<?>>> GUI_MANAGER = Lazy.create(() -> new CraftGuiManager(BukkitPlugin.getInstance()));
    private final Lazy<Leveling> LEVELING = Lazy.create(LevelingImpl::new);
    private final Lazy<PlaceholderAPI> PLACEHOLDER_API = Lazy.create(PlaceholderAPIImpl::new);
    private final Lazy<CoreAPI> CORE_API = Lazy.create(CoreAPIImpl::new);

    public InventoryAPI getInventoryAPI() {
        return INVENTORY_API.get();
    }

    public CommandsAPI getCommandsAPI() {
        return COMMANDS_API.get();
    }

    public UsableAPI getUsableAPI() {
        return USABLE_API.get();
    }

    public ConfigAPI getConfigAPI() {
        return CONFIG_API.get();
    }

    public HologramAPI getHologramAPI() {
        return HOLOGRAM_API.get();
    }

    public ScoreBoardAPI getScoreBoardAPI() {
        return SCORE_BOARD_API.get();
    }

    public EntityAPI getEntityAPI() {
        return ENTITY_API.get();
    }

    public ParticleAPI getParticleAPI() {
        return PARTICLE_API.get();
    }

    public ChatMessageAPI getChatMessageAPI() {
        return CHAT_MESSAGE_API.get();
    }

    public BorderAPI getBorderAPI() {
        return BORDER_API.get();
    }

    public GuiManager<AbstractGui<?>> getGuiManager() {
        return GUI_MANAGER.get();
    }

    public Leveling getLeveling() {
        return LEVELING.get();
    }

    public PlaceholderAPI getPlaceholderAPI() {
        return PLACEHOLDER_API.get();
    }

    public CoreAPI getCoreAPI() {
        return CORE_API.get();
    }
}
