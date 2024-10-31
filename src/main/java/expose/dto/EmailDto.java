package expose.dto;

import org.jboss.resteasy.reactive.multipart.FileUpload;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.Setter;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.util.List;

public class EmailDto {
    @Getter
    @Setter
    @RegisterForReflection
    public static class Request{
        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private String email;

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private String subject;

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private String body;

        @RestForm("files")
        List<FileUpload> files;
    }

    @Getter
    @Setter
    @RegisterForReflection
    public static class EmailBodyDto {

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private String email;

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private String subject;

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private String body;

    }

    @Getter
    @Setter
    @RegisterForReflection
    public static class EmailMultipleDto {

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private List<String> emails;  // Lista de destinatarios

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private String subject;

        @RestForm
        @PartType(MediaType.TEXT_PLAIN)
        private String body;

        @RestForm("files")
        List<FileUpload> files;  // Opcional: si deseas soportar archivos adjuntos también
    }

    @Getter
    @Setter
    @RegisterForReflection
    public static class EmailResponseDto {
        private boolean success;
        private String message;

        // Constructor para éxito
        public EmailResponseDto(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public static EmailResponseDto success() {
            return new EmailResponseDto(true, "Email sent successfully.");
        }

        public static EmailResponseDto error(String errorMessage) {
            return new EmailResponseDto(false, errorMessage);
        }
    }

    }
