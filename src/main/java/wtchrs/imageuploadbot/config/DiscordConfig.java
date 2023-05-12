package wtchrs.imageuploadbot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DiscordConfig {

    @Value("${discord.bot.token}")
    private String token;

    private final List<EventListener> eventListeners;

    @Bean
    public JDA jdaApi() {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        eventListeners.forEach(jdaBuilder::addEventListeners);

        JDA jda = jdaBuilder.build();

        // TODO: add commands that show member's all uploaded images and that delete images.

        jda.updateCommands().addCommands(
                Commands.slash("upload", "upload an image for using.")
                        .addOption(OptionType.STRING, "name", "Alias for the image.", true)
                        .addOption(OptionType.ATTACHMENT, "image", "Image for using.", true),
                Commands.slash("show", "Show an image.")
                        .addOption(OptionType.STRING, "name", "Alias for the image to show.", true),
                Commands.slash("delete", "Delete an image.")
        ).queue();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            log.error("Error with starting JDA: ", e);
        }

        return jda;
    }
}
