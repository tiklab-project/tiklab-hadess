package io.thoughtware.hadess.library.service;

import io.thoughtware.hadess.common.RepositoryUtil;
import io.thoughtware.hadess.library.dao.LibraryVersionDao;
import io.thoughtware.hadess.library.entity.LibraryVersionEntity;
import io.thoughtware.hadess.library.model.*;
import io.thoughtware.dal.jpa.criterial.condition.DeleteCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.DeleteBuilders;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.core.page.Pagination;
import io.thoughtware.core.page.PaginationBuilder;
import io.thoughtware.toolkit.join.JoinTemplate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* LibraryVersionServiceImpl-制品版本
*/
@Service
public class LibraryVersionServiceImpl implements LibraryVersionService {
    private static Logger logger = LoggerFactory.getLogger(LibraryVersionServiceImpl.class);

    @Autowired
    LibraryVersionDao libraryVersionDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    LibraryMavenService libraryMavenService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    LibraryFileService libraryFileService;

    @Autowired
    PullInfoService pullInfoService;

    @Override
    public String createLibraryVersion(@NotNull @Valid LibraryVersion libraryVersion) {
        LibraryVersionEntity libraryVersionEntity = BeanMapper.map(libraryVersion, LibraryVersionEntity.class);
        libraryVersionEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        libraryVersionEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        libraryVersionEntity.setPushTime(new Timestamp(System.currentTimeMillis()));
        return libraryVersionDao.createLibraryVersion(libraryVersionEntity);
    }

    @Override
    public void updateLibraryVersion(@NotNull @Valid LibraryVersion libraryVersion) {
        LibraryVersionEntity libraryVersionEntity = BeanMapper.map(libraryVersion, LibraryVersionEntity.class);
        libraryVersionEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        libraryVersionDao.updateLibraryVersion(libraryVersionEntity);
    }



    @Override
    public void deleteLibraryVersion(@NotNull String id) {
        libraryVersionDao.deleteLibraryVersion(id);

        Thread thread = new Thread() {
            public void run() {
                //删除文件
                libraryFileService.deleteLibraryFileByCondition("libraryVersionId",id);
            }
        };
        thread.start();
    }

    @Override
    public void deleteLibraryVersion(String id, String libraryId) {
        List<LibraryVersionEntity> libraryVersionList = libraryVersionDao.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(libraryId));


        //只有一个版本或者没有版本直接删除整个制品
        if (CollectionUtils.isEmpty(libraryVersionList)||libraryVersionList.size()==1){
            libraryService.deleteLibrary(libraryId);
        }

