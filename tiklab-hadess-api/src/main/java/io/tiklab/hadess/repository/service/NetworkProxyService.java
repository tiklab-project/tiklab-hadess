package io.tiklab.hadess.repository.service;


import io.tiklab.core.page.Pagination;
import io.tiklab.hadess.repository.model.NetworkProxy;
import io.tiklab.hadess.repository.model.NetworkProxyQuery;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* NetworkProxyService-网络代理地址接口
*/
@JoinProvider(model = NetworkProxy.class)
public interface NetworkProxyService {

    /**
    * 创建
    * @param networkProxy
    * @return
    */
    String createNetworkProxy(@NotNull @Valid NetworkProxy networkProxy);

    /**
    * 更新
    * @param networkProxy
    */
    void updateNetworkProxy(@NotNull @Valid NetworkProxy networkProxy);

    /**
    * 删除
    * @param id
    */
    void deleteNetworkProxy(@NotNull String id);

    @FindOne
    NetworkProxy findOne(@NotNull String id);

    @FindList
    List<NetworkProxy> findList(List<String> idList);

    /**
    * 查找
    * @param id
    * @return
    */
    NetworkProxy findNetworkProxy(@NotNull String id);

    /**
    * 查找所有
    * @return
    */
    @FindAll
    List<NetworkProxy> findAllNetworkProxy();

    /**
    * 查询列表
    * @param networkProxyQuery
    * @return
    */
    List<NetworkProxy> findNetworkProxyList(NetworkProxyQuery networkProxyQuery);

    /**
    * 按分页查询
    * @param networkProxyQuery
    * @return
    */
    Pagination<NetworkProxy> findNetworkProxyPage(NetworkProxyQuery networkProxyQuery);


}