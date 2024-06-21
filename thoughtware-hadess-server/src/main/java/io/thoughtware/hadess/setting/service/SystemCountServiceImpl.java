package io.thoughtware.hadess.setting.service;

import io.thoughtware.hadess.setting.model.SystemCount;
import io.thoughtware.licence.appauth.service.ApplyAuthService;
import io.thoughtware.licence.licence.model.Version;
import io.thoughtware.licence.licence.service.VersionService;
import io.thoughtware.message.message.service.MessageNoticeService;
import io.thoughtware.message.setting.service.MessageSendTypeService;
import io.thoughtware.plugin.manager.service.PluginManagerService;
import io.thoughtware.privilege.role.service.RoleService;
import io.thoughtware.security.backups.service.BackupsDbService;
import io.thoughtware.user.directory.service.UserDirService;
import io.thoughtware.user.orga.service.OrgaService;
import io.thoughtware.user.user.service.UserService;
import io.thoughtware.user.usergroup.service.UserGroupService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemCountServiceImpl implements SystemCountService{

    @Autowired
    UserService userService;

    @Autowired
    OrgaService orgaService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserDirService userDirService;

    @Autowired
    MessageNoticeService messageNoticeService;

    @Autowired
    MessageSendTypeService messageSendTypeService;

    @Autowired
    PluginManagerService pluginManagerService;

    @Autowired
    VersionService versionService;

    @Autowired
    ApplyAuthService applyAuthService;

    @Autowired
    BackupsDbService backupsDbService;




    @Override
    public SystemCount collectCount() {
        SystemCount systemCount = new SystemCount();

        Integer userNumber = userService.findUserNumber();
        Integer orgaNumber = orgaService.findOrgaNumber();
        Integer userGroupNumber = userGroupService.findUserGroupNumber();
        Integer roleNumber = roleService.findRoleNumber();
        Integer userDirNumber = userDirService.findUserDirNumber();
        Integer noticeNumber = messageNoticeService.findNoticeNumber("gittok");
        Integer sendTypeNumber = messageSendTypeService.findSendTypeNumber();
        Integer installPluginNumber = pluginManagerService.findInstallPluginNumber();
        Integer shopPluginNumber = pluginManagerService.findShopPluginNumber();
        Version version = versionService.getVersion();
        Integer applyAuthNumber = applyAuthService.findApplyAuthNumber();
        String lastBackupsTime = backupsDbService.findLastBackupsTime();

       /* List<ScanScheme> allScanScheme = scanSchemeService.findAllScanScheme();
        int schemeNum = CollectionUtils.isNotEmpty(allScanScheme) ? allScanScheme.size() : 0;
        systemCount.setScanSchemeNum(schemeNum);*/

        systemCount.setUserNum(userNumber);
        systemCount.setOrgaNum(orgaNumber);
        systemCount.setUserGroupNum(userGroupNumber);
        systemCount.setRoleNum(roleNumber);
        systemCount.setUserDirNum(userDirNumber);
        systemCount.setMessageNoticeNum(noticeNumber);
        systemCount.setMessageSendTypeNum(sendTypeNumber);
        systemCount.setInstallPluginNum(installPluginNumber);
        systemCount.setPluginNum(shopPluginNumber);
        systemCount.setVersion(version);
        systemCount.setAuthUserNum(applyAuthNumber);
        systemCount.setBackupsTime(lastBackupsTime);

        return systemCount;
    }
}
