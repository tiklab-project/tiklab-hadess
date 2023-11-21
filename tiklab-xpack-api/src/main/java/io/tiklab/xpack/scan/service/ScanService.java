package io.tiklab.xpack.scan.service;

import io.tiklab.xpack.scan.model.Scan;
import io.tiklab.xpack.scan.model.ScanQueue;

import java.util.List;
import java.util.Map;

public interface ScanService {

    /**
     * 执行扫描
     * @param scanPlayId 扫描计划id
     * @return
     */
    String execScan(String scanPlayId);

    /**
     * 查询扫描结果
     * @param scanPlayId 扫描计划id
     * @return
     */
    ScanQueue findExecResult(String scanPlayId);
}
