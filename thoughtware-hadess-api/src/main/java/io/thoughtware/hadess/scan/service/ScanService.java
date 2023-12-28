package io.thoughtware.hadess.scan.service;

import io.thoughtware.hadess.scan.model.ScanQueue;
import io.thoughtware.hadess.scan.model.ScanRecord;

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

    /**
     * 查询扫描结果
     * @param scanPlayId 扫描计划id
     * @return
     */
    ScanRecord findExecState(String scanPlayId);
}
