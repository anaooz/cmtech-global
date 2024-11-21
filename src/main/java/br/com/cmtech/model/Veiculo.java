package br.com.cmtech.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Table(name = "Veículos")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "veiculo_id")
    private Long id;

    @Column(name = "modelo_veiculo", length = 255)
    @NotBlank(message = "O modelo do veículo é obrigatório.")
    @Size(max = 255, message = "O modelo do veículo não pode ter mais de 255 caracteres.")
    private String modelo;

    @Column(name = "ano_veiculo")
    @NotNull(message = "O ano do veículo é obrigatório.")
    private Integer ano;

    @Column(name = "capacidade_bateria")
    @NotNull(message = "A capacidade da bateria é obrigatória.")
    private Double capacidadeBateria;
}
