package service;

import expose.dto.EmailDto;
import io.smallrye.mutiny.Uni;

public interface MailService {
    Uni<EmailDto.EmailResponseDto> sendEmail(EmailDto.Request mailDTO);
    Uni<EmailDto.EmailResponseDto> sendEmailBody(EmailDto.EmailBodyDto mailDTO);
    Uni<EmailDto.EmailResponseDto>  sendEmailToMultipleRecipients(EmailDto.EmailMultipleDto mailDTO);
}
