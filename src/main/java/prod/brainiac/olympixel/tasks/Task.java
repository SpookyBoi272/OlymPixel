package prod.brainiac.olympixel.tasks;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Task {

    public abstract int getTaskID();

    public abstract String getObjective();

    public abstract String getWinMsg();

    public abstract void registerListener(JavaPlugin plugin);

    public abstract Listener getListener();
}
