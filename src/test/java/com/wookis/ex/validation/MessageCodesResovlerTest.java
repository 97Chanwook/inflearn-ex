package com.wookis.ex.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageCodesResovlerTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        System.out.println("messageCodes = " + Arrays.toString(messageCodes));

        assertThat(messageCodes).containsExactly("required.item","required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        System.out.println("messageCodes = " + Arrays.toString(messageCodes));

        assertThat(messageCodes).containsExactly(
                "required.item.itemName", "required.itemName", "required.java.lang.String", "required"
        );
    }

    //DefaultMessageCodesResolver 의 규칙
    // - 객체 오류
    // 1. : code + "." + objectName     ex) required.item
    // 2. : code                        ex) required

    // - 필드 오류
    // 1. : code + "." + object name + "." + field      ex) required.item.itemName
    // 2. : code + "." + field                          ex) required.itemName
    // 3. : code + "." + field type                     ex) required.java.lang.String
    // 4. : code                                        ex) required
}
