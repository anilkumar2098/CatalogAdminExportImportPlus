package com.boutiqaat.catalogadminexportimportplus.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


@Getter
@Setter
@ToString
public class CustomAdminUser extends AdminUser {

    private static final long serialVersionUID = 1L;

    private Set<String> permissions;


}

