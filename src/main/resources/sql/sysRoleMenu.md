sample
===
* 注释

    select #use("cols")# from sys_role_menu where #use("condition")#

cols
===

    id,role_id,menu_id,can_update

updateSample
===

    `id`=#id#,`role_id`=#roleId#,`menu_id`=#menuId#,`can_update`=#canUpdate#

condition
===

    1 = 1  
    @if(!isEmpty(roleId)){
     and `role_id`=#roleId#
    @}
    @if(!isEmpty(menuId)){
     and `menu_id`=#menuId#
    @}
    @if(!isEmpty(canUpdate)){
     and `can_update`=#canUpdate#
    @}
    
deleteByRoleId
==============
    DELETE FROM sys_role_menu WHERE role_id = #roleId#