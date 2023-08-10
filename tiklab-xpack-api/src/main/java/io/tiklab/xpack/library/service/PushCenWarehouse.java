package io.tiklab.xpack.library.service;

public interface PushCenWarehouse {

   /**
    * 推送中央仓库
    * @param libraryId 制品id
    * @param type  类型 maven、npm
    */
   String pushCentralWare(String libraryId, String type);

   /**
    * 推送中央仓库结果
    * @param libraryId 制品id
    */
   String pushResult(String libraryId);
}
