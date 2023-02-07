package com.boutiqaat.catalogadminexportimportplus.utils;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class SpecificationImpl implements Specification<Object> {

    private List<Specification> specifications = new LinkedList<>();

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

    public void add(Specification<Object> specification){
        specifications.add(specification);
    }
}
