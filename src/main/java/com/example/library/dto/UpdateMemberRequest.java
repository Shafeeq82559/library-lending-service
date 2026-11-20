package com.example.library.dto;

public record UpdateMemberRequest(
        String fullName,
        String email,
        String phone
) {
}
