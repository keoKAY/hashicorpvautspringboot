package anuznomii.lol.workingwithvault.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private VaultTemplate vaultTemplate;

    @GetMapping("/secret")
    public String getSecret() {
//        VaultResponse response = vaultTemplate.read("/v1/secret/metadata/database_credentials");

        VaultResponse response  = vaultTemplate.opsForKeyValue("secret",
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                .get("database_credentials");

        System.out.println("resposne is: "+response);
        System.out.println("resposne is: "+response.getData());
        System.out.println("Database Password is: "+response.getData().get("database_password"));

//        to create a simple secret
        Map<String, Object> secret = new HashMap<>();
        secret.put("username", "anuznomii");
        secret.put("password", "good night !!!");
        vaultTemplate.opsForKeyValue("secret",
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                .put("my-secret",  secret);

//        for deleting the secrets
        VaultKeyValueOperations ops = vaultTemplate
                .opsForKeyValue("secret",
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
//         if the secret exist then delete it
        if(ops.get("my-secret") != null) {
            ops.delete("my-secret");
            System.out.println("deleted the secret!!!");
        }else {
            System.out.println("secret does not exist!!!");
        }
return  "hello world ";
    }

}
