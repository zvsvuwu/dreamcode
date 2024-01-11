package pl.virtual.gamma.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {

    public void message(CommandSender sender, String type, String message){
        if (sender instanceof Player){
            Player p = (Player)sender;
            switch (type.toUpperCase()){
                case "TITLE":{ ChatUtil.sendTitle(p, message, "");break; }
                case "TITLE_SUBTITLE":{ ChatUtil.sendTitle(p, message, message);break; }
                case "SUBTITLE":{ ChatUtil.sendTitle(p, "", message);break; }
                case "ACTIONBAR":{ ChatUtil.sendActionbar(p, ChatUtil.fixColor(message));break; }
                case "CHAT":{ p.sendMessage(ChatUtil.fixColor(message));break; }
            }
        }else{
            sender.sendMessage(ChatUtil.fixColor(message));
        }
    }
}
