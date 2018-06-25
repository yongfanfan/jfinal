package com.haitai.haitaitv.component.util.serialize;

import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;

public class FSTSerializer implements Serializer {

    // FSTConfiguration是线程安全的
    private static final FSTConfiguration FST_CONFIGURATION = FSTConfiguration.createDefaultConfiguration();

    static {
        // 允许序列化非Serializable的对象
        FST_CONFIGURATION.setForceSerializable(true);
    }

    public byte[] serialize(Object obj) throws IOException {
        return FST_CONFIGURATION.asByteArray(obj);
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes) throws IOException {
        return (T) FST_CONFIGURATION.asObject(bytes);
    }

}
