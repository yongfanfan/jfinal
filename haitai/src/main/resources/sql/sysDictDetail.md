sample
===
* 注释

    select #use("cols")# from sys_dict_detail where #use("condition")#

cols
===

    id,dict_type,detail_name,detail_code,detail_sort,detail_type,detail_state,detail_content,detail_remark,create_time,create_id

updateSample
===

    `id`=#id#,`dict_type`=#dictType#,`detail_name`=#detailName#,`detail_code`=#detailCode#,`detail_sort`=#detailSort#,`detail_type`=#detailType#,`detail_state`=#detailState#,`detail_content`=#detailContent#,`detail_remark`=#detailRemark#,`create_time`=#createTime#,`create_id`=#createId#

condition
===

    1 = 1  
    @if(!isEmpty(dictType)){
     and `dict_type`=#dictType#
    @}
    @if(!isEmpty(detailName)){
     and `detail_name`=#detailName#
    @}
    @if(!isEmpty(detailCode)){
     and `detail_code`=#detailCode#
    @}
    @if(!isEmpty(detailSort)){
     and `detail_sort`=#detailSort#
    @}
    @if(!isEmpty(detailType)){
     and `detail_type`=#detailType#
    @}
    @if(!isEmpty(detailState)){
     and `detail_state`=#detailState#
    @}
    @if(!isEmpty(detailContent)){
     and `detail_content`=#detailContent#
    @}
    @if(!isEmpty(detailRemark)){
     and `detail_remark`=#detailRemark#
    @}
    @if(!isEmpty(createTime)){
     and `create_time`=#createTime#
    @}
    @if(!isEmpty(createId)){
     and `create_id`=#createId#
    @}

page
====
    SELECT #page('*')# FROM sys_dict_detail WHERE 1 = 1
    @if(!isEmpty(dictType)){
     and `dict_type`=#dictType#
    @}