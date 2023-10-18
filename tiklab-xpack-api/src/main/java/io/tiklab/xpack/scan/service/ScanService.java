package io.tiklab.xpack.scan.service;

import io.tiklab.xpack.scan.model.Scan;
import io.tiklab.xpack.scan.model.ScanQueue;

import java.util.List;
import java.util.Map;

public interface ScanService {

    /**
     * 执行单个扫描
     * @param scanQueue 扫描制品
     * @return
     */
    String excOneScanLibrary(ScanQueue scanQueue);

    /**
     * 多个制品扫描
     * @param scanQueue 扫描制品
     * @return
     */
    String excMultiScanLibrary(ScanQueue scanQueue);

    /**
     * 通过制品库id 查询执行队列
     * @param repositoryId
     * @return
     */
    List findScanQueue(String repositoryId);

    /**
     * 查询单个扫描结果
     * @param scanLibraryId 扫描制品id
     * @return
     */
    Map findOneScanResult(String scanLibraryId);




}
