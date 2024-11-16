package zlo.projeto.backendtcc.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;

public class NotificationStorageVOTest {

    @Test
    public void testConstrutorEGetters() {
        Long id = 1L;
        String titulo = "Teste de Título";
        String mensagem = "Esta é uma mensagem de teste.";
        ZonedDateTime dataEnvio = ZonedDateTime.now();
        Boolean lida = false;
        String cpfResponsavel = "12345678901";

        NotificationStorageVO notification = new NotificationStorageVO(
                id, titulo, mensagem, dataEnvio, lida, cpfResponsavel
        );

        assertEquals(id, notification.getId());
        assertEquals(titulo, notification.getTitulo());
        assertEquals(mensagem, notification.getMensagem());
        assertEquals(dataEnvio, notification.getDataEnvio());
        assertEquals(lida, notification.getLida());
        assertEquals(cpfResponsavel, notification.getCpfResponsavel());
    }

    @Test
    public void testSetters() {
        NotificationStorageVO notification = new NotificationStorageVO(
                null, null, null, null, null, null
        );

        Long id = 2L;
        String titulo = "Novo Título";
        String mensagem = "Nova mensagem.";
        ZonedDateTime dataEnvio = ZonedDateTime.now();
        Boolean lida = true;
        String cpfResponsavel = "09876543210";

        notification.setId(id);
        notification.setTitulo(titulo);
        notification.setMensagem(mensagem);
        notification.setDataEnvio(dataEnvio);
        notification.setLida(lida);
        notification.setCpfResponsavel(cpfResponsavel);

        assertEquals(id, notification.getId());
        assertEquals(titulo, notification.getTitulo());
        assertEquals(mensagem, notification.getMensagem());
        assertEquals(dataEnvio, notification.getDataEnvio());
        assertEquals(lida, notification.getLida());
        assertEquals(cpfResponsavel, notification.getCpfResponsavel());
    }
}
