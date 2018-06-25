

page
===
    SELECT
    @pageTag(){
     m.*
    @}
    FROM tb_video_time_point m WHERE m.del_flag = 2 
    @if(!isEmpty(videoId)){
     and m.video_id = #videoId#
    @}