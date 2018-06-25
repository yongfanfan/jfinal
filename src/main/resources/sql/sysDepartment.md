sample
===
* 注释

    select #use("cols")# from sys_department where #use("condition")#

cols
===

    id,name,sort,linkman,linkman_no,create_time,create_id,operator_id

updateSample
===

    `id`=#id#,`name`=#name#,`sort`=#sort#,`linkman`=#linkman#,`linkman_no`=#linkmanNo#,`create_time`=#createTime#,`create_id`=#createId#,`operator_id`=#operatorId#

condition
===

    1 = 1  
    @if(!isEmpty(name)){
     and `name`=#name#
    @}
    @if(!isEmpty(sort)){
     and `sort`=#sort#
    @}
    @if(!isEmpty(linkman)){
     and `linkman`=#linkman#
    @}
    @if(!isEmpty(linkmanNo)){
     and `linkman_no`=#linkmanNo#
    @}
    @if(!isEmpty(createTime)){
     and `create_time`=#createTime#
    @}
    @if(!isEmpty(createId)){
     and `create_id`=#createId#
    @}
    @if(!isEmpty(operatorId)){
     and `operator_id`=#operatorId#
    @}
    
page
====
    SELECT #page('*')# FROM sys_department WHERE 1 = 1
    @if (isNotEmpty(name)) {
     AND name LIKE #'%' + name + '%'#
    @}
