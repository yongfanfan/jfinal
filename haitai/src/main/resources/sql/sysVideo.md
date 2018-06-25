page
===
    SELECT
    @pageTag(){
     m.*
    @}
    FROM sys_video m WHERE m.del_flag = 2 
    @if(!isEmpty(content)){
     and m.content LIKE #'%' + content + '%'#
    @}
    
getVideoBySize
===
    SELECT * from sys_video g where  g.del_flag=2
    ORDER BY g.create_time desc limit #size#