        //存在多个版本 只删除当前版本
        if (libraryVersionList.size()>1){
            this.deleteLibraryVersion(id);
        }
    }

    @Override
    public LibraryVersion findOne(String id) {
        LibraryVersionEntity libraryVersionEntity = libraryVersionDao.findLibraryVersion(id);

        LibraryVersion libraryVersion = BeanMapper.map(libraryVersionEntity, LibraryVersion.class);


        return libraryVersion;
    }

    @Override
    public List<LibraryVersion> findList(List<String> idList) {
        List<LibraryVersionEntity> libraryVersionEntityList =  libraryVersionDao.findLibraryVersionList(idList);

        List<LibraryVersion> libraryVersionList =  BeanMapper.mapList(libraryVersionEntityList,LibraryVersion.class);
        return libraryVersionList;
    }

    @Override
    public LibraryVersion findLibraryVersion(@NotNull String id) {
        LibraryVersion libraryVersion = findOne(id);

        List<LibraryMaven> libraryMavenList = libraryMavenService.findLibraryMavenList(new LibraryMavenQuery().setLibraryId(libraryVersion.getLibrary().getId()));
        if (CollectionUtils.isNotEmpty(libraryMavenList)){
            libraryVersion.setArtifactId(libraryMavenList.get(0).getArtifactId());
            libraryVersion.setGroupId(libraryMavenList.get(0).getGroupId());

            List<PullInfo> pullInfoList = pullInfoService.findPullInfoList(new PullInfoQuery().setLibraryVersionId(libraryMavenList.get(0).getId()));
            if (CollectionUtils.isNotEmpty(pullInfoList)){
                PullInfo pullInfo = pullInfoList.get(0);
                libraryVersion.setPullUser(pullInfo.getUser().getName());
                libraryVersion.setPullTime(pullInfo.getPullCreate());
                libraryVersion.setPullNum(pullInfoList.size());
            }
        }
        //制品的大小
        String librarySize = librarySize(libraryVersion.getId());
        libraryVersion.setShowSize(librarySize);

        joinTemplate.joinQuery(libraryVersion);

        return libraryVersion;
    }

    @Override
    public LibraryVersion findLibraryVersionById(String versionId) {
        LibraryVersion libraryVersion = findOne(versionId);
        joinTemplate.joinQuery(libraryVersion);
        return libraryVersion;
    }



    @Override
    public List<LibraryVersion> findAllLibraryVersion() {
        List<LibraryVersionEntity> libraryVersionEntityList =  libraryVersionDao.findAllLibraryVersion();

        List<LibraryVersion> libraryVersionList =  BeanMapper.mapList(libraryVersionEntityList,LibraryVersion.class);

        joinTemplate.joinQuery(libraryVersionList);

        return libraryVersionList;
    }

    @Override
    public List<LibraryVersion> findLibraryVersionList(LibraryVersionQuery libraryVersionQuery) {
        List<LibraryVersionEntity> libraryVersionEntityList = libraryVersionDao.findLibraryVersionList(libraryVersionQuery);

        List<LibraryVersion> libraryVersionList = BeanMapper.mapList(libraryVersionEntityList,LibraryVersion.class);

        joinTemplate.joinQuery(libraryVersionList);

        return libraryVersionList;
    }

    @Override
    public List<LibraryVersion> findLibraryVersionList(String repositoryId) {
        List<LibraryVersionEntity> libraryVersionEntityList = libraryVersionDao.findLibraryVersionList(new LibraryVersionQuery().setRepositoryId(repositoryId));

        List<LibraryVersion> libraryVersionList = BeanMapper.mapList(libraryVersionEntityList,LibraryVersion.class);

        return libraryVersionList;
    }

    @Override
    public Pagination<LibraryVersion> findLibraryVersionPage(LibraryVersionQuery libraryVersionQuery) {
        Pagination<LibraryVersionEntity>  pagination = libraryVersionDao.findLibraryVersionPage(libraryVersionQuery);

        List<LibraryVersion> libraryVersionList = BeanMapper.mapList(pagination.getDataList(),LibraryVersion.class);

        joinTemplate.joinQuery(libraryVersionList);
        return PaginationBuilder.build(pagination,libraryVersionList);
    }

    @Override
    public Pagination<LibraryVersion> findHistoryVersionPage(LibraryVersionQuery libraryVersionQuery) {
        logger.info("时间1:"+System.currentTimeMillis());
        Pagination<LibraryVersionEntity>  pagination = libraryVersionDao.findLibraryVersionPage(libraryVersionQuery);
        List<LibraryVersion> libraryVersionList=null;
        logger.info("时间2:"+System.currentTimeMillis());
        if (CollectionUtils.isNotEmpty(pagination.getDataList())){
             libraryVersionList = BeanMapper.mapList(pagination.getDataList(),LibraryVersion.class);
            joinTemplate.joinQuery(libraryVersionList);

            //当版本为快照版本时候获取快照时间戳数据
            List<LibraryFile> libraryFileList=null;
            List<LibraryVersion> versions = libraryVersionList.stream().filter(a -> a.getVersion().toUpperCase().endsWith("SNAPSHOT")).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(versions)){
                libraryFileList = libraryFileService.findLibraryFileList(libraryVersionList.get(0).getLibrary().getId());
            }
            logger.info("时间3:"+System.currentTimeMillis());
            for (LibraryVersion libraryVersion:libraryVersionList){
                //制品的大小
                String librarySize = librarySize(libraryVersion.getId());
                libraryVersion.setShowSize(librarySize);


                //查询快照版本的时间戳版本
                if (libraryVersion.getVersion().toUpperCase().endsWith("SNAPSHOT")){
                    snapshotData(libraryFileList,libraryVersion);
                }
            }
            logger.info("时间4:"+System.currentTimeMillis());
        }
        return PaginationBuilder.build(pagination,libraryVersionList);
    }


    @Override
    public LibraryVersion findLibraryNewVersion(LibraryVersionQuery libraryVersionQuery) {
        LibraryVersion libraryVersion=null;
        List<LibraryVersion> libraryVersionList = this.findLibraryVersionList(libraryVersionQuery);
        if (CollectionUtils.isNotEmpty(libraryVersionList)){
            List<LibraryVersion> versions = libraryVersionList.stream().sorted(Comparator.comparing(LibraryVersion::getCreateTime).reversed()).collect(Collectors.toList());
             libraryVersion = versions.get(0);
        }
        return libraryVersion;
    }

    /**
     *  制品版本创建、修改
     * @param libraryVersion    libraryVersion
     * @param fileName 文件名称
     * @return
     */
    public String createLibraryVersionSplice( LibraryVersion libraryVersion,String fileName){

        libraryVersion.setPushTime(new Timestamp(System.currentTimeMillis()));
        String libraryVersionId=null;

        // 通过仓库id、制品id、版本查询制品版本数据
        List<LibraryVersion> libraryVersionList = this.findLibraryVersionList(new LibraryVersionQuery().setLibraryId(libraryVersion.getLibrary().getId()).
                setRepositoryId(libraryVersion.getRepository().getId()).setVersion(libraryVersion.getVersion()));

        //版本存在就更新、不存在直接创建
        if (CollectionUtils.isNotEmpty(libraryVersionList)){
            LibraryVersion version = libraryVersionList.get(0);
            libraryVersionId = version.getId();
            Long versionSize = version.getSize();

            if (StringUtils.isEmpty(libraryVersion.getHash())){
                libraryVersion.setHash(version.getHash());
            }
            libraryVersion.setId(libraryVersionId);

            //通过制品id、版本id、文件名字查询制品文件信息
            List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryId(libraryVersion.getLibrary().getId()).setLibraryVersionId(version.getId())
                    .setFileName(fileName));

            //制品文件不为空的上传是执行的替换操作，版本的大小需要减去原制品文件大小
            if (CollectionUtils.isNotEmpty(libraryFileList)){
                Long size = libraryFileList.get(0).getSize();
                 versionSize = version.getSize() >= size ? version.getSize() - size : version.getSize();
            }

            //maven 快照版本每次提交都会创建新的文件
            if(!("npm").equals(libraryVersion.getLibraryType())){
                //加上最新文件的大小
                versionSize = versionSize + libraryVersion.getSize();
            }
            libraryVersion.setSize(versionSize);

            this.updateLibraryVersion(libraryVersion);
        }else {

            //创建版本
            libraryVersionId = this.createLibraryVersion(libraryVersion);

            //更新最新版本
            libraryVersion.getLibrary().setNewVersion(libraryVersion.getVersion());
            libraryService.updateLibrary(libraryVersion.getLibrary());
        }
        return libraryVersionId;
    }


    @Override
    public void deleteVersionByCondition(String field, String value) {
        DeleteCondition deleteCondition = DeleteBuilders.createDelete(LibraryVersionEntity.class)
                .eq(field, value)
                .get();
        libraryVersionDao.deleteLibraryVersion(deleteCondition);
    }

    @Override
    public LibraryVersion findVersionByNameAndVer(String libraryName, String version) {
        LibraryVersionEntity versionByNameAndVer = libraryVersionDao.findVersionByNameAndVer(libraryName, version);
        LibraryVersion libraryVersion = BeanMapper.map(versionByNameAndVer, LibraryVersion.class);

        return libraryVersion;
    }



    /**
     *  制品版本大小
     * @param libraryVersionId    制品版本id
     * @return
     */
    public String librarySize( String libraryVersionId){
        String librarySize=null;
        List<LibraryFile> libraryFileList = libraryFileService.findLibraryFileList(new LibraryFileQuery().setLibraryVersionId(libraryVersionId));

        if (!CollectionUtils.isEmpty(libraryFileList)){
            LibraryFile libraryFile = libraryFileList.get(0);
            if (("docker").equals(libraryFile.getRepository().getType())){
                List<LibraryFile> libraryFiles = libraryFileService.dockerFile(libraryFile);
                libraryFileList = Stream.concat(libraryFileList.stream(), libraryFiles.stream()).collect(Collectors.toList());

            }

            List<String> sizeList = libraryFileList.stream().map(LibraryFile::getFileSize).collect(Collectors.toList());
            librarySize = RepositoryUtil.formatSizeSum(sizeList);

        }
        return librarySize;
    }

    @Override
    public List<LibraryVersion> findVersionByLibraryIds(String[] libraryIds) {
        List<LibraryVersionEntity>  libraryVersionEntities = libraryVersionDao.findVersionByLibraryIds(libraryIds);
        List<LibraryVersion> libraryVersions = BeanMapper.mapList(libraryVersionEntities, LibraryVersion.class);
        return libraryVersions;
    }

    /**
     * 获取快照数据
     * @param libraryFileList
     */
    public void snapshotData( List<LibraryFile> libraryFileList,LibraryVersion libraryVersion){
        if (CollectionUtils.isNotEmpty(libraryFileList)){
            List<LibraryFile> libraryFiles = libraryFileList.stream().filter(a -> a.getLibraryVersion().getId().equals(libraryVersion.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(libraryFiles)){
                //根据快照时间戳版本分组
                Map<String, List<LibraryFile>> stringListMap = libraryFiles.stream().filter(a->StringUtils.isNotEmpty(a.getSnapshotVersion()))
                        .sorted(Comparator.comparing(LibraryFile::getCreateTime).reversed()).collect(Collectors.groupingBy(LibraryFile::getSnapshotVersion));
                Set<String> strings = stringListMap.keySet();
                List<Object> arrayList = new ArrayList<>();
                for (String version:strings){
                    List<LibraryFile> files = stringListMap.get(version);

                    long time = files.get(0).getCreateTime().getTime();
                    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());

                    // 格式化为日期字符串
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String formattedDateTime = dateTime.format(formatter);

                    long summed = files.stream().mapToLong(LibraryFile::getSize).sum();

                    Map<String, Object> hashMap = new HashMap<>();
                    hashMap.put("version",version);
                    hashMap.put("showSize",RepositoryUtil.formatSize(summed));
                    hashMap.put("pusher",libraryVersion.getPusher());
                    hashMap.put("updateTime",formattedDateTime);
                    hashMap.put("type","child");
                    arrayList.add(hashMap);
                    libraryVersion.setChildren(arrayList);
                }
            }
        }
    }
}