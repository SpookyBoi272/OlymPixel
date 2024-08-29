package prod.brainiac.olympixel.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prod.brainiac.olympixel.Olympixel;
import prod.brainiac.olympixel.utils.ChatMsgManager;

import java.util.ArrayList;

public class OlympixelManager implements CommandExecutor {

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public OlympixelManager(ChatMsgManager chatMsgManager, Olympixel plugin) {
        subCommands.add(new StartSubCommand(chatMsgManager, plugin));
        subCommands.add(new StopSubCommand(chatMsgManager));
        subCommands.add(new SetCountSubCommand(plugin, chatMsgManager));
        subCommands.add(new SetWinScoreSubCommand(plugin, chatMsgManager));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubcommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {

                        if (getSubcommands().get(i).requiresOP() && !sender.hasPermission("olympixel.admin")) {
                            sender.sendMessage("You need to be an operator to perform this command.");
                            return true;
                        }

                        getSubcommands().get(i).perform(p, args);
                    }
                }
            } else {
                p.sendMessage("--------------------------------");
                for (int i = 0; i < getSubcommands().size(); i++) {
                    p.sendMessage(getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription());
                }
                p.sendMessage("--------------------------------");
            }

        }

        return true;
    }

    public ArrayList<SubCommand> getSubcommands() {
        return subCommands;
    }

}

