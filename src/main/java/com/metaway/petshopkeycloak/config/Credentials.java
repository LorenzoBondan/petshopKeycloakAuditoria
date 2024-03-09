package com.metaway.petshopkeycloak.config;

import org.keycloak.representations.idm.CredentialRepresentation;

public class Credentials { // SOMENTE PARA CADASTRAR USUÁRIOS NOVOS
    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
