sample
===
* 注释

    select #use("cols")# from sys_menu where #use("condition")#

cols
===

    id,parent_id,name,url_key,url,status,type,sort,level,create_time,create_id

updateSample
===

    `id`=#id#,`parent_id`=#parentId#,`name`=#name#,`url_key`=#urlKey#,`url`=#url#,`status`=#status#,`type`=#type#,`sort`=#sort#,`level`=#level#,`create_time`=#createTime#,`create_id`=#createId#

condition
===

    1 = 1  
    @if(!isEmpty(parentId)){
     and `parent_id`=#parentId#
    @}
    @if(!isEmpty(name)){
     and `name`=#name#
    @}
    @if(!isEmpty(urlKey)){
     and `url_key`=#urlKey#
    @}
    @if(!isEmpty(url)){
     and `url`=#url#
    @}
    @if(!isEmpty(status)){
     and `status`=#status#
    @}
    @if(!isEmpty(type)){
     and `type`=#type#
    @}
    @if(!isEmpty(sort)){
     and `sort`=#sort#
    @}
    @if(!isEmpty(level)){
     and `level`=#level#
    @}
    @if(!isEmpty(createTime)){
     and `create_time`=#createTime#
    @}
    @if(!isEmpty(createId)){
     and `create_id`=#createId#
    @}
    
listByTypeAndRoleIds
====================
    SELECT m.* FROM sys_menu m JOIN sys_role_menu rm ON m.id = rm.menu_id WHERE m.status = 1 AND m.type = #type#
    AND rm.role_id IN (#join(roleIds)#) GROUP BY m.id ORDER BY m.sort ASC
    
listPermissionsWhenSuper
========================
    SELECT url FROM sys_menu WHERE status = 1 AND url IS NOT NULL
    
listPermissionByRoleIds
=======================
    SELECT DISTINCT(m.url) FROM sys_menu m JOIN sys_role_menu rm ON m.id = rm.menu_id WHERE m.status = 1 AND m.url IS NOT NULL
    AND rm.can_update = #canUpdate# AND rm.role_id IN (#join(roleIds)#)
    
listValid
=========
    SELECT * FROM sys_menu WHERE status = 1 ORDER BY sort
    
page
====
    SELECT #page('*')# FROM sys_menu WHERE 1 = 1
    @if (isNotEmpty(status)) {
     and status = #status#
    @}
    @if (isNotEmpty(type)) {
     and type = #type#
    @}
    @if (isNotEmpty(name)) {
     AND name LIKE #'%' + name + '%'#
    @}
    @if (isNotEmpty(parentId)) {
     AND parent_id = #parentId#
    @}