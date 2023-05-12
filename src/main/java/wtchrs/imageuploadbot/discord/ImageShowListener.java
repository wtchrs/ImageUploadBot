package wtchrs.imageuploadbot.discord;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import wtchrs.imageuploadbot.service.ImageStoreService;

import java.io.File;

@Component
@RequiredArgsConstructor
public class ImageShowListener extends ListenerAdapter {

    private final ImageStoreService imageStoreService;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("show")) return;

        Member member = event.getMember();
        String name = event.getOption("name").getAsString();

        if (!StringUtils.hasText(name)) {
            event.reply("Empty name field is not allowed.").queue();
            return;
        }

        try {
            File imageFile = imageStoreService.getImageFile(member.getIdLong(), name);
            event.reply("")
                    .addFiles(FileUpload.fromData(imageFile))
                    .setEmbeds(new EmbedBuilder().setImage("attachment://" + imageFile.getName()).build())
                    .queue();
        } catch (IllegalStateException e) {
            event.reply("Not found such image... Try again with the correct name.").setEphemeral(true).queue();
        }
    }
}
