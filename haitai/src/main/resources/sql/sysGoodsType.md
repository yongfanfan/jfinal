page
===
    SELECT
    @pageTag(){
     m.*
    @}
    FROM sys_goods_type m WHERE m.del_flag = 2 
    @if(!isEmpty(name)){
     and m.name LIKE #'%' + name + '%'#
    @}