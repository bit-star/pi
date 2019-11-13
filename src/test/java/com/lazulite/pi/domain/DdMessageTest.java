package com.lazulite.pi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lazulite.pi.web.rest.TestUtil;

public class DdMessageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DdMessage.class);
        DdMessage ddMessage1 = new DdMessage();
        ddMessage1.setId(1L);
        DdMessage ddMessage2 = new DdMessage();
        ddMessage2.setId(ddMessage1.getId());
        assertThat(ddMessage1).isEqualTo(ddMessage2);
        ddMessage2.setId(2L);
        assertThat(ddMessage1).isNotEqualTo(ddMessage2);
        ddMessage1.setId(null);
        assertThat(ddMessage1).isNotEqualTo(ddMessage2);
    }
}
