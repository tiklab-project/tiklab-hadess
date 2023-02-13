package net.tiklab.xpack.library.service;

import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.xpack.library.dao.LibraryMavenDao;
import net.tiklab.xpack.library.entity.LibraryMavenEntity;
import net.tiklab.xpack.library.model.Library;
import net.tiklab.xpack.library.model.LibraryMaven;
import net.tiklab.xpack.library.model.LibraryMavenQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
* LibraryMavenServiceImpl
*/
@Service
public class LibraryMavenServiceImpl implements LibraryMavenService {

    @Autowired
    LibraryMavenDao libraryMavenDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    public String createLibraryMaven(@NotNull @Valid LibraryMaven libraryMaven) {
        LibraryMavenEntity libraryMavenEntity = BeanMapper.map(libraryMaven, LibraryMavenEntity.class);
        libraryMavenEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return libraryMavenDao.createLibraryMaven(libraryMavenEntity);
    }

    @Override
    public void updateLibraryMaven(@NotNull @Valid LibraryMaven libraryMaven) {
        LibraryMavenEntity libraryMavenEntity = BeanMapper.map(libraryMaven, LibraryMavenEntity.class);

        libraryMavenDao.updateLibraryMaven(libraryMavenEntity);
    }

    @Override
    public void deleteLibraryMaven(@NotNull String id) {
        libraryMavenDao.deleteLibraryMaven(id);
    }

    @Override
    public LibraryMaven findOne(String id) {
        LibraryMavenEntity libraryMavenEntity = libraryMavenDao.findLibraryMaven(id);

        LibraryMaven libraryMaven = BeanMapper.map(libraryMavenEntity, LibraryMaven.class);
        return libraryMaven;
    }

    @Override
    public List<LibraryMaven> findList(List<String> idList) {
        List<LibraryMavenEntity> libraryMavenEntityList =  libraryMavenDao.findLibraryMavenList(idList);

        List<LibraryMaven> libraryMavenList =  BeanMapper.mapList(libraryMavenEntityList,LibraryMaven.class);
        return libraryMavenList;
    }

    @Override
    public LibraryMaven findLibraryMaven(@NotNull String id) {
        LibraryMaven libraryMaven = findOne(id);

        joinTemplate.joinQuery(libraryMaven);

        return libraryMaven;
    }

    @Override
    public List<LibraryMaven> findAllLibraryMaven() {
        List<LibraryMavenEntity> libraryMavenEntityList =  libraryMavenDao.findAllLibraryMaven();

        List<LibraryMaven> libraryMavenList =  BeanMapper.mapList(libraryMavenEntityList,LibraryMaven.class);

        joinTemplate.joinQuery(libraryMavenList);

        return libraryMavenList;
    }

    @Override
    public List<LibraryMaven> findLibraryMavenList(LibraryMavenQuery libraryMavenQuery) {
        List<LibraryMavenEntity> libraryMavenEntityList = libraryMavenDao.findLibraryMavenList(libraryMavenQuery);

        List<LibraryMaven> libraryMavenList = BeanMapper.mapList(libraryMavenEntityList,LibraryMaven.class);

        joinTemplate.joinQuery(libraryMavenList);

        return libraryMavenList;
    }

    @Override
    public Pagination<LibraryMaven> findLibraryMavenPage(LibraryMavenQuery libraryMavenQuery) {
        Pagination<LibraryMavenEntity>  pagination = libraryMavenDao.findLibraryMavenPage(libraryMavenQuery);

        List<LibraryMaven> libraryMavenList = BeanMapper.mapList(pagination.getDataList(),LibraryMaven.class);

        joinTemplate.joinQuery(libraryMavenList);

        return PaginationBuilder.build(pagination,libraryMavenList);
    }

    public void libraryMavenSplice(String artifactId, String[]  single, Library library ){
        int length = single.length;
        String groupId=null;
        for (int a= 1; a<length-3-3;a++){
            String s = single[3 + a];
            if (groupId==null){
                groupId=s+".";
            }else {
                if(a==length-7){
                    groupId =  groupId+s;
                }else {
                    groupId =  groupId+s+".";
                }

            }
        }
        List<LibraryMaven> libraryMavenList = this.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(library.getId())
                .setArtifactId(artifactId).setGroupId(groupId));
        if (CollectionUtils.isEmpty(libraryMavenList)){
            LibraryMaven libraryMaven = new LibraryMaven();
            libraryMaven.setArtifactId(artifactId);
            libraryMaven.setGroupId(groupId);
            libraryMaven.setLibrary(library);
            this.createLibraryMaven(libraryMaven);
        }
    }
}