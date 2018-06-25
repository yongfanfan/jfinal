package com.haitai.haitaitv.component.es;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;

/**
 * @author liuzhou
 *         create at 2017-06-21 13:33
 */
public class MyListener implements BulkProcessor.Listener {

    private static final Logger LOGGER = LogManager.getLogger(MyListener.class);
    private int numberOfActions;
    private boolean hasFailures;

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public boolean isHasFailures() {
        return hasFailures;
    }

    @Override
    public void beforeBulk(long executionId, BulkRequest request) {
        numberOfActions = request.numberOfActions();
    }

    @Override
    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
        hasFailures = response.hasFailures();
    }

    @Override
    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
        LOGGER.error("es批量索引商品时发生异常", failure);
    }
}