getCreateByTime
===
    SELECT create_time 'create_time', COUNT(1) 'id' FROM `tb_log` 
    where create_time is not null and product_id is null and DATE(create_time) >= #startTime# and DATE(create_time) <= #endTime#
    group by DATE(create_time);
    
    
getPayByTime
===
    SELECT create_time 'create_time', COUNT(1) 'id' FROM `tb_log` 
    where create_time is not null and product_id is not null and DATE(create_time) >= #startTime# and DATE(create_time) <= #endTime#
    group by DATE(create_time);
    
        
getCreateCountByDate
===
    SELECT DATE_FORMAT(create_time,'%H') 'remark', COUNT(1) 'id' FROM `tb_log` 
    where create_time is not null and DATE(create_time)=#time# GROUP BY DATE_FORMAT(create_time,'%H')
        
        
    
getProductTop10ByTime
===
    SELECT product_id, COUNT(1) 'id' FROM `tb_log` 
    where product_id is not null and DATE(create_time) >= #startTime# and DATE(create_time) <= #endTime#
    group by product_id
    ORDER BY  COUNT(1) DESC
    limit 0,10
    
getVideoTop10ByTime
===
    SELECT video_id, COUNT(1) 'id' FROM `tb_log` 
    where video_id is not null and DATE(create_time) >= #startTime# and DATE(create_time) <= #endTime#
    group by video_id
    ORDER BY  COUNT(1) DESC
    limit 0,10
    
