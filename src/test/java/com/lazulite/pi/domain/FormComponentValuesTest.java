package com.lazulite.pi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lazulite.pi.web.rest.TestUtil;

public class FormComponentValuesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormComponentValues.class);
        FormComponentValues formComponentValues1 = new FormComponentValues();
        formComponentValues1.setId(1L);
        FormComponentValues formComponentValues2 = new FormComponentValues();
        formComponentValues2.setId(formComponentValues1.getId());
        assertThat(formComponentValues1).isEqualTo(formComponentValues2);
        formComponentValues2.setId(2L);
        assertThat(formComponentValues1).isNotEqualTo(formComponentValues2);
        formComponentValues1.setId(null);
        assertThat(formComponentValues1).isNotEqualTo(formComponentValues2);
    }
}
