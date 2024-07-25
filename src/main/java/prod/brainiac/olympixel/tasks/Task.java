package prod.brainiac.olympixel.tasks;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import prod.brainiac.olympixel.utils.GameManager;

public abstract class Task {
    protected final GameManager manager;

    public Task(GameManager manager){
        this.manager = manager;
    }

    public abstract int getTaskID();

    public abstract String getObjective();

    public abstract String getWinMsg();

    public abstract void registerListener(JavaPlugin plugin);

    public abstract Listener getListener();
}
