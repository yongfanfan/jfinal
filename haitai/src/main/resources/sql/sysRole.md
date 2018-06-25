sample
===
* 注释

    select #use("cols")# from sys_role where #use("condition")#

cols
===

    id,name,status,sort,remark,create_time,create_id

updateSample
===

    `id`=#id#,`name`=#name#,`status`=#status#,`sort`=#sort#,`remark`=#remark#,`create_time`=#createTime#,`create_id`=#createId#

condition
===

    1 = 1  
    @if(!isEmpty(name)){
     and `name`=#name#
    @}
    @if(!isEmpty(status)){
     and `status`=#status#
    @}
    @if(!isEmpty(sort)){
     and `sort`=#sort#
    @}
    @if(!isEmpty(remark)){
     and `remark`=#remark#
    @}
    @if(!isEmpty(createTime)){
     and `create_time`=#createTime#
    @}
    @if(!isEmpty(createId)){
     and `create_id`=#createId#
    @}
    
listValid
=========
    SELECT * FROM sys_role WHERE status = 1 ORDER BY sort ASC
    
menuNameExcludeWhere
====================
    SELECT GROUP_CONCAT(m.name ORDER BY m.sort) FROM sys_role_menu rm JOIN sys_menu m ON rm.menu_id = m.id
    
page
====
    SELECT
    @pageTag(){
     r.*,
     (#use('menuNameExcludeWhere')# WHERE rm.role_id = r.id AND can_update = 0) view_menus,
     (#use('menuNameExcludeWhere')# WHERE rm.role_id = r.id AND can_update = 1) update_menus
    @}
    FROM sys_role r WHERE 1 = 1
    @if (isNotEmpty(name)) {
     AND r.name LIKE #'%' + name + '%'#
    @}