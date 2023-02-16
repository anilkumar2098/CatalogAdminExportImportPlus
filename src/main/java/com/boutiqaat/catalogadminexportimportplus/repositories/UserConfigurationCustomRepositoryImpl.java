package com.boutiqaat.catalogadminexportimportplus.repositories;

import com.boutiqaat.catalogadminexportimportplus.entity.SqlStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class UserConfigurationCustomRepositoryImpl implements UserConfigurationCustomRepository{

    private static Logger logger = LoggerFactory.getLogger(UserConfigurationCustomRepositoryImpl.class);

    @Autowired
// TODO : Address following during Master Slave
    @Qualifier(value = "catalogEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;


    @Autowired
// TODO : Address following during Master Slave
    @Qualifier(value = "catalogEntityManagerFactory")
    private EntityManagerFactory masterEntityManagerFactory;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void markAllOtherRowsNotDefault(Long userId, String pageId){
        EntityManager entityManager = masterEntityManagerFactory.createEntityManager();
        try {
            entityManager.joinTransaction();
            markAllOtherRowsNotDefault(entityManager, userId, pageId);
        }catch (Exception ex){
            logger.error("Error while markAllOtherRowsNotDefault: "+ex.getMessage());
            throw ex;
        }finally {
            entityManager.close();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void markUserConfigAsDefault(Long userConfigId, Long userId, String pageId){
        EntityManager entityManager = masterEntityManagerFactory.createEntityManager();
        try {
            entityManager.joinTransaction();
            markAllOtherRowsNotDefault(entityManager, userId, pageId);
            Query query = entityManager.createNativeQuery(SqlStatements.MARK_USER_CONFIGURATION_AS_DEFAULT_SQL);
            query.setParameter("userConfigId", userConfigId);
            query.executeUpdate();
        }catch (Exception ex){
            logger.error("Error while markUserConfigAsDefault: "+ex.getMessage());
            throw ex;
        }finally {
            entityManager.close();
        }
    }

    private void markAllOtherRowsNotDefault(EntityManager entityManager, Long userId, String pageId){
        try {
            Query query = entityManager.createNativeQuery(SqlStatements.UPDATE_USER_CONFIGURATION_SQL);
            query.setParameter("userId", userId);
            query.setParameter("pageId", pageId);
            query.executeUpdate();
        }catch (Exception ex){
            logger.error("Error while creating User configurations "+ex.getMessage());
            throw ex;
        }
    }
}
