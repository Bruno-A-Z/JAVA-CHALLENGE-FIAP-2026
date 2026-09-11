package br.com.fiap.challengeClyvo.dto.response;

import br.com.fiap.challengeClyvo.entity.Pet;
import br.com.fiap.challengeClyvo.entity.Veterinario;
import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import java.time.LocalDateTime;

public class AgendamentoResponseDTO {

    private Long id;
    private LocalDateTime dataHora;
    private Long idPet;
    private Long idVeterinario;
    private StatusAgendamento status;
    private String motivo;

    public AgendamentoResponseDTO(Long id, LocalDateTime dataHora, Long idPet, Long idVeterinario, StatusAgendamento status, String motivo) {
        this.id = id;
        this.dataHora = dataHora;
        this.idPet = idPet;
        this.idVeterinario = idVeterinario;
        this.status = status;
        this.motivo = motivo;
    }

    public Long getId() { return id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public Long getIdPet() {
        return idPet;
    }
    public Long getIdVeterinario() {
        return idVeterinario;
    }
    public StatusAgendamento getStatus() { return status; }
    public String getMotivo() { return motivo; }
}