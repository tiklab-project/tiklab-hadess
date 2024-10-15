package io.tiklab.hadess.repository.service;

import io.tiklab.hadess.repository.model.ResourceMan;

/*
* 查询资源占用情况
* */
public interface ResourceManService {

    /**
     * 查询资源
     * @return
     */
    ResourceMan findResource();
}
