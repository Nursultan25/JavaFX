package kg.itschool.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthorizationDetails {

    private String username;
    private String password;

}
