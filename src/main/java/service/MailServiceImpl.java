package service;


import expose.dto.EmailDto;
import io.quarkus.mailer.Attachment;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MailServiceImpl implements MailService  {
    @Inject
    ReactiveMailer reactiveMailer;

    @Override
    public Uni<EmailDto.EmailResponseDto> sendEmail(EmailDto.Request mailDTO) {
        Mail mail = Mail.withHtml(mailDTO.getEmail(), mailDTO.getSubject(), mailDTO.getBody());

        if (mailDTO.getFiles() != null && !mailDTO.getFiles().isEmpty()) {
            List<Attachment> attachments = mailDTO.getFiles().stream()
                    .map(file -> new Attachment(file.fileName(), file.uploadedFile().toFile(), file.contentType()))
                    .collect(Collectors.toList());
            mail.setAttachments(attachments);
        }

        return reactiveMailer.send(mail)
                .onItem().transform(v -> EmailDto.EmailResponseDto.success())
                .onFailure().recoverWithItem(err -> EmailDto.EmailResponseDto.error("Failed to send email: " + err.getMessage()));
    }

    @Override
    public Uni<EmailDto.EmailResponseDto> sendEmailBody(EmailDto.EmailBodyDto mailDTO) {
        Mail mail = Mail.withHtml(mailDTO.getEmail(), mailDTO.getSubject(), mailDTO.getBody());

        return reactiveMailer.send(mail)
                .onItem().transform(v -> EmailDto.EmailResponseDto.success())
                .onFailure().recoverWithItem(err -> EmailDto.EmailResponseDto.error("Failed to send email body: " + err.getMessage()));
    }

    @Override
    public Uni<EmailDto.EmailResponseDto> sendEmailToMultipleRecipients(EmailDto.EmailMultipleDto mailDTO) {
        // Iterar sobre la lista de destinatarios
        List<Uni<Void>> emailSends = mailDTO.getEmails().stream()
                .map(recipient -> {
                    Mail mail = Mail.withHtml(recipient, mailDTO.getSubject(), mailDTO.getBody());

                    // Adjuntar archivos si es necesario
                    if (mailDTO.getFiles() != null && !mailDTO.getFiles().isEmpty()) {
                        List<Attachment> attachments = mailDTO.getFiles().stream()
                                .map(file -> new Attachment(file.fileName(), file.uploadedFile().toFile(), file.contentType()))
                                .collect(Collectors.toList());
                        mail.setAttachments(attachments);
                    }

                    // Enviar el correo de manera individual para cada destinatario
                    return reactiveMailer.send(mail);
                })
                .collect(Collectors.toList());

        // Combinar todos los envíos y manejar los errores
        return Uni.combine().all().unis(emailSends)
                .collectFailures()  // Recoger fallos sin interrumpir el flujo
                .combinedWith(res->EmailDto.EmailResponseDto.success())
                .onFailure().recoverWithItem(err -> EmailDto.EmailResponseDto.error("Failed to send emails: " + err.getMessage()));
    }


}
