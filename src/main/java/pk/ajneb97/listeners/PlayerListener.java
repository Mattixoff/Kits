package pk.ajneb97.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import pk.ajneb97.PlayerKits2;
import pk.ajneb97.managers.InventoryManager;
import pk.ajneb97.managers.MessagesManager;
import pk.ajneb97.model.internal.PlayerKitsMessageResult;
import pk.ajneb97.model.inventory.InventoryPlayer;
import pk.ajneb97.utils.InventoryUtils;
import pk.ajneb97.utils.ItemUtils;

public class PlayerListener implements Listener {

    private PlayerKits2 plugin;
    public PlayerListener(PlayerKits2 plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        plugin.getPlayerDataManager().manageJoin(player);

        //Update notification
        String latestVersion = plugin.getUpdateCheckerManager().getLatestVersion();
        if(player.isOp() && plugin.getConfigsManager().getMainConfigManager().isUpdateNotify() && !plugin.version.equals(latestVersion)){
            player.sendMessage(MessagesManager.getLegacyColoredMessage(plugin.prefix+"&cThere is a new version available. &e(&7"+latestVersion+"&e)"));
            player.sendMessage(MessagesManager.getLegacyColoredMessage("&cYou can download it at: &ahttps://modrinth.com/plugin/playerkits-2"));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        plugin.getPlayerDataManager().manageLeave(event.getPlayer());
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        plugin.getInventoryManager().removeInventoryPlayer(player);
    }

    @EventHandler
    public void clickInventory(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        InventoryManager invManager = plugin.getInventoryManager();
        InventoryPlayer inventoryPlayer = invManager.getInventoryPlayer(player);
        if(inventoryPlayer != null) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null || event.getSlotType() == null){
                return;
            }

            if(event.getClickedInventory().equals(InventoryUtils.getTopInventory(player))) {
                ClickType clickType = event.getClick();
                invManager.clickInventory(inventoryPlayer,event.getCurrentItem(),clickType);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(item == null || item.getType().isAir()){
            return;
        }

        // Check if this is a kit token item
        String kitName = ItemUtils.getTagStringItem(plugin, item, "playerkits_token");
        if(kitName == null){
            return;
        }

        event.setCancelled(true);

        // Give the actual kit to the player
        PlayerKitsMessageResult result = plugin.getKitsManager().giveKit(player, kitName,
                new pk.ajneb97.model.internal.GiveKitInstructions());

        MessagesManager msgManager = plugin.getMessagesManager();
        org.bukkit.configuration.file.FileConfiguration messagesConfig = plugin.getConfigsManager().getMessagesConfigManager().getConfig();

        if(result.isError()){
            msgManager.sendMessage(player, result.getMessage(), true);
        }else{
            if(result.isProceedToBuy()){
                // Open requirements inventory
                InventoryPlayer inventoryPlayer = new InventoryPlayer(player, "buy_requirements_inventory");
                inventoryPlayer.setKitName(kitName);
                inventoryPlayer.setPreviousInventoryName("main_inventory");
                plugin.getInventoryManager().openInventory(inventoryPlayer);
                return;
            }

            // Remove one token item from player's hand
            int amount = item.getAmount();
            if(amount <= 1){
                player.getInventory().setItemInMainHand(null);
            }else{
                item.setAmount(amount - 1);
            }

            msgManager.sendMessage(player, messagesConfig.getString("kitReceived").replace("%kit%", kitName), true);
        }
    }
}
