package prod.brainiac.olympixel.commands.olympixelCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class OlympixelTabComp implements TabCompleter {

    List<String> stringList = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        stringList.add("Start");
        return stringList;
    }
}