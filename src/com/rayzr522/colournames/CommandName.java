
package com.rayzr522.colournames;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandName implements CommandExecutor {

	// private Pattern pattern = Pattern.compile("&[0-9a-f]").matcher("test").;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command!");
			return true;
		}

		Player p = (Player) sender;

		if (!p.hasPermission(Config.PERMISSION)) {
			p.sendMessage(Config.msg("no-permission"));
			return true;
		}

		if (args.length < 1) {

			p.sendMessage(Config.msg("usage", "&a&l", ChatColor.GREEN + "" + ChatColor.BOLD + p.getName()));
			return true;

		}

		int response = checkColorCodes(args[0]);

		if (response == 2) {

			p.sendMessage(Config.msg("invalid-syntax", "&a&l", ChatColor.GREEN + "" + ChatColor.BOLD + p.getName()));
			return true;

		}

		else if (response == 1) {

			p.sendMessage(Config.msg("only-one-each"));
			return true;

		} else {

			PlayerData data = Config.getPlayer(p);

			data.setColor(args[0]);
			data.updateName(p);

			Config.setPlayer(p, data);

			p.sendMessage(Config.msg("name-set", p.getDisplayName()));

			return true;

		}

	}

	private int checkColorCodes(String text) {

		if (text.replaceAll("&[a-f0-9klmnor]", "").length() > 0) { return 2; }

		if (Regex.numMatches(text, "&[a-f0-9]") > 1 || Regex.numMatches(text, "&[klmnor]") > 1) { return 1; }

		return 0;

	}

}