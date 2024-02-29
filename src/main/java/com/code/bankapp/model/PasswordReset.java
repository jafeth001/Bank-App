package com.code.bankapp.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordReset {
    @Valid
    private String oldPassword;
    @NotNull(message = "new password is mandatory")
    @NotBlank(message = "new  password is mandatory")
    @Size(min = 4, max = 25, message = "password must be between 4 and 25 characters")
    private String newPassword;
    @NotNull(message = "confirm password is mandatory")
    @NotBlank(message = "confirm password is mandatory")
    private String confirmPassword;
}
