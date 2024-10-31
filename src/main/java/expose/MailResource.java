package expose;

import expose.dto.EmailDto;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import service.MailService;


@Path("/email")
public class MailResource {

    @Inject
    MailService mailService;

    @POST
    @Path("/send")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public  Uni<EmailDto.EmailResponseDto> sendDynamicEmail(
            EmailDto.Request mailDTO) {
        return mailService.sendEmail(mailDTO);
    }

    @POST
    @Path("/send-body")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public  Uni<EmailDto.EmailResponseDto> sendEmailBody(EmailDto.EmailBodyDto request) {
        return mailService.sendEmailBody(request);
    }

    @POST
    @Path("/multiple")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public  Uni<EmailDto.EmailResponseDto> sendEmailToMultipleRecipients(EmailDto.EmailMultipleDto request) {
        return mailService.sendEmailToMultipleRecipients(request);
    }
}

