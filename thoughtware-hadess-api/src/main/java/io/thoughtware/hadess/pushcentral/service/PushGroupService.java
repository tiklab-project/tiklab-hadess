package io.thoughtware.hadess.pushcentral.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.hadess.pushcentral.model.PushGroup;
import io.thoughtware.hadess.pushcentral.model.PushGroupQuery;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* PushGroupService-推送组接口
*/
@JoinProvider(model = PushGroup.class)
public interface PushGroupService {

    /**
    * 创建
    * @param pushGroup
    * @return
    */
    String createPushGroup(@NotNull @Valid PushGroup pushGroup);



    /**
    * 更新
    * @param pushGroup
    */
    void updatePushGroup(@NotNull @Valid PushGroup pushGroup);

    /**
    * 删除
    * @param id
    */
    void deletePushGroup(@NotNull String id);

    /**
     * 条件删除
     * @param field 根据删除的字段
     * @param  value value
     * @return
     */
    void deleteVersionByCondition(String field, String value);

    @FindOne
    PushGroup findOne(@NotNull String id);

    @FindList
    List<PushGroup> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    PushGroup findPushGroup(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<PushGroup> findAllPushGroup();

    /**
    * 查询列表
    * @param pushGroupQuery
    * @return
    */
    List<PushGroup> findPushGroupList(PushGroupQuery pushGroupQuery);


    /**
    * 按分页查询
    * @param pushGroupQuery
    * @return
    */
    Pagination<PushGroup> findPushGroupPage(PushGroupQuery pushGroupQuery);


}