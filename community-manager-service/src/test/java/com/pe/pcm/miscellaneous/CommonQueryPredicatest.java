package com.pe.pcm.miscellaneous;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CommonQueryPredicateTest {

    private static final String SEARCH_TERM = "Foo";
    private static final String SEARCH_TERM_FIELD = "Foo";
    private static final String SEARCH_TERM_LIKE_PATTERN = "Foo%";

    private CriteriaBuilder criteriaBuilderMock;

    private Root rootMock;

    @BeforeEach
    void setUp() {
        criteriaBuilderMock = Mockito.mock(CriteriaBuilder.class);
        rootMock = Mockito.mock(Root.class);
    }

    @Test
    @DisplayName("Get Predicates with isLike false")
    void testGetPredicate(){
        List<Predicate> predicates = new ArrayList<>();
        CommonQueryPredicate.getPredicate(rootMock,criteriaBuilderMock,predicates,SEARCH_TERM,SEARCH_TERM_FIELD,false);
    }

    @Test
    @DisplayName("Get Predicates with isLike true")
    void testGetPredicate1(){
        List<Predicate> predicates = new ArrayList<>();
        CommonQueryPredicate.getPredicate(rootMock,criteriaBuilderMock,predicates,SEARCH_TERM_LIKE_PATTERN,SEARCH_TERM,true);
    }

}
