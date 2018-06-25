sample
===
* 注释

    select #use("cols")# from sys_user where #use("condition")#

cols
===

    id,username,password,realname,department_id,type,state,third_id,end_time,email,tel,address,title_url,remark,theme,create_time,create_id

updateSample
===

    `id`=#id#,`username`=#username#,`password`=#password#,`realname`=#realname#,`department_id`=#departmentId#,`type`=#type#,`state`=#state#,`third_id`=#thirdId#,`end_time`=#endTime#,`email`=#email#,`tel`=#tel#,`address`=#address#,`title_url`=#titleUrl#,`remark`=#remark#,`theme`=#theme#,`create_time`=#createTime#,`create_id`=#createId#

condition
===

    1 = 1  
    @if(!isEmpty(username)){
     and `username`=#username#
    @}
    @if(!isEmpty(password)){
     and `password`=#password#
    @}
    @if(!isEmpty(realname)){
     and `realname`=#realname#
    @}
    @if(!isEmpty(departmentId)){
     and `department_id`=#departmentId#
    @}
    @if(!isEmpty(type)){
     and `type`=#type#
    @}
    @if(!isEmpty(state)){
     and `state`=#state#
    @}
    @if(!isEmpty(thirdId)){
     and `third_id`=#thirdId#
    @}
    @if(!isEmpty(endTime)){
     and `end_time`=#endTime#
    @}
    @if(!isEmpty(email)){
     and `email`=#email#
    @}
    @if(!isEmpty(tel)){
     and `tel`=#tel#
    @}
    @if(!isEmpty(address)){
     and `address`=#address#
    @}
    @if(!isEmpty(titleUrl)){
     and `title_url`=#titleUrl#
    @}
    @if(!isEmpty(remark)){
     and `remark`=#remark#
    @}
    @if(!isEmpty(theme)){
     and `theme`=#theme#
    @}
    @if(!isEmpty(createTime)){
     and `create_time`=#createTime#
    @}
    @if(!isEmpty(createId)){
     and `create_id`=#createId#
    @}
    
page
====
    SELECT #page('*')# FROM sys_user WHERE id != 1
    @if (isNotEmpty(username)) {
     AND username LIKE #'%' + username + '%'#
    @}
    @if (isNotEmpty(realname)) {
     AND realname LIKE #'%' + realname + '%'#
    @}
    @if (isNotEmpty(type)) {
     AND type = #type#
    @}
    @if (isNotEmpty(departmentId)) {
     AND department_id = #departmentId#
    @}