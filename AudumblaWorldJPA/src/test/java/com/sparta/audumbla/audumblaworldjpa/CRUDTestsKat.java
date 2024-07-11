package com.sparta.audumbla.audumblaworldjpa;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CRUDTestsKat {

    @Test
    @DisplayName("Test I can search books by Author ID")
    @Transactional
    void testSearchByAuthorID() {
        String test = bookRepo.findBookEntityByAuthor_Id(1).getFirst().getTitle();
        Assertions.assertEquals(test, "The History of Me");
    }


}
