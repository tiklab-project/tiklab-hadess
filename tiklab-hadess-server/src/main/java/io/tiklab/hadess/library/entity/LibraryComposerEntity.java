package io.tiklab.hadess.library.entity;

import io.tiklab.core.BaseModel;
import io.tiklab.dal.jpa.annotation.*;

/**
 * LibraryPypiEntity-composer扩展数据
 */
@Entity
@Table(name="pack_library_composer")
public class LibraryComposerEntity extends BaseModel {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",length = 12)
    private String id;


    //制品Id
    @Column(name = "library_id",length = 32,notNull = true)
    private String libraryId;

    //目标包的版本和依赖详情文件的路径
    @Column(name = "metadata_path",length = 448,notNull = true)
    private String metadataPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public String getMetadataPath() {
        return metadataPath;
    }

    public void setMetadataPath(String metadataPath) {
        this.metadataPath = metadataPath;
    }
}
