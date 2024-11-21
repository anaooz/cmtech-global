package br.com.cmtech.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Table(name = "Baterias")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Bateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bateria_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estacao_id", referencedColumnName = "estacao_id", nullable = false)
    private EstacaoCarregamento estacaoCarregamento;

    @Column(name = "status", length = 20)
    @NotBlank(message = "O status não pode estar em branco.")
    @Size(max = 20, message = "O status deve ter no máximo 20 caracteres.")
    private String status;

    @Column(name = "nivel_energia")
    @NotNull(message = "O nível de energia é obrigatório.")
    private Double nivelEnergia;

}
