package io.tiklab.xpack.library.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.xpack.library.model.PushLibrary;
import io.tiklab.xpack.library.model.PushLibraryQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* PushLibraryService-制品接口
*/
@JoinProvider(model = PushLibrary.class)
public interface PushLibraryService {

    /**
    * 创建
    * @param pushLibrary
    * @return
    */
    String createPushLibrary(@NotNull @Valid PushLibrary pushLibrary);



    /**
    * 更新
    * @param pushLibrary
    */
    void updatePushLibrary(@NotNull @Valid PushLibrary pushLibrary);

    /**
    * 删除
    * @param id
    */
    void deletePushLibrary(@NotNull String id);

    /**
     * 条件删除
     * @param field 根据删除的字段
     * @param  value value
     * @return
     */
    void deleteVersionByCondition(String field, String value);

    @FindOne
    PushLibrary findOne(@NotNull String id);

    @FindList
    List<PushLibrary> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    PushLibrary findPushLibrary(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<PushLibrary> findAllPushLibrary();

    /**
    * 查询列表
    * @param pushLibraryQuery
    * @return
    */
    List<PushLibrary> findPushLibraryList(PushLibraryQuery pushLibraryQuery);


    /**
    * 按分页查询
    * @param pushLibraryQuery
    * @return
    */
    Pagination<PushLibrary> findPushLibraryPage(PushLibraryQuery pushLibraryQuery);


    /**
     * 推送中央仓库
     * @param pushLibrary
     * @return
     */
    String pushCentralWare(PushLibrary pushLibrary);


}