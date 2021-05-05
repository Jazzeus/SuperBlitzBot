import listeners.PingListener;
import listeners.RoleGiveListener;
import listeners.RoleListener;
import listeners.UpdateListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class TestBot1 {

    private static JDA instance;

    public static JDA getInstance(){
        return instance;
    }

    public static void main(String[] args) throws LoginException {
//        if (args.length < 1) {
//            System.out.println("You have to provide a token as first argument!");
//            System.exit(1);
//        }
        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.

        JDA jda = JDABuilder.create("ODM3NzIyMTQ1MzE5ODEzMTkx.YIwriw.AfxIqMJa4N6NPCUwF5FHWk-N6vc", GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new PingListener(), new UpdateListener(), new RoleListener(), new RoleGiveListener())
                .setActivity(Activity.streaming("TheRealOne | °ping", "https://twitch.tv/therealone_tv"))
//                .setActivity(Activity.competing("TheRealOne"))
                .build();
        instance = jda;

    }
}
