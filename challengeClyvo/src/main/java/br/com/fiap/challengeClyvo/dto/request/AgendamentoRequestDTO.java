package br.com.fiap.challengeClyvo.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AgendamentoRequestDTO {

    @NotNull(message = "A data e hora são obrigatórias.")
    @Future(message = "O agendamento deve ser em uma data futura.")
    private LocalDateTime dataHora;

    @NotNull(message = "O pet é obrigatório.")
    private Long idPet;

    @NotNull(message = "O veterinário é obrigatório.")
    private Long idVeterinario;

    private String motivo;

    public AgendamentoRequestDTO() {
    }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Long getIdPet() { return idPet; }
    public void setIdPet(Long idPet) { this.idPet = idPet; }

    public Long getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(Long idVeterinario) { this.idVeterinario = idVeterinario; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}