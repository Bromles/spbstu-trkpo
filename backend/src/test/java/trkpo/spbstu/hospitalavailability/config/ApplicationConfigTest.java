package trkpo.spbstu.hospitalavailability.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationConfigTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Test
    void testGetRestTemplate() {
        when(restTemplateBuilder.rootUri("https://gorzdrav.spb.ru/_api/api/v2/")).thenReturn(restTemplateBuilder);
        ApplicationConfig config = new ApplicationConfig();
        config.getRestTemplate(restTemplateBuilder);
        assertThat(restTemplateBuilder).isNotNull();
    }

    @Test
    void testGetTransactionTemplate() {
        ApplicationConfig config = new ApplicationConfig();
        TransactionTemplate result = config.getTransactionTemplate(transactionManager);
        assertThat(result).isNotNull();
    }

    @Test
    void testGetTransactionTemplate2() {
        ApplicationConfig config = new ApplicationConfig();
        TransactionTemplate result = config.getTransactionTemplate2(transactionManager);
        assertThat(result).isNotNull();
        assertThat(result.getPropagationBehavior()).isEqualTo(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }
}

