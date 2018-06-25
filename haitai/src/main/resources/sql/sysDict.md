sample
===
* 注释

    select #use("cols")# from sys_dict where #use("condition")#

cols
===

    id,dict_name,dict_type,dict_remark

updateSample
===

    `id`=#id#,`dict_name`=#dictName#,`dict_type`=#dictType#,`dict_remark`=#dictRemark#

condition
===

    1 = 1  
    @if(!isEmpty(dictName)){
     and `dict_name`=#dictName#
    @}
    @if(!isEmpty(dictType)){
     and `dict_type`=#dictType#
    @}
    @if(!isEmpty(dictRemark)){
     and `dict_remark`=#dictRemark#
    @}
    
