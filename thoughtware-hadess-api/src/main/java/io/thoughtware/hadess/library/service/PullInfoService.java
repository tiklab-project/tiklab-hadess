package io.thoughtware.hadess.library.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;
import io.thoughtware.hadess.library.model.PullInfo;
import io.thoughtware.hadess.library.model.PullInfoQuery;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* PullInfoService-制品拉取信息接口
*/
@JoinProvider(model = PullInfo.class)
public interface PullInfoService {

    /**
    * 创建
    * @param pullInfo
    * @return
    */
    String createPullInfo(@NotNull @Valid PullInfo pullInfo);

    /**
    * 更新
    * @param pullInfo
    */
    void updatePullInfo(@NotNull @Valid PullInfo pullInfo);

    /**
    * 删除
    * @param id
    */
    void deletePullInfo(@NotNull String id);

    @FindOne
    PullInfo findOne(@NotNull String id);

    @FindList
    List<PullInfo> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    PullInfo findPullInfo(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<PullInfo> findAllPullInfo();

    /**
    * 查询列表
    * @param pullInfoQuery
    * @return
    */
    List<PullInfo> findPullInfoList(PullInfoQuery pullInfoQuery);

    /**
    * 按分页查询
    * @param pullInfoQuery
    * @return
    */
    Pagination<PullInfo> findPullInfoPage(PullInfoQuery pullInfoQuery);




}