package ranks.utility;

import org.bukkit.ChatColor;

public class String2Color {

    public static ChatColor string2Color(String assignColor) {
        ChatColor groupColor = ChatColor.WHITE;
        if(assignColor.equals("&0")) groupColor = ChatColor.BLACK;
        if(assignColor.equals("&1")) groupColor = ChatColor.DARK_BLUE;
        if(assignColor.equals("&2")) groupColor = ChatColor.DARK_GREEN;
        if(assignColor.equals("&3")) groupColor = ChatColor.DARK_AQUA;
        if(assignColor.equals("&4")) groupColor = ChatColor.DARK_RED;
        if(assignColor.equals("&5")) groupColor = ChatColor.DARK_PURPLE;
        if(assignColor.equals("&6")) groupColor = ChatColor.GOLD;
        if(assignColor.equals("&7")) groupColor = ChatColor.GRAY;
        if(assignColor.equals("&8")) groupColor = ChatColor.DARK_GRAY;
        if(assignColor.equals("&9")) groupColor = ChatColor.BLUE;
        if(assignColor.equals("&a")) groupColor = ChatColor.GREEN;
        if(assignColor.equals("&b")) groupColor = ChatColor.AQUA;
        if(assignColor.equals("&c")) groupColor = ChatColor.RED;
        if(assignColor.equals("&d")) groupColor = ChatColor.LIGHT_PURPLE;
        if(assignColor.equals("&e")) groupColor = ChatColor.YELLOW;
        if(assignColor.equals("&f")) groupColor = ChatColor.WHITE;
        if(assignColor.equals("&g")) groupColor = ChatColor.MAGIC;
        return groupColor;
    }
}
