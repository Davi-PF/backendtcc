package zlo.projeto.backendtcc.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@JsonPropertyOrder({"id", "tokenDispositivo", "dataCadastro"})
public class DeviceStorageVO extends RepresentationModel<DeviceStorageVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2093471018037172557L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("tokenDispositivo")
    private String tokenDispositivo;

    @JsonProperty("dataCadastro")
    private Instant dataCadastro;

    public DeviceStorageVO() {
    }

    public DeviceStorageVO(Integer id, String tokenDispositivo, Instant dataCadastro) {
        this.id = id;
        this.tokenDispositivo = tokenDispositivo;
        this.dataCadastro = dataCadastro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTokenDispositivo() {
        return tokenDispositivo;
    }

    public void setTokenDispositivo(String tokenDispositivo) {
        this.tokenDispositivo = tokenDispositivo;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceStorageVO that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(id, that.id) && Objects.equals(tokenDispositivo, that.tokenDispositivo) && Objects.equals(dataCadastro, that.dataCadastro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, tokenDispositivo, dataCadastro);
    }
}