package com.isikef.club.query;

import com.isikef.club.forms.SortedPageParams;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
@Getter
@Setter
public class FormationSearchQuery extends SortedPageParams<FormationSort> {
}
