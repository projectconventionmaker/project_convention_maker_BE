package com.pcmk.utils;

import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UUIdUtilsTest {

    @Test
    @DisplayName("UUID 포맷이면 true를 반환한다")
    void isUUIDFormat_returnsTrue() throws Exception {
        //given
        String uuid = UUID.randomUUID().toString();

        //when
        boolean result = UUIdUtils.isUUIDFormat(uuid);

        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("UUID 포맷이 아니면 False를 반환한다")
    void isUUIDFormat_returnsFalse() throws Exception {
        //given
        String notUuid = "string";

        //when
        boolean result = UUIdUtils.isUUIDFormat(notUuid);

        //then
        Assertions.assertThat(result).isFalse();
    }

}