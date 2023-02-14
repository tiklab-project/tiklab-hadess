package net.tiklab.xpack.repository.service;
import net.tiklab.beans.BeanMapper;
import net.tiklab.core.page.Pagination;
import net.tiklab.core.page.PaginationBuilder;
import net.tiklab.join.JoinTemplate;
import net.tiklab.xpack.repository.dao.RepositoryDao;
import net.tiklab.xpack.repository.entity.RepositoryEntity;
import net.tiklab.xpack.repository.model.Repository;
import net.tiklab.xpack.repository.model.RepositoryQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;


/**
* RepositoryServiceImpl
*/
@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    RepositoryDao repositoryDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    RepositoryGroupItemsService groupItemsService;

    @Value("${repository.library:null}")
    String repositoryLibrary;

    @Override
    public String createRepository(@NotNull @Valid Repository repository) {
        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);

        repositoryEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        repositoryEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));


        return repositoryDao.createRepository(repositoryEntity);
    }

    @Override
    public void updateRepository(@NotNull @Valid Repository repository) {
        RepositoryEntity repositoryEntity = BeanMapper.map(repository, RepositoryEntity.class);

        repositoryDao.updateRepository(repositoryEntity);
    }

    @Override
    public void deleteRepository(@NotNull String id) {
        repositoryDao.deleteRepository(id);

        groupItemsService.deleteRepositoryGroupItems(id);
    }

    @Override
    public Repository findOne(String id) {
        RepositoryEntity repositoryEntity = repositoryDao.findRepository(id);

        Repository repository = BeanMapper.map(repositoryEntity, Repository.class);
        return repository;
    }

    @Override
    public List<Repository> findList(List<String> idList) {
        List<RepositoryEntity> repositoryEntityList =  repositoryDao.findRepositoryList(idList);

        List<Repository> repositoryList =  BeanMapper.mapList(repositoryEntityList,Repository.class);
        return repositoryList;
    }

    @Override
    public Repository findRepository(@NotNull String id) {
        Repository repository = findOne(id);

        joinTemplate.joinQuery(repository);

        return repository;
    }

    @Override
    public List<Repository> findAllRepository() {
        List<RepositoryEntity> repositoryEntityList =  repositoryDao.findAllRepository();

        List<Repository> repositoryList =  BeanMapper.mapList(repositoryEntityList,Repository.class);

        joinTemplate.joinQuery(repositoryList);

        return repositoryList;
    }

    @Override
    public List<Repository> findRepositoryList(RepositoryQuery repositoryQuery) {
        List<RepositoryEntity> repositoryEntityList = repositoryDao.findRepositoryList(repositoryQuery);

        List<Repository> repositoryList = BeanMapper.mapList(repositoryEntityList,Repository.class);

        joinTemplate.joinQuery(repositoryList);

        return repositoryList;
    }

    @Override
    public List<Repository> findLocalAndRemoteRepository(String type) {
        List<RepositoryEntity> repositoryEntityList = repositoryDao.findLocalAndRemoteRepository(type);

        List<Repository> repositoryList = BeanMapper.mapList(repositoryEntityList,Repository.class);

        joinTemplate.joinQuery(repositoryList);

        return repositoryList;
    }

    @Override
    public Pagination<Repository> findRepositoryPage(RepositoryQuery repositoryQuery) {
        Pagination<RepositoryEntity>  pagination = repositoryDao.findRepositoryPage(repositoryQuery);

        List<Repository> repositoryList = BeanMapper.mapList(pagination.getDataList(),Repository.class);

        joinTemplate.joinQuery(repositoryList);

        return PaginationBuilder.build(pagination,repositoryList);
    }

    @Override
    public void mavenSubmit(String contextPath, OutputStream outputStream, InputStream inputStream) throws IOException {
        String url = StringUtils.substringBeforeLast(contextPath, "/");
        String path=repositoryLibrary+url;

        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
            System.out.println("创建文件夹");
        }
        String filePath=repositoryLibrary+contextPath;
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        String line;
        //写入内容
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        StringBuffer buf=new StringBuffer();
        while((line=reader.readLine())!=null){
            buf.append(line);
            writer.write(line+"\n");
        }
        writer.close();
        String s = buf.toString();

        System.out.println("文件夹已存在");

    }
}