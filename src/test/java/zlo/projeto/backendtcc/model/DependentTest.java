package zlo.projeto.backendtcc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DependentTest {

    private Dependent dependent;

    @BeforeEach
    public void setUp() {
        dependent = new Dependent();
        dependent.setCpfDep("123456789");
        dependent.setNomeDep("João Silva");
        dependent.setIdadeDep(12);
        dependent.setTipoSanguineo("O+");
        dependent.setLaudo("Nenhum");
        dependent.setGeneroDep("Masculino");
        dependent.setRgDep("987654321");
        dependent.setCpfResDep("11111111111");
        dependent.setPiTagIdDep(123);
        dependent.setCpfTerDep("22222222222");
        dependent.setIdCirurgiaDep(1);
        dependent.setIdScanDep(2);
    }

    @Test
    public void testGettersAndSetters() {
        // Verifica os getters
        assertThat(dependent.getCpfDep()).isEqualTo("123456789");
        assertThat(dependent.getNomeDep()).isEqualTo("João Silva");
        assertThat(dependent.getIdadeDep()).isEqualTo(12);
        assertThat(dependent.getTipoSanguineo()).isEqualTo("O+");
        assertThat(dependent.getLaudo()).isEqualTo("Nenhum");
        assertThat(dependent.getGeneroDep()).isEqualTo("Masculino");
        assertThat(dependent.getRgDep()).isEqualTo("987654321");
        assertThat(dependent.getCpfResDep()).isEqualTo("11111111111");
        assertThat(dependent.getPiTagIdDep()).isEqualTo(123);
        assertThat(dependent.getCpfTerDep()).isEqualTo("22222222222");
        assertThat(dependent.getIdCirurgiaDep()).isEqualTo(1);
        assertThat(dependent.getIdScanDep()).isEqualTo(2);
    }

    @Test
    public void testEqualsAndHashCode() {
        Dependent dependent2 = new Dependent();
        dependent2.setCpfDep("123456789");
        dependent2.setNomeDep("João Silva");
        dependent2.setIdadeDep(12);
        dependent2.setTipoSanguineo("O+");
        dependent2.setLaudo("Nenhum");
        dependent2.setGeneroDep("Masculino");
        dependent2.setRgDep("987654321");
        dependent2.setCpfResDep("11111111111");
        dependent2.setPiTagIdDep(123);
        dependent2.setCpfTerDep("22222222222");
        dependent2.setIdCirurgiaDep(1);
        dependent2.setIdScanDep(2);

        // Testa igualdade
        assertThat(dependent).isEqualTo(dependent2);

        // Testa hashCode
        assertThat(dependent.hashCode()).isEqualTo(dependent2.hashCode());
    }

    @Test
    public void testNotEquals() {
        Dependent differentDependent = new Dependent();
        differentDependent.setCpfDep("999999999");

        // Verifica que são diferentes
        assertThat(dependent).isNotEqualTo(differentDependent);
    }

    @Test
    public void testDefaultConstructor() {
        Dependent dependent = new Dependent();

        assertNull(dependent.getCpfDep());
        assertNull(dependent.getNomeDep());
        assertNull(dependent.getIdadeDep());
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        Dependent dependent = new Dependent();
        dependent.setCpfDep("123456789");

        // Serializa o objeto
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(dependent);

        // Desserializa o objeto
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        Dependent deserializedDependent = (Dependent) in.readObject();

        assertEquals(dependent, deserializedDependent);
    }

}
