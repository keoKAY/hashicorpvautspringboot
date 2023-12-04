package anuznomii.lol.workingwithvault.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VaultRestController {

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

    @GetMapping("/working-cubbyhole")
    public String getToken(){

//        Getting the token from the cubbyhole
        VaultResponse response = vaultTemplate.read("cubbyhole/girlfriendcredentials");
        System.out.println("response is: "+response);
        assert response != null; // this is to make sure that the response is not null
        System.out.println("response is: "+response.getData());


//        List all the secrets reside in the cubbyhole
        List<String> secretNames = vaultTemplate.list("cubbyhole");
        assert secretNames != null;
        for(String secretName: secretNames){
            System.out.println("secret name is: "+secretName);
        }


        return " this is the token endpoint!";
    }


    @GetMapping("/creating-token")
    public String createToken(){




        Map<String , Object > params = new HashMap<>();
        params.put("policies", "default");
        params.put("ttl", "1h");
        params.put("client-token", "my-wonderful-token");


        VaultResponse response
                = vaultTemplate.write("auth/token/create",
                params) ;

//        print the response
        System.out.println("response is: "+response.getAuth());
/*
*  client_token , accessor , policies , token_policies , metadata , lease_duration , renewable
* */
        System.out.println("response is: "+response.getAuth().get("client_token"));
        System.out.println("response is: "+response.getData());
        return "this is the create token endpoint";
    }

}
