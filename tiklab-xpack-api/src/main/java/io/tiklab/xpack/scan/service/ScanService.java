package io.tiklab.xpack.scan;

public interface ScanService {


    /**
     * 执行单个扫描
     * @param libraryId 制品id
     * @param versionId 版本id
     * @return
     */
    void scanOneLibrary(String libraryId,String versionId);

    /**
     * 执行扫描
     * @param repositoryId 仓库id
     * @return
     */
    void scanLibrary(String repositoryId);

}
