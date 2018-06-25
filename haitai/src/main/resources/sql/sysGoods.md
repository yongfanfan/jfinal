page
===
    SELECT
    @pageTag(){
     m.*
    @}
    FROM sys_goods m WHERE m.del_flag = 2 
    @if(!isEmpty(name)){
     and m.name LIKE #'%' + name + '%'#
    @}
    
getProductBySize
===
    SELECT * from sys_goods g where g.update_time is not null and g.del_flag=2
    ORDER BY g.update_time desc limit #size#
    
    
    