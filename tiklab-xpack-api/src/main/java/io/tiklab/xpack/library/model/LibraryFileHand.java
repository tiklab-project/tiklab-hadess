package io.tiklab.xpack.library.model;

import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.io.Serializable;

@ApiModel
@Join
public class LibraryFileHand implements Serializable {

    @ApiProperty(name="filePath",desc="文件地址")
    private java.lang.String filePath;

    @ApiProperty(name="groupId",desc="groupId")
    private String groupId;

    @ApiProperty(name="artifactId",desc="artifactId")
    private String artifactId;

    @ApiProperty(name="version",desc="版本")
    private String version;

    @ApiProperty(name="packaging",desc="文件类型 jar、pom...")
    private String packaging;

    @ApiProperty(name="classifier",desc="classifier")
    private String classifier;

    @ApiProperty(name="pom",desc="是否生成pom")
    private String pom;


    @ApiProperty(name="repositoryId",desc="仓库id")
    private String repositoryId;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getPom() {
        return pom;
    }

    public void setPom(String pom) {
        this.pom = pom;
    }


    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }
}
