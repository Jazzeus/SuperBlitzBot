package listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RoleListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        Message msg = event.getMessage();
        Guild guild = event.getGuild();
        // getRaw = originale Nachricht
        String command = msg.getContentRaw();
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();
        User user = member.getUser();
        boolean hatRolleSchon = false;
        allRoles(msg, user, guild, channel);

        // Splitted nach dem Leerzeichen
        String[] argumenteFRolle = command.split(" ");
        if (argumenteFRolle[0].equals("°role")) {
            if (!(argumenteFRolle[1].equals("get") || argumenteFRolle[1].equals("remove"))) {
                return;
            }
            System.out.println(member.getRoles());
            if (argumenteFRolle.length < 3) {
                System.out.println("Keine Rolle angegeben!");
                fehlermeldungen("https://media.giphy.com/media/hLwSzlKN8Fi6I/giphy.gif", "Keine Rolle angegeben!", user, channel);
                // return, damit es nicht mehr alles überpfrüft und das Array nicht out of bounds ist
                return;
            }
            for (int i = 3; i < argumenteFRolle.length; i++) {
                argumenteFRolle[2] = argumenteFRolle[2] + " " + argumenteFRolle[i];
            }
            // ignoreCase, missachtet die Groß & Kleinschreibung
            // RolesByName = Rolle wird anhand des Namens "geholt"
            List<Role> listeFRollen = guild.getRolesByName(argumenteFRolle[2], true);
            System.out.println(guild.getRolesByName(argumenteFRolle[2], true));
            // Wenn .size() = 0 --> liste leer
            if (listeFRollen.size() == 0 && (argumenteFRolle[1].equals("get") ^ argumenteFRolle[1].equals("remove"))) {
                fehlermeldungen("https://media.giphy.com/media/TqiwHbFBaZ4ti/giphy.gif", "Keine Rolle mit diesem Name gefunden!", user, channel);
                System.out.println(guild.getRolesByName(argumenteFRolle[2], true));
                // NEU!
            }
            if (member.getRoles().contains(listeFRollen.get(0)) && argumenteFRolle[1].equals("get")) {
                fehlermeldungen("https://media.giphy.com/media/1LYS8RmnsFwg8/giphy.gif", "Du hast diese Rolle (" + listeFRollen.get(0).getName() + ") schon!", user, channel);
            } else if (listeFRollen.size() == 1) {
                if (argumenteFRolle[1].equals("get")) {
                    // wenn da ein Fehler passiert...
                    try {
                        guild.addRoleToMember(member, listeFRollen.get(0)).queue();
                        bestätigungGet("https://media.giphy.com/media/ej0cjmiFD525JgbMsL/giphy.gif", "Rolle " + listeFRollen.get(0).getName() + " wurde dir hinzugefügt!", user, channel);
                        hatRolleSchon = true;
                    } catch (HierarchyException fehlerZuHoch) {
                        System.out.println("Sie können keine Rolle ändern, deren höchste Rolle höher oder gleich hoch ist wie die eigene!");
                        fehlermeldungen("https://media.giphy.com/media/TqiwHbFBaZ4ti/giphy.gif", "Du kannst keine Rolle ändern, die höher oder gleich weit oben in der Hierarchie ist, wie Deine. \nOder der Bot steht in der Hierarchie nicht über der genannten Rolle (" + listeFRollen.get(0).getName() + ")!", user, channel);
                    }
                } else if (argumenteFRolle[1].equals("remove")) {
                    // .get(0) "Holt sich" Rolle an der ersten Listenstelle
                    if (member.getRoles().contains(listeFRollen.get(0))) {
                        try {
                            guild.removeRoleFromMember(member, listeFRollen.get(0)).queue();
                            bestätigungRemove("https://cdn.dribbble.com/users/1483888/screenshots/6101062/success_animation.gif", "Rolle " + listeFRollen.get(0).getName() + " wurde dir entfernt!", user, channel);
                        } catch (HierarchyException fehlerZuHoch) {
                            System.out.println("Sie können keine Rolle ändern, deren höchste Rolle höher oder gleich hoch ist wie die eigene!");
                            fehlermeldungen("https://media.giphy.com/media/TqiwHbFBaZ4ti/giphy.gif", "Du kannst keine Rolle ändern, die höher oder gleich weit oben in der Hierarchie ist, wie Deine. \nOder der Bot steht in der Hierarchie nicht über der genannten Rolle (" + listeFRollen.get(0).getName() + ")!", user, channel);
                        }
                    } else {
                        System.out.println("Du hast diese Rolle nicht!");
                        fehlermeldungen("https://media.giphy.com/media/FlWpltZ9OOcUg/giphy.gif", "Du hast diese Rolle nicht und kannst sie somit nicht entfernen!", user, channel);
                    }
                } else {
                    System.out.println("Nichts richtíges eingegeben");
                    fehlermeldungen("https://media.giphy.com/media/YyKPbc5OOTSQE/giphy.gif", "Keine vollständige Eingabe, prüfe noch mal deine Rechtschreibung und die Eingabe!", user, channel);
                }
            } else {
                System.out.println("Welche Rolle?");
            }
        }
    }

    private void fehlermeldungen(String gif, String text, User user, MessageChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.red);
        embedBuilder.setTitle("FEHLER");
        embedBuilder.addField(text, "https://discordapp.com/users/601715164835741696", false);
        embedBuilder.setDescription(user.getAsMention() + "\n");
        embedBuilder.setThumbnail(gif);
        embedBuilder.setFooter("Angefragt von: " + user.getAsTag(), user.getAvatarUrl());
        channel.sendMessage(embedBuilder.build()).queue();
    }

    private void bestätigungGet(String gif, String text, User user, MessageChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.green);
        embedBuilder.setTitle("ERFOLG! ROLLE HINZUGEFÜGT!");
        embedBuilder.addField(text, "https://discordapp.com/users/601715164835741696", false);
        embedBuilder.setDescription(user.getAsMention() + "\n");
        embedBuilder.setThumbnail(gif);
        embedBuilder.setFooter("Angefragt von: " + user.getAsTag(), user.getAvatarUrl());
        channel.sendMessage(embedBuilder.build()).queue();
    }

    private void bestätigungRemove(String gif, String text, User user, MessageChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.green);
        embedBuilder.setTitle("ERFOLG! ROLLE ENTFERNT!");
        embedBuilder.addField(text, ""/*"https://discordapp.com/users/601715164835741696"*/, false);
        embedBuilder.setDescription(user.getAsMention() + "\n");
        embedBuilder.setThumbnail(gif);
        embedBuilder.setFooter("Angefragt von: " + user.getAsTag(), user.getAvatarUrl());
        channel.sendMessage(embedBuilder.build()).queue();
    }

    private void allRoles(Message msg, User user, Guild guild, MessageChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (msg.getContentRaw().equals("°roles")) {
            String allRoles = "";
            for (Role role : guild.getRoles()) {
                allRoles = allRoles + "\n" + role.getAsMention();
            }
            embedBuilder.setColor(Color.blue);
            embedBuilder.setTitle("ALLE ROLLEN AUF " + guild.getName());
            embedBuilder.addField("", ""/*"https://discordapp.com/users/601715164835741696"*/, false);
            embedBuilder.setDescription(allRoles);
            embedBuilder.setThumbnail(guild.getIconUrl());
            embedBuilder.setFooter("Angefragt von: " + user.getAsTag(), user.getAvatarUrl());
            channel.sendMessage(embedBuilder.build()).queue();
        }
    }
}


