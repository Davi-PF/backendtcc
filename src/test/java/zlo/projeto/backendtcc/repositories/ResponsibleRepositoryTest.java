package zlo.projeto.backendtcc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import zlo.projeto.backendtcc.model.Responsible;
import zlo.projeto.backendtcc.services.ResponsibleServices;

public class ResponsibleRepositoryTest {

    @Mock
    private ResponsibleRepository responsibleRepository;

    @InjectMocks
    private ResponsibleServices responsibleService; // Se você tiver uma classe de serviço

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindResponsiblesByName() {
        // Arrange: Crie um responsável simulado
        Responsible responsible = new Responsible();
        responsible.setCpfRes("12345678900");
        responsible.setNomeRes("Maria");

        // Crie uma página com o responsável
        Page<Responsible> page = new PageImpl<>(Collections.singletonList(responsible));

        // Simule o comportamento do repositório
        when(responsibleRepository.findResponsiblesByName("Maria", PageRequest.of(0, 10))).thenReturn(page);

        // Act: Busque responsáveis pelo nome
        Page<Responsible> result = responsibleRepository.findResponsiblesByName("Maria", PageRequest.of(0, 10));

        // Assert: Verifique se o responsável foi encontrado
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNomeRes()).isEqualTo("Maria");
    }

    @Test
    public void testFindResponsibleEmergPhoneByCpfDep() {
        // Arrange
        String cpfDep = "11122233344";
        String contatoEmerg = "999999999";

        // Simule o comportamento do repositório
        when(responsibleRepository.findResponsibleEmergPhoneByCpfDep(cpfDep)).thenReturn(contatoEmerg);

        // Act
        String result = responsibleRepository.findResponsibleEmergPhoneByCpfDep(cpfDep);

        // Assert
        assertThat(result).isEqualTo(contatoEmerg);
    }

    @Test
    public void testFindResponsiblesCpfAndName() {
        // Arrange
        String email = "maria@example.com";
        String senha = "senha123";
        Object[] data = new Object[]{"12345678900", "Maria"};

        // Simule o comportamento do repositório
        when(responsibleRepository.findResponsiblesCpfAndName(email, senha)).thenReturn(Collections.singletonList(data));

        // Act
        List<Object[]> result = responsibleRepository.findResponsiblesCpfAndName(email, senha);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0)[0]).isEqualTo("12345678900");
        assertThat(result.get(0)[1]).isEqualTo("Maria");
    }

    @Test
    public void testFindResponsibleByTelefone() {
        // Arrange
        Responsible responsible = new Responsible();
        responsible.setCpfRes("12345678900");
        responsible.setNomeRes("Maria");
        responsible.setContato1Res("999999999");

        // Simule o comportamento do repositório
        when(responsibleRepository.findResponsibleByTelefone("999999999")).thenReturn(Optional.of(responsible));

        // Act
        Optional<Responsible> result = responsibleRepository.findResponsibleByTelefone("999999999");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNomeRes()).isEqualTo("Maria");
    }

    @Test
    public void testFindResponsibleByEmail() {
        // Arrange
        Responsible responsible = new Responsible();
        responsible.setCpfRes("12345678900");
        responsible.setNomeRes("Maria");
        responsible.setEmailRes("maria@example.com");

        // Simule o comportamento do repositório
        when(responsibleRepository.findResponsibleByEmail("maria@example.com")).thenReturn(Optional.of(responsible));

        // Act
        Optional<Responsible> result = responsibleRepository.findResponsibleByEmail("maria@example.com");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNomeRes()).isEqualTo("Maria");
    }
}
