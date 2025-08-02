package fr.mediaskol.projet.dto.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of="pseudo")
public class AuthenticationRequest {

    private String pseudo;
    private String password;
}
