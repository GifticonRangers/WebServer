package com.capstone.webserver.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTimeUtilTest {

    /*
     * " [SP115:월(0A-0)(1-2A)]"
     * " [SP503:수(8B-9), 금(7-8A)]"
     * " [SP116:월(6)] [SP403:수(3)]"
     * " [SP116:월(6), 금(7-8A)] [SP403:수(3)]"
     */
    @Test
    void splitSubjectTime(){
        assertEquals(SubjectTimeUtil.splitSubjectTime(" [SP115:월(0A-0)(1-2A)]"), "{월=[0A-0, 1-2A]}");
        assertEquals(SubjectTimeUtil.splitSubjectTime(" [SP503:수(8B-9), 금(7-8A)]"), "{수=[8B-9], 금=[7-8A]}");
        assertEquals(SubjectTimeUtil.splitSubjectTime(" [SP116:월(6)] [SP403:수(3)]"), "{월=[6], 수=[3]}");
        assertEquals(SubjectTimeUtil.splitSubjectTime(" [SP116:월(6), 금(7-8A)] [SP403:수(3)]"), "{월=[6], 금=[7-8A], 수=[3]}");
    }
}