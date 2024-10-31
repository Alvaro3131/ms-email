package junit5;

import expose.MailResource;
import expose.dto.EmailDto;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import service.MailService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MailResourceTest {

    @InjectMocks
    private MailResource mailResource;

    @Mock
    private MailService mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para sendDynamicEmail
    @Test
    public void testSendDynamicEmail() {
        // Preparar datos de prueba
        EmailDto.Request request = new EmailDto.Request();
        request.setEmail("test@example.com");
        request.setSubject("Test Subject");
        request.setBody("Test Body");
        // Los archivos adjuntos pueden ser null o se pueden mockear si es necesario

        // Preparar respuesta esperada
        EmailDto.EmailResponseDto expectedResponse = EmailDto.EmailResponseDto.success();

        // Simular respuesta del servicio
        when(mailService.sendEmail(request)).thenReturn(Uni.createFrom().item(expectedResponse));

        // Llamar al método
        Uni<EmailDto.EmailResponseDto> responseUni = mailResource.sendDynamicEmail(request);

        // Verificar la respuesta
        EmailDto.EmailResponseDto actualResponse = responseUni.await().indefinitely();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.isSuccess(), actualResponse.isSuccess());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());

        // Verificar interacción con el servicio
        verify(mailService, times(1)).sendEmail(request);
    }

    // Test para sendEmailBody
    @Test
    public void testSendEmailBody() {
        // Preparar datos de prueba
        EmailDto.EmailBodyDto request = new EmailDto.EmailBodyDto();
        request.setEmail("test@example.com");
        request.setSubject("Test Subject");
        request.setBody("Test Body");

        // Preparar respuesta esperada
        EmailDto.EmailResponseDto expectedResponse = EmailDto.EmailResponseDto.success();

        // Simular respuesta del servicio
        when(mailService.sendEmailBody(request)).thenReturn(Uni.createFrom().item(expectedResponse));

        // Llamar al método
        Uni<EmailDto.EmailResponseDto> responseUni = mailResource.sendEmailBody(request);

        // Verificar la respuesta
        EmailDto.EmailResponseDto actualResponse = responseUni.await().indefinitely();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.isSuccess(), actualResponse.isSuccess());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());

        // Verificar interacción con el servicio
        verify(mailService, times(1)).sendEmailBody(request);
    }

    // Test para sendEmailToMultipleRecipients
    @Test
    public void testSendEmailToMultipleRecipients() {
        // Preparar datos de prueba
        EmailDto.EmailMultipleDto request = new EmailDto.EmailMultipleDto();
        request.setEmails(Arrays.asList("user1@example.com", "user2@example.com"));
        request.setSubject("Test Subject");
        request.setBody("Test Body");
        // Los archivos adjuntos pueden ser null o se pueden mockear si es necesario

        // Preparar respuesta esperada
        EmailDto.EmailResponseDto expectedResponse = EmailDto.EmailResponseDto.success();

        // Simular respuesta del servicio
        when(mailService.sendEmailToMultipleRecipients(request)).thenReturn(Uni.createFrom().item(expectedResponse));

        // Llamar al método
        Uni<EmailDto.EmailResponseDto> responseUni = mailResource.sendEmailToMultipleRecipients(request);

        // Verificar la respuesta
        EmailDto.EmailResponseDto actualResponse = responseUni.await().indefinitely();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.isSuccess(), actualResponse.isSuccess());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());

        // Verificar interacción con el servicio
        verify(mailService, times(1)).sendEmailToMultipleRecipients(request);
    }
}
