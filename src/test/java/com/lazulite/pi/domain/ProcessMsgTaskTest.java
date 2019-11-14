package com.lazulite.pi.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lazulite.pi.web.rest.TestUtil;

public class ProcessMsgTaskTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessMsgTask.class);
        ProcessMsgTask processMsgTask1 = new ProcessMsgTask();
        processMsgTask1.setId(1L);
        ProcessMsgTask processMsgTask2 = new ProcessMsgTask();
        processMsgTask2.setId(processMsgTask1.getId());
        assertThat(processMsgTask1).isEqualTo(processMsgTask2);
        processMsgTask2.setId(2L);
        assertThat(processMsgTask1).isNotEqualTo(processMsgTask2);
        processMsgTask1.setId(null);
        assertThat(processMsgTask1).isNotEqualTo(processMsgTask2);
    }
}
