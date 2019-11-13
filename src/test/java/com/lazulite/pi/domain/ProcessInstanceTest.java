package com.lazulite.pi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lazulite.pi.web.rest.TestUtil;

public class ProcessInstanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessInstance.class);
        ProcessInstance processInstance1 = new ProcessInstance();
        processInstance1.setId(1L);
        ProcessInstance processInstance2 = new ProcessInstance();
        processInstance2.setId(processInstance1.getId());
        assertThat(processInstance1).isEqualTo(processInstance2);
        processInstance2.setId(2L);
        assertThat(processInstance1).isNotEqualTo(processInstance2);
        processInstance1.setId(null);
        assertThat(processInstance1).isNotEqualTo(processInstance2);
    }
}
