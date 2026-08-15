package com.covira.backend.dto;

public record CandidateResponseDto(
        Long id,
        Integer questionNumber,
        String question,
        String answer,
        String videoUrl,
        String duration,
        String submittedAt
) {
}