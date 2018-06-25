sample
===
* 注释

    select #use("cols")# from sys_log where #use("condition")#

cols
===

    id,log_type,oper_object,oper_table,oper_id,oper_type,oper_remark,create_time,create_id

updateSample
===

    `id`=#id#,`log_type`=#logType#,`oper_object`=#operObject#,`oper_table`=#operTable#,`oper_id`=#operId#,`oper_type`=#operType#,`oper_remark`=#operRemark#,`create_time`=#createTime#,`create_id`=#createId#

condition
===

    1 = 1  
    @if(!isEmpty(logType)){
     and `log_type`=#logType#
    @}
    @if(!isEmpty(operObject)){
     and `oper_object`=#operObject#
    @}
    @if(!isEmpty(operTable)){
     and `oper_table`=#operTable#
    @}
    @if(!isEmpty(operId)){
     and `oper_id`=#operId#
    @}
    @if(!isEmpty(operType)){
     and `oper_type`=#operType#
    @}
    @if(!isEmpty(operRemark)){
     and `oper_remark`=#operRemark#
    @}
    @if(!isEmpty(createTime)){
     and `create_time`=#createTime#
    @}
    @if(!isEmpty(createId)){
     and `create_id`=#createId#
    @}
    
page
====
    SELECT #page('*')# FROM sys_log WHERE 1 = 1
    @if(!isEmpty(logType)){
     and `log_type`=#logType#
    @}