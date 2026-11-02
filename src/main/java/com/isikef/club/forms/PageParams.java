package com.isikef.club.forms;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
@Getter
@Setter
public class PageParams {
    private int page;
    private int size;
}
