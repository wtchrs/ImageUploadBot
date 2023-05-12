package wtchrs.imageuploadbot.discord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import wtchrs.imageuploadbot.service.ImageStoreService;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUploadListener extends ListenerAdapter {

    private final ImageStoreService imageStoreService;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("upload")) return;

        Member member = event.getMember();

        String name = event.getOption("name").getAsString();
        Message.Attachment image = event.getOption("image").getAsAttachment();

        if (!image.isImage()) {
            event.reply("Only image is allowed.").setEphemeral(true).queue();
            return;
        }

        if (!StringUtils.hasText(name)) {
            event.reply("Empty name field is not allowed.").setEphemeral(true).queue();
            return;
        }

        try {
            imageStoreService.saveImage(member.getIdLong(), name, image);
            event.reply("Successfully uploaded!").setEphemeral(true).queue();
        } catch (IOException e) {
            event.reply("Something went wrong with uploading the image...").setEphemeral(true).queue();
        }
    }
}
