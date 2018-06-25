sample
===
* 注释

    select #use("cols")# from stb_config where #use("condition")#

cols
===

    id,operator_name,operator_id,call_number,load_id,apk_url,apk_net_url,version,update_force,description,update_time,update_id,sync_flag,nick_id,business_type,parent_operator_id,share_cost,album_id

updateSample
===

    `id`=#id#,`operator_name`=#operatorName#,`operator_id`=#operatorId#,`call_number`=#callNumber#,`load_id`=#loadId#,`apk_url`=#apkUrl#,`apk_net_url`=#apkNetUrl#,`version`=#version#,`update_force`=#updateForce#,`description`=#description#,`update_time`=#updateTime#,`update_id`=#updateId#,`sync_flag`=#syncFlag#,`nick_id`=#nickId#,`business_type`=#businessType#,`parent_operator_id`=#parentOperatorId#,`share_cost`=#shareCost#,`album_id`=#albumId#

condition
===

    1 = 1  
    @if(!isEmpty(operatorName)){
     and `operator_name`=#operatorName#
    @}
    @if(!isEmpty(operatorId)){
     and `operator_id`=#operatorId#
    @}
    @if(!isEmpty(callNumber)){
     and `call_number`=#callNumber#
    @}
    @if(!isEmpty(loadId)){
     and `load_id`=#loadId#
    @}
    @if(!isEmpty(apkUrl)){
     and `apk_url`=#apkUrl#
    @}
    @if(!isEmpty(apkNetUrl)){
     and `apk_net_url`=#apkNetUrl#
    @}
    @if(!isEmpty(version)){
     and `version`=#version#
    @}
    @if(!isEmpty(updateForce)){
     and `update_force`=#updateForce#
    @}
    @if(!isEmpty(description)){
     and `description`=#description#
    @}
    @if(!isEmpty(updateTime)){
     and `update_time`=#updateTime#
    @}
    @if(!isEmpty(updateId)){
     and `update_id`=#updateId#
    @}
    @if(!isEmpty(syncFlag)){
     and `sync_flag`=#syncFlag#
    @}
    @if(!isEmpty(nickId)){
     and `nick_id`=#nickId#
    @}
    @if(!isEmpty(businessType)){
     and `business_type`=#businessType#
    @}
    @if(!isEmpty(parentOperatorId)){
     and `parent_operator_id`=#parentOperatorId#
    @}
    @if(!isEmpty(shareCost)){
     and `share_cost`=#shareCost#
    @}
    @if(!isEmpty(albumId)){
     and `album_id`=#albumId#
    @}

listChildrenOperatorId
======================
    SELECT operator_id FROM stb_config WHERE parent_operator_id = #operatorId#
    
listByOperatorId
================
    SELECT * FROM stb_config WHERE 1 = 1
    @if(!isEmpty(operatorIds)){ // 渠道合计
     AND operator_id IN (#join(operatorIds)#)
    @}else if(!isEmpty(operatorId)){ // 单个渠道
     AND operator_id = #operatorId#
    @}
    
listParentAndBusinessChildren
=============================
    SELECT * FROM stb_config WHERE (parent_operator_id = #operatorId# AND business_type = 1)
    OR operator_id = #operatorId#
    @ // 将父渠道排前面
    ORDER BY CASE operator_id WHEN #operatorId# THEN 1 ELSE 2 END ASC
    
listIndependent
===============
    SELECT * FROM stb_config WHERE parent_operator_id IS NULL OR business_type != 1
    
page
====
    SELECT #page('*')# FROM stb_config WHERE 1 = 1
    @if (isNotEmpty(operatorName)) {
     AND operator_name LIKE #'%' + operatorName + '%'#
    @}
    
listParent
==========
    SELECT * FROM stb_config WHERE parent_operator_id IS NULL
    @if (isNotEmpty(operatorId)) {
     AND operator_id != #operatorId#
    @}
    
getByOperatorId
===============
    SELECT * FROM stb_config WHERE operator_id = #operatorId# LIMIT 0, 1