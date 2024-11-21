package br.com.cmtech.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Registros_Carregamento")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RegistroCarregamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registro_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", referencedColumnName = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "estacao_id", referencedColumnName = "estacao_id", nullable = false)
    private EstacaoCarregamento estacaoCarregamento;

    @Column(name = "data_hora_inicio", nullable = false)
    @NotNull(message = "A data e hora de início são obrigatórias.")
    @PastOrPresent(message = "A data e hora de início devem ser no passado ou presente.")
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    @FutureOrPresent(message = "A data e hora de fim devem ser no presente ou futuro.")
    private LocalDateTime dataHoraFim;

    @Column(name = "energia_utilizada")
    @DecimalMin(value = "0.0", message = "A energia utilizada não pode ser negativa.")
    private Double energiaUtilizada;

}
