package com.isikef.club.builder;

import com.isikef.club.query.FormationSearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FormationQueryBuilder {

    public static Pageable of(FormationSearchQuery query){

        if(query.getField() != null && query.getDirection() != null){
            return PageRequest.of(
                    query.getPage(),
                    query.getSize(),
                    Sort.by(query.getDirection(),
                            query.getField().name()));
        }
        else{
            return PageRequest.of(
                    query.getPage(),
                    query.getSize());
        }
    }
}
