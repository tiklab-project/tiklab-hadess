-- 初始话管理员角色表
INSERT INTO `prc_role` (`id`, `name`, `description`, `grouper`, `type`, `bgroup`) VALUES ('1', '管理员角色', NULL, 'system', '1', 'oms');

-- 初始话项目角色表
INSERT INTO `prc_role` (`id`, `name`, `description`, `grouper`, `type`, `bgroup`) VALUES ('2', '项目角色', '项目角色', 'custom', '1', 'oms');

-- 项目角色金和 portal 用户绑定 表
INSERT INTO `prc_role_user` (`id`, `role_id`, `user_id`, `bgroup`) VALUES ('2', '2', 'f7d301865bb64ef21ef88f8cd58a69a5', 'oms');
-- 管理员角色金和admin用户绑定 表
INSERT INTO `prc_role_user` (`id`, `role_id`, `user_id`, `bgroup`) VALUES ('1', '1', '111111', 'oms');