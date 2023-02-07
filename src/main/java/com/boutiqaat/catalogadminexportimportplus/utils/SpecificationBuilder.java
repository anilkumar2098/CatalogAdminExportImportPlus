package com.boutiqaat.catalogadminexportimportplus.utils;

import com.boutiqaat.catalogadminexportimportplus.model.Filter;
import liquibase.pro.packaged.R;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class SpecificationBuilder<T> {

    private JpaSpecificationExecutor repository;


    private SpecificationImpl specification;


    public Specification getSpecification() {
        return specification;
    }

    public static <T, R extends JpaRepository<T, ?> & JpaSpecificationExecutor>
    SpecificationBuilder<T> selectFrom(R repository) {
        SpecificationBuilder<T> builder = new SpecificationBuilder<>();
        builder.repository = (JpaSpecificationExecutor) repository;
        builder.specification = new SpecificationImpl();
        return builder;
    }

    public SpecificationBuilder<T> where(Filter filter) {
        if (this.repository == null) {
            throw new IllegalStateException("Did not specify which repository, please use from() before where()");
        }
        specification.add(new WhereSpecification(filter));
        return this;
    }
}
