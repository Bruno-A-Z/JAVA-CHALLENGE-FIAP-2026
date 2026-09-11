package br.com.fiap.challengeClyvo.dto.response;

public class LoginResponseDTO {

    private String token;
    private String tipo = "Bearer";

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public String getTipo() { return tipo; }
}