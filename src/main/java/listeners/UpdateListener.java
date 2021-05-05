package listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class UpdateListener extends ListenerAdapter {
    @Override
    public void onMessageUpdate(MessageUpdateEvent updateEvent) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Message msg = updateEvent.getMessage();
        MessageChannel channel = updateEvent.getChannel();
//        System.out.println("AUSGEFÜHRT");
        msg.addReaction("U+1F3C4").queue();
        User user = updateEvent.getAuthor();
//        channel.sendMessage(user.getAsMention() + " changed his message. ").queue();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle("TEST SUPERBLITZ");
        embedBuilder.setDescription("Nachricht erfolgreich geändert!");
        embedBuilder.setThumbnail("https://media.giphy.com/media/jTOlzdqrsVlZlxWqP5/giphy.gif");
        embedBuilder.setFooter(user.getAsTag(), user.getAvatarUrl());
        channel.sendMessage(embedBuilder.build()).queue();
    }
}

