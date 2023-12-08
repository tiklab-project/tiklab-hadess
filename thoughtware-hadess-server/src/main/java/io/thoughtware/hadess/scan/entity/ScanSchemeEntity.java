package io.thoughtware.hadess.scan.entity;

import io.thoughtware.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pack_scan_scheme")
public class ScanSchemeEntity {
    @Id
    @GeneratorValue(length=12)
    @Column(name = "id")
    private String id;

    @Column(name = "scheme_name" ,notNull = true)
    private String schemeName;

    @Column(name = "language",notNull = true)
    private String language;

    @Column(name = "scheme_type",notNull = true)
    private String schemeType;

    @Column(name = "describe",notNull = true)
    private String describe;

    @Column(name = "create_time")
    private Timestamp createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }
}
