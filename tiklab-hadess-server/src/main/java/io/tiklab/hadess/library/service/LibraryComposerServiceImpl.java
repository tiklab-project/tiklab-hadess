package io.tiklab.hadess.library.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.dal.jpa.criterial.condition.DeleteCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.tiklab.hadess.library.dao.LibraryComposerDao;
import io.tiklab.hadess.library.entity.LibraryComposerEntity;
import io.tiklab.hadess.library.model.LibraryComposer;
import io.tiklab.hadess.library.model.LibraryComposerQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* LibraryComposerServiceImpl-Composer制品差异数据
*/
@Service
public class LibraryComposerServiceImpl implements LibraryComposerService {

    @Autowired
    LibraryComposerDao libraryComposerDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createLibraryComposer(@NotNull @Valid LibraryComposer libraryComposer) {
        List<LibraryComposer> libraryComposerList = findLibraryComposerList(new LibraryComposerQuery().setLibraryId(libraryComposer.getLibraryId()));
        if (CollectionUtils.isEmpty(libraryComposerList)){
            LibraryComposerEntity libraryComposerEntity = BeanMapper.map(libraryComposer, LibraryComposerEntity.class);
            return libraryComposerDao.createLibraryComposer(libraryComposerEntity);
        }
        return libraryComposerList.get(0).getId();
    }

    @Override
    public void updateLibraryComposer(@NotNull @Valid LibraryComposer libraryComposer) {
        LibraryComposerEntity libraryComposerEntity = BeanMapper.map(libraryComposer, LibraryComposerEntity.class);

        libraryComposerDao.updateLibraryComposer(libraryComposerEntity);
    }

    @Override
    public void deleteLibraryComposer(@NotNull String id) {
        libraryComposerDao.deleteLibraryComposer(id);
    }


    @Override
    public void deleteLibraryComposerByCondition(String field, String value) {
        DeleteCondition libraryComposer = DeleteBuilders.createDelete(LibraryComposerEntity.class)
                .eq(field,value)
                .get();
        libraryComposerDao.deleteLibraryComposer(libraryComposer);
    }

    @Override
    public LibraryComposer findOne(String id) {
        LibraryComposerEntity libraryComposerEntity = libraryComposerDao.findLibraryComposer(id);

        LibraryComposer libraryComposer = BeanMapper.map(libraryComposerEntity, LibraryComposer.class);
        return libraryComposer;
    }

    @Override
    public List<LibraryComposer> findList(List<String> idList) {
        List<LibraryComposerEntity> libraryComposerEntityList =  libraryComposerDao.findLibraryComposerList(idList);

        List<LibraryComposer> libraryComposerList =  BeanMapper.mapList(libraryComposerEntityList,LibraryComposer.class);
        return libraryComposerList;
    }

    @Override
    public LibraryComposer findLibraryComposer(@NotNull String id) {
        LibraryComposer libraryComposer = findOne(id);

        joinTemplate.joinQuery(libraryComposer);

        return libraryComposer;
    }

    @Override
    public List<LibraryComposer> findAllLibraryComposer() {
        List<LibraryComposerEntity> libraryComposerEntityList =  libraryComposerDao.findAllLibraryComposer();

        List<LibraryComposer> libraryComposerList =  BeanMapper.mapList(libraryComposerEntityList,LibraryComposer.class);

        joinTemplate.joinQuery(libraryComposerList);

        return libraryComposerList;
    }

    @Override
    public List<LibraryComposer> findLibraryComposerList(LibraryComposerQuery libraryComposerQuery) {
        List<LibraryComposerEntity> libraryComposerEntityList = libraryComposerDao.findLibraryComposerList(libraryComposerQuery);

        List<LibraryComposer> libraryComposerList = BeanMapper.mapList(libraryComposerEntityList,LibraryComposer.class);

        joinTemplate.joinQuery(libraryComposerList);

        return libraryComposerList;
    }

    @Override
    public Pagination<LibraryComposer> findLibraryComposerPage(LibraryComposerQuery libraryComposerQuery) {
        Pagination<LibraryComposerEntity>  pagination = libraryComposerDao.findLibraryComposerPage(libraryComposerQuery);

        List<LibraryComposer> libraryComposerList = BeanMapper.mapList(pagination.getDataList(),LibraryComposer.class);

        joinTemplate.joinQuery(libraryComposerList);

        return PaginationBuilder.build(pagination,libraryComposerList);
    }



}