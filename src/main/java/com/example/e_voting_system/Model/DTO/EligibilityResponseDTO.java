package com.example.e_voting_system.Model.DTO;

public class EligibilityResponseDTO {
    private boolean eligible;

    public EligibilityResponseDTO(boolean eligible) {
        this.eligible = eligible;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }
}
