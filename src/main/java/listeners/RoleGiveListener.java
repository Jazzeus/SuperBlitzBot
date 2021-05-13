package listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.List;

public class RoleGiveListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();
        Guild guild = event.getJDA().getGuildById(event.getGuild().getId());
        String input = msg.getContentRaw();
        System.out.println("GUILD :"+guild);
        System.out.println("OWNER: " +guild.getOwner());
        System.out.println("GUILD ID: "+guild.getId());
        System.out.println("All Members:");
                    for (Member member1 : guild.getMembers()) {
                        System.out.println(member1.getId());
                    }
        String[] argumentsFRole = input.split(" ");
//        System.out.println(input);
        if (argumentsFRole[0].equals("°role")) {
            if (argumentsFRole.length < 4) {
                System.out.println("Fehler, nicht vollständig");
                return;
            }
            for (int i = 4; i < argumentsFRole.length; i++) {
                argumentsFRole[3] = argumentsFRole[3] + " " + argumentsFRole[i];
            }
            List<Role> listFRoles = guild.getRolesByName(argumentsFRole[3], true);

            if (argumentsFRole[1].equals("give")){
                String userTag = argumentsFRole[2];
                if (userTag.contains("<@!") && userTag.endsWith(">")){
                    userTag = userTag.replace("<@!", "");
                    userTag = userTag.replace(">", "");
                    long ID = Long.parseLong(userTag);
//                    System.out.println(ID);
                    member = guild.getMemberById(ID);
                    System.out.println("All Members:");
                    for (Member member1 : guild.getMembers()) {
                        System.out.println(member1.getId());
                    }
//                    System.out.println(memberPing);A
                    guild.addRoleToMember(member, listFRoles.get(0)).queue();
                }
            }
        }
    }
}
