package br.com.cmtech.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@Table(name = "Estacoes_Carregamento")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class EstacaoCarregamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estacao_id")
    private Long id;

    @Column(name = "localizacao", nullable = false, length = 100)
    @NotBlank(message = "A localização não pode estar em branco.")
    @Size(max = 100, message = "A localização deve ter no máximo 100 caracteres.")
    private String localizacao;

    @Column(name = "capacidade_maxima")
    @NotNull(message = "A capacidade máxima é obrigatória.")
    private Double capacidadeMaxima;
}
