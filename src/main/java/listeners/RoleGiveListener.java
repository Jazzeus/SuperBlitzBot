package listeners;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class RoleGiveListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        Message msg = event.getMessage();
        MessageChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        Member member = event.getMember();
        String input = msg.getContentRaw();

        String[] argumentsFRole = input.split(" ");
        System.out.println(input);
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
                    userTag.replace("<@!", "");
                }
            }
        }
    }
}
