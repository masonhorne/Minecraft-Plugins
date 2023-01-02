package ranks.commands;

import org.bukkit.command.CommandSender;

public abstract class RankCommand {

    // Name and arguments for the command
    private String name, args;

    public RankCommand(String name, String args){
        // Store the name and arguments on command
        this.name = name;
        this.args = args;
    }

    public String getName() { return name; }

    public String getArgs() { return args; };

    public abstract void run(CommandSender sender, String[] args);
}
