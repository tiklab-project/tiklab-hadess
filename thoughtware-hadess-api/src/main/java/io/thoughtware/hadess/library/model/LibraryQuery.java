package io.thoughtware.hadess.library.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel
public class LibraryQuery implements Serializable {

        @ApiProperty(name ="orderParams",desc = "排序参数")
        private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

        @ApiProperty(name ="pageParam",desc = "分页参数")
        private Page pageParam = new Page();

        @ApiProperty(name ="repositoryId",desc = "制品库id")
        private String repositoryId;

        @ApiProperty(name ="libraryType",desc = "制品类型")
        private String libraryType;

        @ApiProperty(name ="name",desc = "制品名称")
        private String name;

        @ApiProperty(name ="newVersion",desc = "最新版本")
        private String newVersion;

        @ApiProperty(name ="sort",desc = "排序")
        private String sort;

        /*-----------其他字段------------*/


        @ApiProperty(name ="repositoryIds",desc = "制品库ids")
        private List<String> repositoryIds;

        @ApiProperty(name ="groupId",desc = "groupId")
        private String groupId;

        @ApiProperty(name ="artifactId",desc = "artifactId")
        private String artifactId;


        @ApiProperty(name ="scanPlayId",desc = "扫描计划id")
        private String scanPlayId;

        @ApiProperty(name ="versionType",desc = "查询版本类型 all、new")
        private String versionType;

        public List<Order> getOrderParams() {
            return orderParams;
        }

        public void setOrderParams(List<Order> orderParams) {
            this.orderParams = orderParams;
        }

        public Page getPageParam() {
            return pageParam;
        }

        public void setPageParam(Page pageParam) {
            this.pageParam = pageParam;
        }

        public String getRepositoryId() {
            return repositoryId;
        }

        public LibraryQuery setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
            return this;
        }

        public String getLibraryType() {
            return libraryType;
        }

        public LibraryQuery setLibraryType(String libraryType) {
            this.libraryType = libraryType;
            return this;
        }

        public String getName() {
            return name;
        }

        public LibraryQuery setName(String name) {
            this.name = name;
            return this;
        }

        public String getNewVersion() {
            return newVersion;
        }

        public LibraryQuery setNewVersion(String newVersion) {
            this.newVersion = newVersion;
            return this;
        }

        public List<String> getRepositoryIds() {
            return repositoryIds;
        }

        public void setRepositoryIds(List<String> repositoryIds) {
            this.repositoryIds = repositoryIds;
        }

        public String getGroupId() {
                return groupId;
            }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }

        public String getScanPlayId() {
            return scanPlayId;
        }

        public LibraryQuery setScanPlayId(String scanPlayId) {
            this.scanPlayId = scanPlayId;
            return this;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getVersionType() {
            return versionType;
        }

        public void setVersionType(String versionType) {
            this.versionType = versionType;
        }
}