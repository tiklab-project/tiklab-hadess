package io.thoughtware.hadess.timedtask.service;


import io.thoughtware.core.page.Pagination;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstance;
import io.thoughtware.hadess.timedtask.model.TimeTaskInstanceQuery;
import io.thoughtware.toolkit.join.annotation.FindAll;
import io.thoughtware.toolkit.join.annotation.FindList;
import io.thoughtware.toolkit.join.annotation.FindOne;
import io.thoughtware.toolkit.join.annotation.JoinProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* TimeTaskInstanceService-定时任务实例
*/
@JoinProvider(model = TimeTaskInstance.class)
public interface TimeTaskInstanceService {

    /**
    * 创建
    * @param timeTaskInstance
    * @return
    */
    String createTimeTaskInstance(@NotNull @Valid TimeTaskInstance timeTaskInstance);

    /**
    * 更新
    * @param timeTaskInstance
    */
    void updateTimeTaskInstance(@NotNull @Valid TimeTaskInstance timeTaskInstance);



    /**
    * 删除
    * @param id
    */
    void deleteTimeTaskInstance(@NotNull String id);

    /**
     * 条件删除
     * @param fieldType 字段类型
     * @param  value 删除value
     */
    void deleteTimeTaskInstance(@NotNull String fieldType,@NotNull String value);


    @FindOne
    TimeTaskInstance findOne(@NotNull String id);

    @FindList
    List<TimeTaskInstance> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    TimeTaskInstance findTimeTaskInstance(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<TimeTaskInstance> findAllTimeTaskInstance();

    /**
    * 查询列表
    * @param timeTaskInstanceQuery
    * @return
    */
    List<TimeTaskInstance> findTimeTaskInstanceList(TimeTaskInstanceQuery timeTaskInstanceQuery);

    /**
    * 按分页查询
    * @param timeTaskInstanceQuery
    * @return
    */
    Pagination<TimeTaskInstance> findTimeTaskInstancePage(TimeTaskInstanceQuery timeTaskInstanceQuery);

}