package com.example.e_voting_system.Model.DTO;

public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String email;
    private Long roleId;

    public JwtResponseDTO(String token, Long userId, String email, Long roleId) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.roleId = roleId;
    }
}
