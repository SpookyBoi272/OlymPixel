package prod.brainiac.olympixel.commands;

import org.bukkit.entity.Player;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.utils.ChatMsgManager;

public class SetCountSubCommand extends SubCommand {

    private final Olympixel plugin;
    private final ChatMsgManager chatMsgManager;

    public SetCountSubCommand(Olympixel plugin, ChatMsgManager chatMsgManager) {
        this.plugin = plugin;
        this.chatMsgManager = chatMsgManager;
    }


    @Override
    public String getName() {
        return "setCD";
    }

    @Override
    public String getDescription() {
        return "Set countdown (In Seconds) before the game starts";
    }

    @Override
    public Boolean requiresOP() {
        return true;
    }

    @Override
    public String getSyntax() {
        return "/olympixel setCD";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length != 2) {
            chatMsgManager.msgPlayer(player, "Invalid usage of command");
            return;
        }

        String str = args[1];
        try {
            int secs = Integer.parseInt(str);
            plugin.configHook.setCountdownSecs(secs);
            chatMsgManager.msgPlayer(player, "Successfully set countdown to " + secs + " seconds");
        } catch (NumberFormatException e) {
            chatMsgManager.msgPlayer(player, "Invalid usage of command");
        }

    }
}
