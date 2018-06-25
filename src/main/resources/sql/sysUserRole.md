sample
===
* 注释

    select #use("cols")# from sys_user_role where #use("condition")#

cols
===

    id,user_id,role_id

updateSample
===

    `id`=#id#,`user_id`=#userId#,`role_id`=#roleId#

condition
===

    1 = 1  
    @if(!isEmpty(userId)){
     and `user_id`=#userId#
    @}
    @if(!isEmpty(roleId)){
     and `role_id`=#roleId#
    @}
    
listRoleId
==========
    SELECT role_id FROM sys_user_role WHERE user_id = #userId#
    
deleteByUserId
==============
    DELETE FROM sys_user_role WHERE user_id = #userId#
    
deleteByRoleId
==============
    DELETE FROM sys_user_role WHERE role_id = #roleId#