package io.thoughtware.hadess.setting.model;

import io.thoughtware.licence.licence.model.Version;
import io.thoughtware.postin.annotation.ApiModel;
import io.thoughtware.postin.annotation.ApiProperty;

@ApiModel
public class SystemCount {

    @ApiProperty(name="userNum",desc="用户数量")
    private Integer userNum;

    @ApiProperty(name="orgaNum",desc="部门数量")
    private Integer orgaNum;

    @ApiProperty(name="userGroupNum",desc="用户组数量")
    private Integer userGroupNum;

    @ApiProperty(name="roleNum",desc="角色（权限）数量")
    private Integer roleNum;

    @ApiProperty(name="userDirNum",desc="用户目录数量")
    private Integer userDirNum;

    @ApiProperty(name="messageNoticeNum",desc="消息通知方案数量")
    private Integer messageNoticeNum;

    @ApiProperty(name="messageSendTypeNum",desc="消息发送方式数量")
    private Integer messageSendTypeNum;

    @ApiProperty(name="installPluginNum",desc="安装的插件数量")
    private Integer installPluginNum;

    @ApiProperty(name="pluginNum",desc="插件市场数量")
    private Integer pluginNum;

    @ApiProperty(name="version",desc="版本 企业版、社区版")
    private Version version;

    @ApiProperty(name="authUserNum",desc="授权人数数量")
    private Integer authUserNum;

    @ApiProperty(name="scanSchemeNum",desc="扫描方案数量")
    private Integer scanSchemeNum;

    @ApiProperty(name="backupsTime",desc="备份时间")
    private String backupsTime;

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getOrgaNum() {
        return orgaNum;
    }

    public void setOrgaNum(Integer orgaNum) {
        this.orgaNum = orgaNum;
    }

    public Integer getUserGroupNum() {
        return userGroupNum;
    }

    public void setUserGroupNum(Integer userGroupNum) {
        this.userGroupNum = userGroupNum;
    }

    public Integer getRoleNum() {
        return roleNum;
    }

    public void setRoleNum(Integer roleNum) {
        this.roleNum = roleNum;
    }

    public Integer getUserDirNum() {
        return userDirNum;
    }

    public void setUserDirNum(Integer userDirNum) {
        this.userDirNum = userDirNum;
    }

    public Integer getMessageNoticeNum() {
        return messageNoticeNum;
    }

    public void setMessageNoticeNum(Integer messageNoticeNum) {
        this.messageNoticeNum = messageNoticeNum;
    }

    public Integer getMessageSendTypeNum() {
        return messageSendTypeNum;
    }

    public void setMessageSendTypeNum(Integer messageSendTypeNum) {
        this.messageSendTypeNum = messageSendTypeNum;
    }

    public Integer getInstallPluginNum() {
        return installPluginNum;
    }

    public void setInstallPluginNum(Integer installPluginNum) {
        this.installPluginNum = installPluginNum;
    }

    public Integer getPluginNum() {
        return pluginNum;
    }

    public void setPluginNum(Integer pluginNum) {
        this.pluginNum = pluginNum;
    }


    public Integer getAuthUserNum() {
        return authUserNum;
    }

    public void setAuthUserNum(Integer authUserNum) {
        this.authUserNum = authUserNum;
    }

    public String getBackupsTime() {
        return backupsTime;
    }

    public void setBackupsTime(String backupsTime) {
        this.backupsTime = backupsTime;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Integer getScanSchemeNum() {
        return scanSchemeNum;
    }

    public void setScanSchemeNum(Integer scanSchemeNum) {
        this.scanSchemeNum = scanSchemeNum;
    }
}
