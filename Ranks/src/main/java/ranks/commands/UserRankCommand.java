package ranks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import ranks.managers.PermissionsManager;

public class UserRankCommand extends RankCommand {

    public UserRankCommand() {
        super("permission", "[<name>] [<add | remove> <perm>]");
    }

    @Override
    public void run ( CommandSender sender, String[] args ) {
        if(args.length == 0){
            sender.sendMessage( ChatColor.RED + "You didn't enter a user.");
            return;
        }
        String player = args[0];
        if(args.length < 2){
            // If no permissions output none, otherwise display all permissions
            if(player.equals("add")) sender.sendMessage( ChatColor.YELLOW + "Please provide a player and a permission to add to the player." );
            else if(player.equals("remove")) sender.sendMessage( ChatColor.YELLOW + "Please provide a player and a permission to remove from the player." );
            else if(PermissionsManager.getInstance().getPermissions( player ).size() == 0) sender.sendMessage(ChatColor.YELLOW + "No permissions for " + player + ".");
            else {
                for (String permission : PermissionsManager.getInstance().getPermissions(player)) {
                    sender.sendMessage(ChatColor.YELLOW + permission);
                    return;
                }
            }
        } else {
            if(args[1].equalsIgnoreCase("add")){
                PermissionsManager.getInstance().addPermission( player, args[2] );
                sender.sendMessage( ChatColor.YELLOW + "Granted " + args[2] + " to " + player + "." );
            }
            if(args[1].equalsIgnoreCase("remove")){
                PermissionsManager.getInstance().removePermission( player, args[2] );
                sender.sendMessage( ChatColor.YELLOW + "Removed " + args[2] + " from " + player + "." );
            }
        }
    }
}
