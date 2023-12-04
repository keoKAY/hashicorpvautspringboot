package anuznomii.lol.workingwithvault.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {
    private String username;
    private String password;
}
