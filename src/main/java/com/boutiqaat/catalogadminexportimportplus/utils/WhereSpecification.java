package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.model.Filter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class WhereSpecification implements Specification<Object>{
    public WhereSpecification(Filter filter) {

    }

    @Override
    public Specification<Object> and(Specification<Object> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Object> or(Specification<Object> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Object> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
