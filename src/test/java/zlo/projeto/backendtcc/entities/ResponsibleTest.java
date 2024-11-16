package zlo.projeto.backendtcc.entities;

import org.junit.jupiter.api.Test;
import zlo.projeto.backendtcc.entities.responsible.Responsible;

import static org.junit.jupiter.api.Assertions.*;

class ResponsibleTest {

    @Test
    void testGettersAndSetters() {
        Responsible responsible = new Responsible();

        responsible.setCpfRes("12345678900");
        responsible.setNomeRes("Nome de Teste");
        responsible.setIdadeRes(40);
        responsible.setContato1Res("999999999");
        responsible.setContato2Res("888888888");
        responsible.setContato3Res("777777777");
        responsible.setPlanoAssinado(1);
        responsible.setEmailRes("email@teste.com");
        responsible.setEnderecoIdRes(100);
        responsible.setRgRes("1234567");
        responsible.setSenhaRes("securePassword");

        assertEquals("12345678900", responsible.getCpfRes());
        assertEquals("Nome de Teste", responsible.getNomeRes());
        assertEquals(40, responsible.getIdadeRes());
        assertEquals("999999999", responsible.getContato1Res());
        assertEquals("888888888", responsible.getContato2Res());
        assertEquals("777777777", responsible.getContato3Res());
        assertEquals(1, responsible.getPlanoAssinado());
        assertEquals("email@teste.com", responsible.getEmailRes());
        assertEquals(100, responsible.getEnderecoIdRes());
        assertEquals("1234567", responsible.getRgRes());
        assertEquals("securePassword", responsible.getSenhaRes());
    }

    @Test
    void testEqualsAndHashCode() {
        Responsible responsible1 = new Responsible();
        responsible1.setCpfRes("12345678900");
        responsible1.setNomeRes("Nome de Teste");
        responsible1.setIdadeRes(40);

        Responsible responsible2 = new Responsible();
        responsible2.setCpfRes("12345678900");
        responsible2.setNomeRes("Nome de Teste");
        responsible2.setIdadeRes(40);

        assertEquals(responsible1, responsible2);
        assertEquals(responsible1.hashCode(), responsible2.hashCode());

        responsible2.setNomeRes("Nome de teste 2");
        assertNotEquals(responsible1, responsible2);
        assertNotEquals(responsible1.hashCode(), responsible2.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        Responsible responsible = new Responsible();
        assertNotNull(responsible);
    }
}
