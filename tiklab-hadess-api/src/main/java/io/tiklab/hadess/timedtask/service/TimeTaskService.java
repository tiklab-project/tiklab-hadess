package io.tiklab.hadess.timedtask.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.timedtask.model.TimeTask;
import io.tiklab.hadess.timedtask.model.TimeTaskQuery;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* TimeTaskService-定时任务
*/
@JoinProvider(model = TimeTask.class)
public interface TimeTaskService {

    /**
    * 创建
    * @param timeTask
    * @return
    */
    String createTimeTask(@NotNull @Valid TimeTask timeTask);

    /**
    * 更新
    * @param timeTask
    */
    void updateTimeTask(@NotNull @Valid TimeTask timeTask);

    /**
     * 更新
     * @param taskInstanceId id
     */
    void updateTimeTask(String taskInstanceId);

    /**
    * 删除
    * @param id
    */
    void deleteTimeTask(@NotNull String id);


    @FindOne
    TimeTask findOne(@NotNull String id);

    @FindList
    List<TimeTask> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */

    TimeTask findTimeTask(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<TimeTask> findAllTimeTask();

    /**
    * 查询列表
    * @param timeTaskQuery
    * @return
    */
    List<TimeTask> findTimeTaskList(TimeTaskQuery timeTaskQuery);

    /**
     * 通过定时对象id 查询
     * @param scanPlayId
     * @return
     */
    List<TimeTask> findTimeTaskList(String scanPlayId);

    /**
    * 按分页查询
    * @param timeTaskQuery
    * @return
    */
    Pagination<TimeTask> findTimeTaskPage(TimeTaskQuery timeTaskQuery);

}