package trkpo.spbstu.hospitalavailability.utils;

import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.entity.Client;

import java.util.Random;
import java.util.UUID;

public class ClientUtils {

    public static Client getClient(){
        UUID expectedKeycloakId = UUID.randomUUID();
        String expectedEmail = "test@example.com";
        Client client = new Client();
        client.setEmail(expectedEmail);
        client.setKeycloakId(expectedKeycloakId);
        client.setId(RandomUtils.nextLong());
        return client;
    }
}
