package com.metaway.petshopkeycloak.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

public class KeycloakConfig { // SOMENTE PARA CADASTRAR NOVOS USUÁRIOS
    static Keycloak keycloak = null;
    final static String serverUrl = "http://localhost:8080";
    public final static String realm = "Petshop";
    final static String clientId = "petshop-rest-api";
    //final static String clientSecret = "";
    final static String userName = "000.000.000-00"; // no keycloak -> ASIGN ROLES (ROLES de CRUD) para este usuário
    final static String password = "123456";


    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    //.clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                    .build();
        }
        return keycloak;
    }
}
