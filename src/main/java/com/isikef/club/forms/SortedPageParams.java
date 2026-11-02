package com.isikef.club.forms;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort;

@ParameterObject
@Getter
@Setter
public abstract class SortedPageParams<T> extends PageParams {
    private T field;
    private Sort.Direction direction;
}
