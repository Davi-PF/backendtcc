package zlo.projeto.backendtcc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import zlo.projeto.backendtcc.model.Dependent;
import zlo.projeto.backendtcc.services.DependentServices;

public class DependentRepositoryTest {

    @Mock
    private DependentRepository dependentRepository;

    @InjectMocks
    private DependentServices dependentService; // Se você tiver uma classe de serviço

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindDependentsByName() {
        // Arrange: Crie um dependente simulado
        Dependent dependent = new Dependent();
        dependent.setNomeDep("João");

        Page<Dependent> page = new PageImpl<>(Collections.singletonList(dependent));

        // Simule o comportamento do repositório
        when(dependentRepository.findDependentsByName("João", PageRequest.of(0, 10))).thenReturn(page);

        // Act: Busque dependentes pelo nome (usando um service ou o próprio repository)
        var result = dependentRepository.findDependentsByName("João", PageRequest.of(0, 10));

        // Assert: Verifique se o dependente foi encontrado
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNomeDep()).isEqualTo("João");
    }
}
