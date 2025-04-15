package io.tiklab.hadess.library.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.hadess.library.dao.LibraryPypiDao;
import io.tiklab.hadess.library.entity.LibraryPypiEntity;
import io.tiklab.hadess.library.model.Library;
import io.tiklab.hadess.library.model.LibraryPypi;
import io.tiklab.hadess.library.model.LibraryPypiQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
* LibraryPypiServiceImpl-Pypi制品差异数据
*/
@Service
public class LibraryPypiServiceImpl implements LibraryPypiService {

    @Autowired
    LibraryPypiDao libraryPypiDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createLibraryPypi(@NotNull @Valid LibraryPypi libraryPypi) {
        List<LibraryPypi> libraryPypiList = findLibraryPypiList(new LibraryPypiQuery().setLibraryId(libraryPypi.getLibraryId()));
        if (CollectionUtils.isEmpty(libraryPypiList)){
            LibraryPypiEntity libraryPypiEntity = BeanMapper.map(libraryPypi, LibraryPypiEntity.class);
            return libraryPypiDao.createLibraryPypi(libraryPypiEntity);
        }
        return libraryPypiList.get(0).getId();
    }

    @Override
    public void updateLibraryPypi(@NotNull @Valid LibraryPypi libraryPypi) {
        LibraryPypiEntity libraryPypiEntity = BeanMapper.map(libraryPypi, LibraryPypiEntity.class);

        libraryPypiDao.updateLibraryPypi(libraryPypiEntity);
    }

    @Override
    public void deleteLibraryPypi(@NotNull String id) {
        libraryPypiDao.deleteLibraryPypi(id);
    }


    @Override
    public void deleteLibraryPypiByCondition(String field, String value) {
        DeleteCondition libraryPypi = DeleteBuilders.createDelete(LibraryPypiEntity.class)
                .eq(field,value)
                .get();
        libraryPypiDao.deleteLibraryPypi(libraryPypi);
    }

    @Override
    public LibraryPypi findOne(String id) {
        LibraryPypiEntity libraryPypiEntity = libraryPypiDao.findLibraryPypi(id);

        LibraryPypi libraryPypi = BeanMapper.map(libraryPypiEntity, LibraryPypi.class);
        return libraryPypi;
    }

    @Override
    public List<LibraryPypi> findList(List<String> idList) {
        List<LibraryPypiEntity> libraryPypiEntityList =  libraryPypiDao.findLibraryPypiList(idList);

        List<LibraryPypi> libraryPypiList =  BeanMapper.mapList(libraryPypiEntityList,LibraryPypi.class);
        return libraryPypiList;
    }

    @Override
    public LibraryPypi findLibraryPypi(@NotNull String id) {
        LibraryPypi libraryPypi = findOne(id);

        joinTemplate.joinQuery(libraryPypi);

        return libraryPypi;
    }

    @Override
    public List<LibraryPypi> findAllLibraryPypi() {
        List<LibraryPypiEntity> libraryPypiEntityList =  libraryPypiDao.findAllLibraryPypi();

        List<LibraryPypi> libraryPypiList =  BeanMapper.mapList(libraryPypiEntityList,LibraryPypi.class);

        joinTemplate.joinQuery(libraryPypiList);

        return libraryPypiList;
    }

    @Override
    public List<LibraryPypi> findLibraryPypiList(LibraryPypiQuery libraryPypiQuery) {
        List<LibraryPypiEntity> libraryPypiEntityList = libraryPypiDao.findLibraryPypiList(libraryPypiQuery);

        List<LibraryPypi> libraryPypiList = BeanMapper.mapList(libraryPypiEntityList,LibraryPypi.class);

        joinTemplate.joinQuery(libraryPypiList);

        return libraryPypiList;
    }

    @Override
    public Pagination<LibraryPypi> findLibraryPypiPage(LibraryPypiQuery libraryPypiQuery) {
        Pagination<LibraryPypiEntity>  pagination = libraryPypiDao.findLibraryPypiPage(libraryPypiQuery);

        List<LibraryPypi> libraryPypiList = BeanMapper.mapList(pagination.getDataList(),LibraryPypi.class);

        joinTemplate.joinQuery(libraryPypiList);

        return PaginationBuilder.build(pagination,libraryPypiList);
    }



}