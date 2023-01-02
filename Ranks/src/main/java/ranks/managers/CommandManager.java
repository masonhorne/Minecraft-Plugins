package ranks.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ranks.commands.CheckRankCommand;
import ranks.commands.GroupRankCommand;
import ranks.commands.RankCommand;
import ranks.commands.UserRankCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor {

    /** List of all possible Rank commands */
    private List<RankCommand> commands = new ArrayList();

    public CommandManager() {
        commands.add(new CheckRankCommand());
        commands.add(new UserRankCommand());
        commands.add(new GroupRankCommand());
    }

    @Override
    public boolean onCommand ( CommandSender commandSender, Command command, String s, String[] strings ) {
        if(command.getName().equalsIgnoreCase("ranks")){
            // If no arguments are provided, output a usage message for all commands
            if(strings.length == 0) {
                for(RankCommand cmd : commands) commandSender.sendMessage( ChatColor.YELLOW +  "/ranks " + cmd.getName() + " " + cmd.getArgs());
                return true;
            }
            // Initialize a list of arguments, removing the first
            List<String> args = new ArrayList(Arrays.asList(strings));
            args.remove(0);
            // Loop through all available commands
            for(RankCommand cmd : commands) {
                // If requested command is found, execute it
                if(cmd.getName().equalsIgnoreCase(strings[0])) {
                    try { cmd.run(commandSender, args.toArray(new String[args.size()])); }
                    catch (Exception e) { commandSender.sendMessage(ChatColor.RED + "An error has occurred."); e.printStackTrace(); }
                    return true;
                }
            }
        }
        return true;
    }
}
