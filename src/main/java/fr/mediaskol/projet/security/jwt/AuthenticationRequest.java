package fr.mediaskol.projet.security.jwt;


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
